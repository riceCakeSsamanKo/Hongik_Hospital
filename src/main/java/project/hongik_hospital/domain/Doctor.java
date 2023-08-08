package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Doctor {

    @Id @GeneratedValue
    @Column(name = "doctor_id")
    private Long id;

    private String name;
    private int career;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // 생성자
    public Doctor(String name, int career) {
        this.name = name;
        this.career = career;
    }

    // setter
    public void setName(String doctorName){this.name = doctorName;}
    public void setCareer(int career) {
        this.career = career;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
