package project.hongik_hospital.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class TreatmentDate {

    @Id @GeneratedValue
    private Long id;

    private String year;
    private String month;
    private String date;
    private String time;

    public TreatmentDate(String year, String month, String date, String time) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.time = time;
    }
}
