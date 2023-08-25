package project.hongik_hospital.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.hongik_hospital.domain.Hospital;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DepartmentForm {

    private Long departmentId;

    @NotEmpty(message = "이름은 필수 입니다. 공백이나 빈 문자열은 허용되지 않습니다")
    private String name;
    @NotEmpty(message = "비밀번호는 필수 입니다. 공백이나 빈 문자열은 허용되지 않습니다")
    private String phoneNumber;

    public DepartmentForm(Long id, String name, String phoneNumber) {
        this.departmentId = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
