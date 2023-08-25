package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.domain.Hospital;
import project.hongik_hospital.domain.Reserve;
import project.hongik_hospital.form.DepartmentForm;
import project.hongik_hospital.repository.DepartmentRepository;
import project.hongik_hospital.repository.update.DoctorUpdateRepository;
import project.hongik_hospital.repository.update.ReserveUpdateRepository;
import project.hongik_hospital.service.DepartmentService;
import project.hongik_hospital.service.DoctorService;
import project.hongik_hospital.service.HospitalService;
import project.hongik_hospital.service.ReserveService;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final ReserveService reserveService;
    private final DoctorUpdateRepository doctorUpdateRepository;
    private final ReserveUpdateRepository reserveUpdateRepository;

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

        return "department/deleteDepartment";
    }

    @PostMapping("/admin/edit/department/delete")
    public String deleteDepartmentDelete(DepartmentForm form) {

        System.out.println("form.getId= " + form.getDepartmentId());
        Department department = departmentService.findDepartment(form.getDepartmentId());
        System.out.println("department.getId() = " + department.getId());

        // DOCTOR와 RESERVE 연관관계 해제
        List<Doctor> doctors = doctorService.findDoctors();
        for (Doctor doctor : doctors) {
            if (doctor.getDepartment().getId().compareTo(department.getId()) == 0) {
                doctorUpdateRepository.updateDepartment(doctor.getId(), null);
            }
        }

        List<Reserve> reserves = reserveService.findReserves();
        for (Reserve reserve : reserves) {
            if (reserve.getDepartment().getId().compareTo(department.getId()) == 0) {
                reserveUpdateRepository.updateDepartment(reserve.getId(),null);
            }
        }

        departmentService.removeDepartment(department);

        return "redirect:/admin/edit/department/delete";
    }
}
