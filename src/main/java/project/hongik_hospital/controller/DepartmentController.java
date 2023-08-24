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
import project.hongik_hospital.domain.Hospital;
import project.hongik_hospital.form.DepartmentForm;
import project.hongik_hospital.repository.DepartmentRepository;
import project.hongik_hospital.service.HospitalService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final HospitalService hospitalService;

    @RequestMapping("/admin/edit/department")
    public String editDepartment() {

        log.info("edit department");
        return "department/editDepartment";
    }

    @GetMapping("/admin/edit/department/add")
    public String addDepartment(Model model) {

        model.addAttribute("form", new DepartmentForm());

        log.info("add department");
        return "department/addDepartment";
    }

    @PostMapping("/admin/edit/department/add")
    public String addDepartmentToDB(@Valid DepartmentForm form, Model model, BindingResult result) {
        String name = form.getName();
        String phoneNumber = form.getPhoneNumber();

        // 이미 존재하는 부서인지 확인
        Optional<Department> findByName = departmentRepository.findByName(name);
        Optional<Department> findByPhoneNumber = departmentRepository.findByPhoneNumber(phoneNumber);
        if (findByName.isPresent() || findByPhoneNumber.isPresent()) {
            result.rejectValue("name", "invalid", "Department is already existed");
            result.rejectValue("phoneNumber", "invalid", "Department is already existed");


            model.addAttribute("isAlreadyExist", true);
            model.addAttribute("form", new DepartmentForm());
            return "department/addDepartment";
        }

        Department department = new Department(name, phoneNumber);

        // 병원과 의존관계 매핑
        Hospital hospital = hospitalService.findHospitals().get(0);
        hospital.addDepartment(department);

        departmentRepository.save(department);

        return "department/editDepartment";
    }
}
