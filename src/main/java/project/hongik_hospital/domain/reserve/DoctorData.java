package project.hongik_hospital.domain.reserve;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.hongik_hospital.domain.Doctor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

// Reserve에서 Doctor의 정보만 담는 Embedded 타입
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class DoctorData {
    private String doctorName;
    private int doctorCareer;
    private Long doctorId;

    public DoctorData(Doctor doctor) {
        this.doctorName = doctor.getName();
        this.doctorCareer = doctor.getCareer();
        this.doctorId = doctor.getId();
    }

    public DoctorData(String doctorName, int doctorCareer) {
        this.doctorName = doctorName;
        this.doctorCareer = doctorCareer;
    }

    public DoctorData(String doctorName, int doctorCareer, Long doctorId) {
        this.doctorName = doctorName;
        this.doctorCareer = doctorCareer;
        this.doctorId = doctorId;
    }
}
