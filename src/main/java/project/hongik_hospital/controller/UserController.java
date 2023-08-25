package project.hongik_hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.User;
import project.hongik_hospital.form.UserForm;
import project.hongik_hospital.repository.UserRepository;
import project.hongik_hospital.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static project.hongik_hospital.domain.AccountType.ADMIN;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user/new")
    public String createForm(Model model) {

        model.addAttribute("form", new UserForm());
        log.info("sign up");

        return "user/createUserForm";
    }

    @PostMapping("/user/new")
    public String create(@Valid UserForm form, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "user/createUserForm";
        }

        // 이미 가입된 아이딘지 확인하는 로직
        Optional<User> optionalUser = userService.findUserByLoginId(form.getLogin_id());
        if (optionalUser.isPresent()) {
            result.rejectValue("login_id", "invalid", "Login id already exists");
            model.addAttribute("form", new UserForm());
            model.addAttribute("isIdAlreadyExist", true);
            return "user/createUserForm";
        }

        User user = new User(form.getName(), parseInt(form.getAge()), form.getGender());
        user.setLogIn(form.getLogin_id(), form.getLogin_pw());

        userService.join(user);
        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String signIn(Model model) {
        model.addAttribute("form", new UserForm());
        log.info("Login page");
        return "loginForm";
    }

    @PostMapping("/user/login")
    public String beforeSignIn(@Valid UserForm form, BindingResult result, HttpSession session, Model model) {
        // Check if the login credentials are valid
        Optional<User> findUser = userService.findUser(form.getLogin_id(), form.getLogin_pw());

        if (findUser.isEmpty()) {  // 등록된 회원이 없는 경우
            result.rejectValue("login_id", "invalid", "Invalid login credentials");
            model.addAttribute("form", new UserForm());
            model.addAttribute("isSignInFail", true); // 등록된 회원이 아닌 경우 경고 발생
            return "loginForm";
        }

        User user = findUser.get();
        // Set the 'loggedIn' attribute to true and store the user's information
        model.addAttribute("user", user);
        model.addAttribute("loggedIn", true);

        // 사용자 정보를 세션에 저장
        session.setAttribute("loggedInUser", user);

        if (user.getAccountType() == ADMIN) {
            // 운영자 전용 페이지로 이동
            return "redirect:/admin/home";
        }

        log.info("Logged in: " + user.getName());
        return "redirect:/"; // Redirect to the home page
    }

    @GetMapping("/user/logout")
    public String userLogout(HttpSession session, Model model) {
        // Clear the session or do necessary logout operations

        model.addAttribute("loggedIn", false);

        // 사용자 정보를 세션에서 제거
        session.removeAttribute("loggedInUser");

        log.info("Logged out");
        return "redirect:/"; // Redirect to the home page
    }

    @GetMapping("/user")
    public String userInfo(HttpSession session, Model model) {
        // 로그인한 회원 조회
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("form", new UserForm());
            model.addAttribute("needToSignIn", true);
            return "loginForm";
        }

        // 로그인한 회원의 id로 회원 엔티티 조회(그냥 loggedInUser를 view에 넘기게 된다면 해당 엔티티가 수정된 경우에도 화면에는 수정전 데이터가 표시됨)
        // 즉 더티체킹의 영향을 받지 않는 똑같은 객체만 사용할 수 밖에 없어서 사용하려는 id는 동일하므로 db에서 새롭게 조회한다.
        User user = userService.findUser(loggedInUser.getId());
        model.addAttribute("user", user);

        log.info("User info: " + loggedInUser.getLogIn().getLogin_id());
        return "user/information";
    }

    @GetMapping("/user/{userId}/edit")
    // 보안적인 측면에서 본다면 {userId}를 외부로 노출하지 않고 HttpSession 에서 getId 해서 회원 조회하는 것이 보안적인 측면에서 좋을듯?
    public String changeUserInfo(@PathVariable Long userId, Model model) {
        // 로그인한 회원 조회
        User loggedInUser = userService.findUser(userId);
        model.addAttribute("user", loggedInUser);

        UserForm userForm = new UserForm();
        userForm.setUserId(userId);
        model.addAttribute("form", userForm);

        log.info("Change info: " + loggedInUser.getLogIn().getLogin_id());
        return "user/editInformation";
    }

    @PatchMapping("/user/{userId}/edit") //부분 업데이트시에는 Patch, 전체 업데이트시에는 Put, 새로운 데이터 생성시에는 Post
    public String updateUserInfo(@PathVariable Long userId, @Valid UserForm form) {

        // 로그인한 회원 조회
        User user = userService.findUser(userId);

        // form으로 새롭게 가져온 데이터
        String loginId = user.getLogIn().getLogin_id();  //id는 변경하지 않음
        String loginPw = form.getLogin_pw();
        String name = form.getName();
        int age = parseInt(form.getAge());
        GenderType gender = form.getGender();

        userService.update(userId, loginId, loginPw, name, age, gender);

        return "redirect:/";
    }

    @GetMapping("/user/delete")
    public String deleteUser(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("form", new UserForm());
            model.addAttribute("needToSignIn", true);
            return "loginForm";
        }

        return "user/deleteUser";
    }

    @DeleteMapping("/user/delete")
    public String delete(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        User user = userService.findUser(loggedInUser.getId());
        if (user != null) {
            // 여기서 데이터베이스에서 사용자 정보를 삭제
            if (userService.removeUser(user.getId())) { // 이 부분은 removeUser 메서드의 반환값에 따라 수정되어야 할 수 있습니다.
                // 사용자 정보를 세션에서 제거
                session.removeAttribute("loggedInUser");
            }
        }

        return "redirect:/";
    }

    @RequestMapping("/admin/edit/user")
    public String editUserInfo(Model model) {
        List<User> users = userRepository.findAll();
        List<UserForm> userForms = users.stream().map(u -> new UserForm(u)).collect(Collectors.toList());

        model.addAttribute("users", userForms);
        return "admin/editUser";
    }

    @GetMapping("/admin/edit/user/{userId}")
    public String getUserInfo(@PathVariable Long userId, Model model) {
        User user = userService.findUser(userId);

        model.addAttribute("user", user);
        model.addAttribute("form", new UserForm());

        log.info("Change info: " + user.getLogIn().getLogin_id());

        return "admin/editUserInfo";
    }

    @PostMapping("/admin/edit/user/{userId}")
    public String postUserInfo(@Valid UserForm form, @PathVariable Long userId) {
        User user = userService.findUser(userId);

        // form으로 새롭게 가져온 데이터
        String loginId = user.getLogIn().getLogin_id();  //id는 변경하지 않음
        String loginPw = form.getLogin_pw();
        String name = form.getName();
        int age = parseInt(form.getAge());
        GenderType gender = form.getGender();

        userService.update(userId, loginId, loginPw, name, age, gender);

        return "redirect:/admin/edit/user";
    }
}

