package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hongik_hospital.domain.Patient;
import project.hongik_hospital.form.PatientForm;
import project.hongik_hospital.repository.PatientRepository;
import project.hongik_hospital.service.PatientService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Optional;

import static java.lang.Integer.valueOf;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;
    private final PatientService patientService;

    @GetMapping("/patient/new")
    public String createForm(Model model) {

        model.addAttribute("form", new PatientForm());
        log.info("sign up");

        return "patient/createPatientForm";
    }

    @PostMapping("/patient/new")
    public String create(@Valid PatientForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "patient/createPatientForm";
        }

        Patient patient = new Patient(form.getName(), valueOf(form.getAge()), form.getGender());
        patient.setLogIn(form.getLogin_id(), form.getLogin_pw());

        patientService.join(patient);
        return "redirect:/";
    }

    @GetMapping("/patient/login")
    public String showLoginForm(Model model) {
        model.addAttribute("form", new PatientForm());
        log.info("Login page");
        return "loginForm";
    }

    @PostMapping("/patient/login")
    public String login(@Valid PatientForm form, BindingResult result, HttpSession session, Model model) {
        // Check if the login credentials are valid
        Optional<Patient> optioanlPatient = patientService.findPatient(form.getLogin_id(), form.getLogin_pw());

        if (optioanlPatient.isEmpty()) {
            result.rejectValue("login_id", "invalid", "Invalid login credentials");
            model.addAttribute("form", new PatientForm());
            model.addAttribute("isSignInFail", true);
            return "loginForm";
        }

        Patient patient = optioanlPatient.get();
        // Set the 'loggedIn' attribute to true and store the patient's information
        model.addAttribute("loggedIn", true);
        model.addAttribute("patient", patient);

        // 사용자 정보를 세션에 저장
        session.setAttribute("loggedInUser", patient);

        log.info("Logged in: " + patient.getName());
        return "redirect:/"; // Redirect to the home page
    }

    @GetMapping("/patient/logout")
    public String logout(HttpSession session, Model model) {
        // Clear the session or do necessary logout operations
        model.addAttribute("loggedIn", false);
        model.addAttribute("patient", null);

        // 사용자 정보를 세션에서 제거
        session.removeAttribute("loggedInUser");

        log.info("Logged out");
        return "redirect:/"; // Redirect to the home page
    }

    @RequestMapping("/patient/information")
    public String info(HttpSession session, Model model) {
        // 로그인한 회원 조회
        Patient loggedInUser = (Patient) session.getAttribute("loggedInUser");
        model.addAttribute("patient", loggedInUser);

        log.info("User info: " + loggedInUser.getLogIn().getLogin_id());
        return "patient/information";
    }

    @GetMapping("/patient/information/change")
    public String changeInfo(HttpSession session, Model model) {
        // 로그인한 회원 조회
        Patient loggedInUser = (Patient) session.getAttribute("loggedInUser");

        model.addAttribute("patient", loggedInUser);
        model.addAttribute("form", new PatientForm());

        log.info("Change info: " + loggedInUser.getLogIn().getLogin_id());
        return "patient/changeInformation";
    }

    @PostMapping("/patient/information/change")
    public String updateInfo(@Valid PatientForm form, HttpSession session, Model model) {

        // form으로 새롭게 가져온 데이터
        String loginId = form.getLogin_id();
        String loginPw = form.getLogin_pw();
        String name = form.getName();
        String age = form.getAge();

        // 로그인한 회원 조회
        Patient loggedInUser = (Patient) session.getAttribute("loggedInUser");

        if (loginId != null && loginPw != null) {
            loggedInUser.setLogIn(loginId, loginPw);
        } else if (loginId != null && loginPw == null) {
            loggedInUser.setLogIn(loginId, loggedInUser.getLogIn().getLogin_pw());
        } else if (loginId == null && loginPw != null) {
            loggedInUser.setLogIn(loggedInUser.getLogIn().getLogin_pw(), loginPw);
        } else {
            // 변경 안함
        }

        if (name != null) {
            loggedInUser.setName(name);
        }

        if (age != null) {
            loggedInUser.setAge(valueOf(age));
        }

        return "redirect:/";
    }

//    @DeleteMapping("/patient/delete")
//    public String delete(Mod)
}

