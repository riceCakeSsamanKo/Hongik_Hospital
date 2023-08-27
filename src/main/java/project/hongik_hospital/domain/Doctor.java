package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Doctor {

    @Id
    @GeneratedValue
    @Column(name = "doctor_id")
    private Long id;

    private String name;
    private int career;

    // 의사 진료 예약 시간
    @OneToMany(mappedBy = "doctor", cascade = ALL, orphanRemoval = true)
    private List<TreatmentDate> treatmentDates = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // 생성자
    public Doctor(String name, int career) {
        this.name = name;
        this.career = career;
    }

    // setter
    public void setName(String doctorName) {
        this.name = doctorName;
    }

    public void setCareer(int career) {
        this.career = career;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    // 연관관계 편의 메서드
    public void addTreatmentDate(TreatmentDate treatmentDate) {
        treatmentDates.add(treatmentDate);
        treatmentDate.setDoctor(this);
    }

    public void addTreatmentDate(int month, int date, int hour, int minute) {
        TreatmentDate treatmentDate = new TreatmentDate(month, date, hour, minute);
        treatmentDates.add(treatmentDate);
        treatmentDate.setDoctor(this);
    }

    // 비즈니스 로직
    public void cancelTreatment(TreatmentDate treatmentDate) {
        List<TreatmentDate> datesToRemove = new ArrayList<>();

        for (TreatmentDate date : treatmentDates) {
            if (date.compare(treatmentDate)) {
                datesToRemove.add(date);
            }
        }

        treatmentDates.removeAll(datesToRemove);
    }
}
