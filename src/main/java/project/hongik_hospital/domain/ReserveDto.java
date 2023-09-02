package project.hongik_hospital.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReserveDto {

    private Long id;

    // User
    private String userName;
    private int userAge;
    //Doctor
    private String doctorName;
    private int doctorCareer;
    //Department
    private String departmentName;
    //reserveDate
    private LocalDateTime reserveDate;
    //TreatmentDate
    private String treatmentDate;
    //ReserveStatus
    private ReserveStatus reserveStatus;
    //fee
    private int fee;

    public ReserveDto(Reserve reserve) {
        id = reserve.getId();

        userName = reserve.getUser().getName();
        userAge = reserve.getUser().getAge();

        try {
            doctorName = reserve.getDoctor().getName();
            doctorCareer = reserve.getDoctor().getCareer();
        } catch (NullPointerException e) {
            doctorName = null;
            doctorCareer = 0;
        }

        departmentName = reserve.getDepartment().getName();

        reserveDate = reserve.getReserveDate();
        reserveStatus = reserve.getReserveStatus();

        fee = reserve.getFee();

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
