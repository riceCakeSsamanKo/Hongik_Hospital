package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Patient {

    @Id @GeneratedValue
    @Column(name = "patient_id")
    private Long id;

    private String name;
    private int age;
    @Enumerated(value = STRING)
    private GenderType gender;

    @Embedded
    private LogInInformation logIn;

    @OneToMany(mappedBy = "patient",cascade = ALL)
    List<Reserve> reserves = new ArrayList<>();

    // 생성자
    public Patient(String name, int age, GenderType gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setSex(GenderType gender) {
        this.gender = gender;
    }
    public void setLogIn(String id, String pw) {
        this.logIn = new LogInInformation(id, pw);
    }
}