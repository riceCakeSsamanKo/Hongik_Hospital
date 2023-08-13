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
import project.hongik_hospital.service.PatientService;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PatientController {

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
        return "redirect:/";
    }
}
