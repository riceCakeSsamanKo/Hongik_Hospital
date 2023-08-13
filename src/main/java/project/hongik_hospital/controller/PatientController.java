package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.hongik_hospital.domain.Patient;
import project.hongik_hospital.form.PatientForm;
import project.hongik_hospital.repository.PatientRepository;
import project.hongik_hospital.service.PatientService;

import javax.validation.Valid;

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

        Patient patient = new Patient(form.getName(), Integer.valueOf(form.getAge()), form.getGender());
        patient.setLogIn(form.getLogin_id(), form.getLogin_pw());

        patientService.join(patient);
        log.info("home page");
        return "redirect:/";
    }

    @GetMapping("/patient/login")
    public String showLoginForm(Model model) {
        model.addAttribute("form", new PatientForm());
        log.info("Login page");
        return "loginForm";
    }

    @PostMapping("/patient/login")
    public String login(@Valid PatientForm form, BindingResult result, Model model) {
        // Check if the login credentials are valid
        Patient patient = patientService.findPatient(form.getLogin_id(), form.getLogin_pw());

        if (patient == null) {
            result.rejectValue("login_id", "invalid", "Invalid login credentials");
            return "loginForm";
        }

        // Set the 'loggedIn' attribute to true and store the patient's information
        model.addAttribute("loggedIn", true);
        model.addAttribute("patient", patient);

        log.info("Logged in: " + patient.getName());
        log.info("home page");
        return "home"; // Redirect to the home page
    }

    @GetMapping("/patient/logout")
    public String logout(Model model) {
        // Clear the session or do necessary logout operations
        model.addAttribute("loggedIn", false);
        model.addAttribute("patient", null);

        log.info("Logged out");
        log.info("home page");
        return "home"; // Redirect to the home page
    }
}
