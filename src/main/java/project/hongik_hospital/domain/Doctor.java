package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Doctor {

    @Id @GeneratedValue
    @Column(name = "doctor_id")
    private Long id;

    private String name;
    private int career;
    private List<TreatmentDate> treatmentDates = new ArrayList<>();
    /*// reserve에서 예약이 들어오는 경우 doctor의 treatmentDate에 해당하는 날짜가 비어있다면 컬렉션에 해당 날짜 추가
    @ElementCollection // 값 타입 컬렉션을 사용하기 위한 어노테이션
    // DB는 컬렉션을 같은 테이블(DOCTOR)에 저장할 수 없다. => @CollectionTable: 별도의 테이블 생성
    @CollectionTable(name = "TREATMENT_DATE", // TREATMENT_DATE 테이블을 따로 생성함
            joinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<LocalDateTime> treatmentDate =new ArrayList<>();*/


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // 생성자
    public Doctor(String name, int career) {
        this.name = name;
        this.career = career;
    }

    // setter
    public void setName(String doctorName){this.name = doctorName;}
    public void setCareer(int career) {
        this.career = career;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
