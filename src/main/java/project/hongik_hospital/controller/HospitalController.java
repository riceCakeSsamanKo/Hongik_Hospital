package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hongik_hospital.domain.Address;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Hospital;
import project.hongik_hospital.domain.User;
import project.hongik_hospital.form.UserForm;
import project.hongik_hospital.service.HospitalService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @RequestMapping("/hospital")
    public String hospitalInfo(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("form", new UserForm());
            model.addAttribute("needToSignIn", true);
            return "loginForm";
        }

        log.info("hospital Home");
        return "hospital/hospitalHome";
    }

    @RequestMapping("/hospital/address")
    public String hospitalAddress(Model model) {

        Hospital hospital = hospitalService.findHospitals("Hongik Hospital").get(0);
        Address address = hospital.getAddress();
        model.addAttribute("address", address);

        log.info("hospital address information");
        return "hospital/addressInfo";
    }

    @RequestMapping("/hospital/department")
    public String hospitalDepartment(Model model) {
        Hospital hospital = hospitalService.findHospitals().get(0);
        List<Department> departments = hospital.getDepartments();

        model.addAttribute("departments", departments);

        log.info("hospital department information");

        return "hospital/departmentInfo";
    }
}
