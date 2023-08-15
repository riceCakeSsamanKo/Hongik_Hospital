package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.hongik_hospital.form.ReserveForm;
import project.hongik_hospital.service.ReserveService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;

    @GetMapping("/reserve/new")
    public String createReserve(Model model) {

        model.addAttribute(new ReserveForm());
        return "reserve/createReserveForm";
    }
}
