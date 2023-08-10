package project.hongik_hospital.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class HospitalDate {

    @Id @GeneratedValue
    private Long id;

    private String year;
    private String month;
    private String date;
    private String time;

    @OneToMany(mappedBy = "treatmentDate")
    @JoinColumn(name = "doctor_id")
    TreatmentDate treatmentDate;

    public HospitalDate(String year, String month, String date, String time) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.time = time;
    }
}
