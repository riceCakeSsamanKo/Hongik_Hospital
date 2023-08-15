package project.hongik_hospital.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class TreatmentDate {

    @Id
    @GeneratedValue
    @Column(name = "treatmentdate_id")
    private Long id;

    private int month;
    private int date;
    private int hour;
    private int minute;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public TreatmentDate(int month, int date, int hour, int minute) {
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }
    static public TreatmentDate createTreatmentDate(int month, int date, int hour, int minute) {
        return new TreatmentDate(month, date, hour, minute);
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    //비교 메서드
    public boolean compare(TreatmentDate treatmentDate) {
        if (this.hour == treatmentDate.hour ||
                this.date == treatmentDate.hour ||
                this.hour == treatmentDate.hour ||
                this.minute == treatmentDate.minute)
        {
            return true;
        }
        return false;
    }
}
