package project.hongik_hospital.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Patient {

    @Id @GeneratedValue
    @Column(name = "patient_id")
    private Long id;

    private int age;
    private String sex;
}