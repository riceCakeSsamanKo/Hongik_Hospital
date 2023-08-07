package project.hongik_hospital.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
public class Doctor {

    @Id @GeneratedValue
    @Column(name = "doctor_id")
    private Long id;

    private int career;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
