package project.hongik_hospital.form;

import lombok.Getter;
import lombok.Setter;
import project.hongik_hospital.domain.GenderType;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
public class PatientForm {

    private Long patientId;
    private String login_id;

    @NotEmpty(message = "비밀번호는 필수입니다")
    private String login_pw;

    @NotEmpty(message = "회원 이름은 필수입니다")
    private String name;

    @NotEmpty(message = "회원 나이는 필수입니다")
    private String age;

    @Enumerated(value = STRING)
    private GenderType gender;
}
