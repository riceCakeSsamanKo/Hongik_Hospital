package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hongik_hospital.domain.*;
import project.hongik_hospital.form.ReserveForm;
import project.hongik_hospital.repository.DepartmentRepository;
import project.hongik_hospital.service.DoctorService;
import project.hongik_hospital.service.HospitalService;
import project.hongik_hospital.service.PatientService;
import project.hongik_hospital.service.ReserveService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;
    private final PatientService patientService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final DepartmentRepository departmentRepository; //추후에 DepartmentService로 교체 가능

    @GetMapping("/reserve/department")
    public String selectReserveDepartment(Model model) {

        model.addAttribute("form", new ReserveForm());

        List<Hospital> hospitals = hospitalService.findHospitals();
        Hospital hospital = hospitals.get(0);
        model.addAttribute("hospital", hospital);

        log.info("GET: select department");
        return "reserve/selectDepartment";
    }

    @PostMapping("reserve/department")
    public String getReserveDepartment(ReserveForm form, Model model) {
        Long departmentId = form.getDepartmentId();
        Department department = departmentRepository.findOne(departmentId);

        log.info("POST: select department");
        return "reserve/createReserveForm";
    }

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
    public String getReserveDoctor(ReserveForm form, HttpSession session) {
        Patient loggedInUser = (Patient) session.getAttribute("loggedInUser");
        Patient patient = patientService.findPatient(loggedInUser.getId());

        Long doctorId = form.getDoctorId();
        Doctor doctor = doctorService.findDoctor(doctorId);

        TreatmentDate treatmentDate = TreatmentDate.createTreatmentDate(form.getMonth(), form.getDate(), form.getHour(), form.getMinute());

        Reserve reserve = Reserve.createReserve(patient, doctor , LocalDateTime.now(), treatmentDate);

        reserveService.makeReserve(reserve);
        log.info("POST: select doctor and treatmentTime");
        return "redirect:/";
    }

    //의사가 예약하려는 시간에 이미 예약이 차 있다면 예약 불가 떠야 함
}
