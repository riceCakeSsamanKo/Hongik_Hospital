package project.hongik_hospital.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
public class TreatmentDate {

    @Id @GeneratedValue
    @Column(name = "treatmentdate_id")
    private Long id;


}
