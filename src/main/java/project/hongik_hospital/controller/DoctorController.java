package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.domain.reserve.Reserve;
import project.hongik_hospital.domain.TreatmentDate;
import project.hongik_hospital.form.DoctorForm;
import project.hongik_hospital.repository.TreatmentDateRepository;
import project.hongik_hospital.service.DepartmentService;
import project.hongik_hospital.service.DoctorService;
import project.hongik_hospital.service.ReserveService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final ReserveService reserveService;
    private final TreatmentDateRepository treatmentDateRepository;

    @RequestMapping("/admin/edit/doctor")
    public String editDoctor() {

        log.info("edit doctor");
        return "doctor/editDoctor";
    }

    @GetMapping("/admin/edit/doctor/add")
    public String addDepartment(Model model) {
        List<Department> departments = departmentService.findDepartments();

        model.addAttribute("form", new DoctorForm());
        model.addAttribute("departments", departments);

        log.info("add doctor");
        return "doctor/addDoctor";
    }

    @PostMapping("/admin/edit/doctor/add")
    public String addDepartmentToDB(@Valid DoctorForm form, Model model) {
        String name = form.getName();
        int career = form.getCareer();
        Department department = departmentService.findDepartment(form.getDepartmentId());

        // 진료과와 의사 의존관계 매핑
        Doctor doctor = new Doctor(name, career);
        department.addDoctor(doctor);

        doctorService.join(doctor);

        return "doctor/editDoctor";
    }

    @GetMapping("/admin/edit/doctor/delete")
    public String deleteDepartment(Model model) {
        List<Doctor> doctors = doctorService.findDoctors();  //왠지 모르겠는데 jpql에서 department를 left join해서 가져오려는데 에러남.

        model.addAttribute("doctors", doctors);
        model.addAttribute("form", new DoctorForm());

        log.info("delete doctor");

        return "doctor/deleteDoctor";
    }

    @DeleteMapping("/admin/edit/doctor/delete")
    public String deleteDepartmentDelete(DoctorForm form) {

        Doctor doctor = doctorService.findDoctor(form.getDoctorId());

        // Doctor와 TreatmentDate 연관관계 해제
        List<TreatmentDate> treatmentDatesFindByDoc = treatmentDateRepository.findByDoctor(doctor);
        for (TreatmentDate treatmentDateFindByDoc : treatmentDatesFindByDoc) {
            treatmentDateRepository
                    .findOne(treatmentDateFindByDoc.getId())   //변경 감지를 위하여 id로 조회해옴
                    .setDoctor(null);
        }

        // Doctor와 Reserve 연관관계 해제
        List<Reserve> reserves = reserveService.findReserves();
        for (Reserve reserve : reserves) {
            if (reserve.getDepartment().getId().compareTo(doctor.getId()) == 0) {
                reserveService.updateDepartment(reserve.getId(), null); /** 더티체킹을 통한 업데이트 */
            }
        }

        doctorService.removeDoctor(doctor);

        return "redirect:/admin/edit/doctor/delete";
    }

    @RequestMapping("/admin/edit/doctor/information")
    public String changeDepartment(Model model) {
        List<Doctor> doctors = doctorService.findDoctors();

        log.info("edit doctor info");
        model.addAttribute("doctors", doctors);
        return "doctor/changeDoctor";
    }

    @GetMapping("/admin/edit/doctor/{id}")
    public String changeDepartmentInformationGet(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.findDoctor(id);
        List<Department> departments = departmentService.findDepartments();

        model.addAttribute("departments", departments);
        model.addAttribute("doctor", doctor);
        model.addAttribute("form", new DoctorForm());

        log.info("change doctor information");
        return "doctor/changeDoctorInfo";
    }

    @PostMapping("/admin/edit/doctor/{id}")
    public String changeDepartmentInformationPost(@PathVariable Long id, @Valid DoctorForm form) {
        Doctor doctor = doctorService.findDoctor(id);

        String name = form.getName();
        int career = form.getCareer();
        Department department = departmentService.findDepartment(form.getDepartmentId());

        doctorService.update(id, name, career,department);

        return "redirect:/admin/edit/doctor/information";
    }
}
