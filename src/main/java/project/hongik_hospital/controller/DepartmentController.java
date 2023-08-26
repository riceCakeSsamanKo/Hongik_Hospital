package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.domain.Hospital;
import project.hongik_hospital.domain.Reserve;
import project.hongik_hospital.form.DepartmentForm;

import project.hongik_hospital.service.DepartmentService;
import project.hongik_hospital.service.DoctorService;
import project.hongik_hospital.service.HospitalService;
import project.hongik_hospital.service.ReserveService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final ReserveService reserveService;

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
        Optional<Department> findByName = departmentService.findDepartmentByName(name);
        Optional<Department> findByPhoneNumber = departmentService.findDepartmentByPhoneNumber(phoneNumber);
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

        departmentService.join(department);

        return "department/editDepartment";
    }

    @GetMapping("/admin/edit/department/delete")
    public String deleteDepartment(Model model) {
        List<Department> departments = departmentService.findDepartments();
        model.addAttribute("departments", departments);
        model.addAttribute("form", new DepartmentForm());

        log.info("delete department");

        return "department/deleteDepartment";
    }

    @DeleteMapping("/admin/edit/department/delete")
    public String deleteDepartmentDelete(DepartmentForm form) {

        Department department = departmentService.findDepartment(form.getDepartmentId());

        // DOCTOR와 RESERVE 연관관계 해제
        List<Doctor> doctors = doctorService.findDoctors();
        for (Doctor doctor : doctors) {
            if (doctor.getDepartment().getId().compareTo(department.getId()) == 0) {
                doctorService.updateDepartment(doctor.getId(), null);  /** 더티체킹을 통한 업데이트 */
            }
        }

        List<Reserve> reserves = reserveService.findReserves();
        for (Reserve reserve : reserves) {
            if (reserve.getDepartment().getId().compareTo(department.getId()) == 0) {
                reserveService.updateDepartment(reserve.getId(), null); /** 더티체킹을 통한 업데이트 */
            }
        }

        departmentService.removeDepartment(department);

        return "redirect:/admin/edit/department/delete";
    }

    @RequestMapping("/admin/edit/department/information")
    public String changeDepartment(Model model) {
        List<Department> departments = departmentService.findDepartments();

        log.info("edit department");
        model.addAttribute("departments", departments);
        return "department/changeDepartment";
    }

    @GetMapping("/admin/edit/department/{id}")
    public String changeDepartmentInformationGet(@PathVariable Long id, Model model) {
        Department department = departmentService.findDepartment(id);
        model.addAttribute("department", department);
        model.addAttribute("form", new DepartmentForm());

        log.info("change Department information");
        return "department/changeDepartmentInfo";
    }

    @PostMapping("/admin/edit/department/{id}")
    public String changeDepartmentInformationPost(@PathVariable Long id, @Valid DepartmentForm form, Model model, BindingResult result) {
        Department department = departmentService.findDepartment(id);

        String name = form.getName();
        String phoneNumber = form.getPhoneNumber();

        if (departmentService.findDepartmentByName(name).isPresent()) {

            result.rejectValue("name", "invalid", "Department is already existed");

            model.addAttribute("department", department);
            model.addAttribute("form", new DepartmentForm());
            model.addAttribute("isAlreadyExistName", true);

            log.info("change department information fail - name already exist");
            return "department/changeDepartmentInfo";

        }else if(departmentService.findDepartmentByPhoneNumber(phoneNumber).isPresent()){
            result.rejectValue("phoneNumber", "invalid", "PhoneNumber is already used");

            model.addAttribute("department", department);
            model.addAttribute("form", new DepartmentForm());
            model.addAttribute("isAlreadyExistPhoneNumber", true);

            log.info("change department information fail - phone number already exist");
            return "department/changeDepartmentInfo";
        }

        departmentService.update(id,name,phoneNumber);

        return "redirect:/admin/edit/department/information";
    }
}
