package project.hongik_hospital.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
public class Department {

    @Id @GeneratedValue
    private Long id;

    private String call;

    @OneToMany(mappedBy = "department")
    private List<Doctor> doctor = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
