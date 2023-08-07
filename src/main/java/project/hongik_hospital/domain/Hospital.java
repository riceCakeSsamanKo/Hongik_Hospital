package project.hongik_hospital.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
public class Hospital {

    @Id @GeneratedValue
    @Column(name = "hospital_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "hospital", cascade = ALL)
    private List<Department> departments = new ArrayList<>();
}