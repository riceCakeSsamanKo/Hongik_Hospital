package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.hongik_hospital.domain.*;
import project.hongik_hospital.form.UserForm;
import project.hongik_hospital.form.ReserveForm;
import project.hongik_hospital.repository.DepartmentRepository;
import project.hongik_hospital.service.DoctorService;
import project.hongik_hospital.service.HospitalService;
import project.hongik_hospital.service.UserService;
import project.hongik_hospital.service.ReserveService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;
    private final UserService userService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final DepartmentRepository departmentRepository; //추후에 DepartmentService로 교체 가능

    @GetMapping("/reserve/department")
    public String selectReserveDepartment(Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("form", new UserForm());
            model.addAttribute("needToSignIn", true);
            return "loginForm";
        }

        model.addAttribute("form", new ReserveForm());

        List<Hospital> hospitals = hospitalService.findHospitals();
        Hospital hospital = hospitals.get(0);
        model.addAttribute("hospital", hospital);

        log.info("GET: select department");
        return "reserve/selectDepartment";
    }

//    @PostMapping("reserve/department")
//    public String getReserveDepartment(ReserveForm form, Model model) {
//
//        Long departmentId = form.getDepartmentId();
//        Department department = departmentRepository.findOne(departmentId);
//
//        log.info("POST: select department");
//        return "reserve/createReserveForm";
//    }

    @GetMapping("reserve/department/doctor")
    //th:action="@{/reserve/department/doctor}에 의해서 전달된 데이터(departmentId)가 form으로 주입됨
    public String selectReserveDoctor(@ModelAttribute("form") ReserveForm form, Model model) {

        Department department = departmentRepository.findOne(form.getDepartmentId());
        List<Doctor> doctors = department.getDoctors();

        model.addAttribute("doctors", doctors);
        model.addAttribute("department", department);

        log.info("GET: select doctor and treatmentTime");
        return "reserve/createReserveForm";
    }

    @PostMapping("reserve/department/doctor")
    public String getReserveDoctor(ReserveForm form, BindingResult result, Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        User user = userService.findUser(loggedInUser.getId());

        Long doctorId = form.getDoctorId();
        Doctor doctor = doctorService.findDoctor(doctorId);

        TreatmentDate treatmentDate = TreatmentDate.createTreatmentDate(form.getMonth(), form.getDate(), form.getHour(), form.getMinute());

        //의사가 예약하려는 시간에 이미 예약이 차 있다면 예약 불가 창이 떠야 함
        try {
            Reserve reserve = Reserve.createReserve(user, doctor, LocalDateTime.now(), treatmentDate);
            reserveService.saveReserve(reserve);
        } catch (IllegalStateException e) {
            log.info(e.getMessage());
            return "reserve/error";
        }


        log.info("POST: select doctor and treatmentTime");
        return "redirect:/";
    }

    @GetMapping("/reserve/history")
    public String getReserveHistory(@ModelAttribute("reserveForm") ReserveForm form, Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("form", new UserForm());
            model.addAttribute("needToSignIn", true);
            return "loginForm";
        }
        User user = userService.findUser(loggedInUser.getId());

        List<ReserveDto> reserveDtos = new ArrayList<>();
        List<Reserve> reserves = user.getReserves();

        //reserves에서 form에서 넘어온 status와 같은 reserve만 가져와 dto로 넘김
        for (Reserve reserve : reserves) {
            if (reserve.getReserveStatus() == form.getStatus()) {
                reserveDtos.add(new ReserveDto(reserve));
            }
        }

        model.addAttribute("reserves", reserveDtos);

        log.info("reserve history");

        return "reserve/reserveHistory";
    }

    @PostMapping("/reserve/history/{reserveId}/cancel")
    public String cancelReserve(@PathVariable("reserveId") Long reserveId) {

        reserveService.cancel(reserveId);
        return "redirect:/reserve/history";
    }

    @GetMapping("/admin/manage/history")
    public String manageReserve(@ModelAttribute("form") ReserveForm form, Model model) {

        List<Reserve> reserves = reserveService.findReserves();
        for (Reserve reserve : reserves) {
            System.out.println("reserve.getReserveStatus() = " + reserve.getReserveStatus());
        }

        List<ReserveDto> reserveDtos = new ArrayList<>();

        //reserves에서 form에서 넘어온 status와 같은 reserve만 가져와 dto로 넘김
        for (Reserve reserve : reserves) {
            if (reserve.getReserveStatus() == form.getStatus()) {
                reserveDtos.add(new ReserveDto(reserve));
            }
        }

        model.addAttribute("reserves", reserveDtos);

        return "reserve/manageHistory";
    }

    @PostMapping("/admin/manage/history/{reserveId}")
    public String manageReserveComplete(@PathVariable("reserveId") Long reserveId, ReserveForm form) {

        reserveService.complete(reserveId, form.getFee());

        return "redirect:/admin/manage/history";
    }
}