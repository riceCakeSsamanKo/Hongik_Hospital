package project.hongik_hospital.form;

import lombok.Data;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.domain.ReserveStatus;

import java.util.List;

@Data
public class ReserveForm {

    private Long departmentId;
    private Long doctorId;
    private int month;
    private int date;
    private int hour;
    private int minute;
    private ReserveStatus status;
    private int fee;
}
