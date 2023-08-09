package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reserve {

    @Id @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    private LocalDateTime reserveDate;
    private int fee;

    @Override
    public String toString() {
        return "Reserve{" +
                "id=" + id +
                ", patient=" + patient.getName() +
                ", doctor=" + doctor.getName() +
                ", department=" + department.getName() +
                ", hospital=" + hospital.getName() +
                ", reserveDate=" + reserveDate +
                ", fee=" + fee +
                ", reserveStatus=" + reserveStatus +
                '}';
    }

    @Enumerated(value = STRING)
    private ReserveStatus reserveStatus;

    // 생성 메서드
    public static Reserve createReserve(Patient patient, Doctor doctor, LocalDateTime reserveDate) {
        Reserve reserve = new Reserve();
        reserve.setPatient(patient);
        reserve.setDoctor(doctor);

        if (doctor.getDepartment() == null) {
            throw new IllegalStateException("오류! 의사의 소속 부서가 없습니다");
        }
        if (doctor.getDepartment().getHospital() == null) {
            throw new IllegalStateException("오류! 의사의 소속 병원이 없습니다");
        }

        reserve.setDepartment(doctor.getDepartment());
        reserve.setHospital(doctor.getDepartment().getHospital());

        // fee는 reserveStatus가 COMPLETE인 경우에 책정 하기로 함 ㅇㅇ.
        reserve.setReserveDate(reserveDate);
        reserve.setReserveStatus(ReserveStatus.RESERVE);

        return reserve;
    }

    // setter
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
    public void setReserveDate(LocalDateTime reserveDate) {
        this.reserveDate = reserveDate;
    }
    public void setReserveStatus(ReserveStatus reserveStatus) {
        this.reserveStatus = reserveStatus;
    }
    public void setFee(int fee){this.fee = fee;}
    
    // 연관관계 편의 메서드
    public void setPatient(Patient patient) {
        this.patient = patient;
        patient.getReserves().add(this);
    }
}