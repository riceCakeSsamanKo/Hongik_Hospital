package project.hongik_hospital.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hongik_hospital.domain.AccountType;
import project.hongik_hospital.domain.User;
import project.hongik_hospital.form.UserForm;

import javax.servlet.http.HttpSession;

import static project.hongik_hospital.domain.AccountType.*;

@Controller
@Slf4j // logger 생성: Logger log = (Logger) LoggerFactory.getLogger(getClass());

public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        boolean loggedIn = session.getAttribute("loggedInUser") != null;
        model.addAttribute("loggedIn", loggedIn);


        if (loggedIn) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            model.addAttribute("isAdmin", loggedInUser.getAccountType()==ADMIN);
        }

        log.info("home page");
        return "home";
    }

    @GetMapping("/admin/home")
    public String adminHome(HttpSession session, Model model) {
        // 로그인한 회원 조회
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("form", new UserForm());
            model.addAttribute("needToSignIn", true);
            return "loginForm";
        }

        log.info("admin home: "+loggedInUser.getLogIn().getLogin_id());
        return "admin/home";
    }
}

