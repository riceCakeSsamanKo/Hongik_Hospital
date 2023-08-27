package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import project.hongik_hospital.repository.TreatmentDateRepository;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;
import static project.hongik_hospital.domain.ReserveStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reserve {

    @Id @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "treatmentdate_id")
    private TreatmentDate treatmentDate;

    private LocalDateTime reserveDate;

    private int fee;

    @Enumerated(value = STRING)
    private ReserveStatus reserveStatus;

    // 생성 메서드
    public static Reserve createReserve(User user, Doctor doctor, LocalDateTime reserveDate, TreatmentDate treatmentDate) {

        // 예약 불가 조건
        if (doctor.getDepartment() == null) {
            throw new IllegalStateException("오류! 의사의 소속 부서가 없습니다");
        }
        if (doctor.getDepartment().getHospital() == null) {
            throw new IllegalStateException("오류! 의사의 소속 병원이 없습니다");
        }
        for(TreatmentDate date : doctor.getTreatmentDates()){
            // 의사의 treatmentDate 리스트에 이미 treamentDate에 해당하는 날짜가 존재한다면 예약 불가
            if (date.compare(treatmentDate)) {
                throw new IllegalStateException("오류! 이미 예약된 시간입니다.");
            }
        }
        doctor.addTreatmentDate(treatmentDate); //해당 시간에 예약이 없으니 예약가능.

        Reserve reserve = new Reserve();
        reserve.setTreatmentDate(treatmentDate);
        reserve.setUser(user);
        reserve.setDoctor(doctor);
        reserve.setDepartment(doctor.getDepartment());
        reserve.setHospital(doctor.getDepartment().getHospital());
        reserve.setReserveDate(reserveDate);
        reserve.setReserveStatus(RESERVE);
        reserve.setFee(0);

        // fee는 reserveStatus가 COMPLETE인 경우에 책정 하기로 함 ㅇㅇ.
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
    public void setTreatmentDate(TreatmentDate treatmentDate) {
        this.treatmentDate = treatmentDate;
    }
    public void setFee(int fee){this.fee = fee;}
    
    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
        user.getReserves().add(this);
    }

    // 비즈니스 로직
    public void cancel() {
        if (reserveStatus == COMPLETE) {
            throw new IllegalStateException("이미 완료된 진료는 취소가 불가합니다.");
        }
        setReserveStatus(CANCEL);
    }

    public void complete(int fee) {
        if (reserveStatus == CANCEL) {
            throw new IllegalStateException("이미 취소된 진료는 완료가 불가합니다.");
        }
        setReserveStatus(COMPLETE);
        setFee(fee);
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "id=" + id +
                ", user=" + user.getName() +
                ", doctor=" + doctor.getName() +
                ", department=" + department.getName() +
                ", hospital=" + hospital.getName() +
                ", reserveDate=" + reserveDate +
                ", fee=" + fee +
                ", reserveStatus=" + reserveStatus +
                '}';
    }
}