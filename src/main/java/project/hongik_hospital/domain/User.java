package project.hongik_hospital.domain;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.PROTECTED;
import static project.hongik_hospital.domain.AccountType.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private int age;

    @Enumerated(value = STRING)
    private GenderType gender;
    @Enumerated(value = STRING)  //default는 USER
    private AccountType accountType = USER;

    @Embedded
    private LogInInformation logIn;

    @OneToMany(mappedBy = "user",cascade = ALL)
    List<Reserve> reserves = new ArrayList<>();

    // 생성자
    public User(String name, int age, GenderType gender) {
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
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    // 비즈니스 로직
    public void update(String loginId, String loginPw, String name, int age, GenderType gender) {
        setLogIn(loginId, loginPw);
        setName(name);
        setAge(age);
        setSex(gender);
    }
}