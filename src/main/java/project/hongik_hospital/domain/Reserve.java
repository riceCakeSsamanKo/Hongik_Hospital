package project.hongik_hospital.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
public class Reserve {

    @Id @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
