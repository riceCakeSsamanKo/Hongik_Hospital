package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.form.DoctorForm;
import project.hongik_hospital.service.DepartmentService;
import project.hongik_hospital.service.DoctorService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

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
}
