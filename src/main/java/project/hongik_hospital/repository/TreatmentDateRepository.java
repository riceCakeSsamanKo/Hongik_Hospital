package project.hongik_hospital.repository;

import org.springframework.stereotype.Repository;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.domain.TreatmentDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TreatmentDateRepository {

    @PersistenceContext
    EntityManager em;

    public void save(TreatmentDate treatmentDate) {
        em.persist(treatmentDate);
    }

    public TreatmentDate findOne(Long treatmentDateId) {
        return em.find(TreatmentDate.class, treatmentDateId);
    }

    public List<TreatmentDate> findAll() {
        return em.createQuery("select t from TreatmentDate t " +
                        "join fetch t.doctor d",TreatmentDate.class)
                .getResultList();
    }

    public List<TreatmentDate> findByTime(int month, int date, int hour, int minute) {
        return em.createQuery("select t from TreatmentDate t " +
                                "join fetch t.doctor d " +
                                "where t.month = :month " +
                                "and t.date = :date " +
                                "and t.hour=:hour " +
                                "and t.minute = :minute",
                        TreatmentDate.class)
                .setParameter("month", month)
                .setParameter("date", date)
                .setParameter("hour", hour)
                .setParameter("minute", minute)
                .getResultList();
    }

    public List<TreatmentDate> findByDoctor(Doctor doctor) {
        return em.createQuery("select t from TreatmentDate t " +
                        "where t.doctor =: doctor",
                        TreatmentDate.class)
                .setParameter("doctor", doctor)
                .getResultList();
    }

    public void remove(Long treatmentDateId) {
        em.createQuery("DELETE FROM TreatmentDate td WHERE td.id = :id")
                .setParameter("id", treatmentDateId)
                .executeUpdate();
    }
}
