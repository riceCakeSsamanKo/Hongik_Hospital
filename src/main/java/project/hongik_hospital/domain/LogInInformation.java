package project.hongik_hospital.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogInInformation {

    @NotEmpty(message = "아이디는 필수입니다")
    private String login_id;
    @NotEmpty(message = "비밀번호는 필수입니다")
    private String login_pw;

    protected LogInInformation(String id, String pw) {
        this.login_id = id;
        this.login_pw = pw;
    }
}
