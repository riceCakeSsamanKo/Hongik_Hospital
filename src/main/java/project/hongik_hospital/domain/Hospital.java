package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class Hospital {

    @Id @GeneratedValue
    @Column(name = "hospital_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "hospital", cascade = ALL)
    private List<Department> departments = new ArrayList<>();

    public Hospital(String name, Address address, Department... departments) {
        this.name = name;
        this.address = address;
        for (Department department : departments) {
            addDepartment(department);
        }
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setAddress(String city, String street, String zipcode) {
        this.address = new Address(city, street, zipcode);
    }

    //* 연관관계 편의 메서드 *//
    public void addDepartment(Department department) {
        department.setHospital(this);
        departments.add(department);
    }
}