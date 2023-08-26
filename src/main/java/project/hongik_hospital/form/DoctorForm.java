package project.hongik_hospital.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.hongik_hospital.domain.Department;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class DoctorForm {

    private Long doctorId;

    @NotEmpty(message = "이름은 필수 입니다. 공백이나 빈 문자열은 허용되지 않습니다")
    private String name;
    private int career;
    private Long departmentId;

    public DoctorForm(String name, int career, Long departmentId) {
        this.name = name;
        this.career = career;
        this.departmentId = departmentId;
    }
}
