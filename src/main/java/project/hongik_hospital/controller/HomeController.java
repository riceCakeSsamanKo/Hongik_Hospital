package project.hongik_hospital.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j // logger 생성: Logger log = (Logger) LoggerFactory.getLogger(getClass());

public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        boolean loggedIn = session.getAttribute("loggedInUser") != null;
        model.addAttribute("loggedIn", loggedIn);

        log.info("home page");
        return "home";
    }
}

