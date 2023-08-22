package project.hongik_hospital.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReserveDto {
    private Long id;
    private User user;
    private Doctor doctor;
    private Department department;
    private Hospital hospital;
    private LocalDateTime reserveDate;
    private String treatmentDate;
    private ReserveStatus reserveStatus;

    public ReserveDto(Reserve reserve) {
        id = reserve.getId();
        user = reserve.getUser();
        doctor = reserve.getDoctor();
        department = reserve.getDepartment();
        hospital = reserve.getHospital();
        reserveDate = reserve.getReserveDate();
        reserveStatus = reserve.getReserveStatus();

        TreatmentDate treatment = reserve.getTreatmentDate();

        String month = Integer.toString(treatment.getMonth());
        String date = Integer.toString(treatment.getDate());
        String hour = Integer.toString(treatment.getHour());
        String minute = Integer.toString(treatment.getMinute());

        if (treatment.getMonth() < 10) {
            month = "0" + month;
        }
        if (treatment.getDate() < 10) {
            date = "0" + date;
        }
        if (treatment.getHour() < 10) {
            hour = "0" + hour;
        }
        if (treatment.getMinute() < 10) {
            minute = "0" + minute;
        }

        treatmentDate = month + "-" + date + "-" + hour + ":" + minute;


    }
}
