package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Department {

    @Id @GeneratedValue
    @Column(name = "department_id")
    private Long id;

    private String name;
    private String phoneNumber;

    @OneToMany(mappedBy = "department",cascade = ALL)
    private List<Doctor> doctors = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    // 생성자
    public Department(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    //* 연관 관계 편의 메서드 *//
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctor.setDepartment(this);
    }
}
