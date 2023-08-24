package project.hongik_hospital.form;

import lombok.Data;
import project.hongik_hospital.domain.Hospital;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentForm {

    @NotEmpty(message = "이름은 필수 입니다. 공백이나 빈 문자열은 허용되지 않습니다")
    private String name;
    @NotEmpty(message = "비밀번호는 필수 입니다. 공백이나 빈 문자열은 허용되지 않습니다")
    private String phoneNumber;
}
