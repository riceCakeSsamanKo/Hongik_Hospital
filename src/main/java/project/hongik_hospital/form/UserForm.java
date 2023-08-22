package project.hongik_hospital.form;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.hongik_hospital.domain.AccountType;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.User;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.EnumType.STRING;

@Data
@NoArgsConstructor
public class UserForm {

    private Long userId;  //기본키
    
    private String login_id;

    @NotEmpty(message = "비밀번호는 필수입니다")
    private String login_pw;

    @NotEmpty(message = "회원 이름은 필수입니다")
    private String name;

    @NotEmpty(message = "회원 나이는 필수입니다")
    private String age;

    @Enumerated(value = STRING)
    private GenderType gender;

    @Enumerated(value = STRING)
    private AccountType accountType;

    private boolean isEdited = false;  //수정된 경우 true로 변경

    public UserForm(User user) {
        this.userId = user.getId();
        this.login_id = user.getLogIn().getLogin_id();
        this.login_pw = user.getLogIn().getLogin_pw();
        this.name = user.getName();
        this.age = String.valueOf(user.getAge());
        this.gender = user.getGender();
        this.accountType = user.getAccountType();
    }
}
