package project.hongik_hospital.repository;

import org.springframework.stereotype.Repository;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Reserve;
import project.hongik_hospital.domain.ReserveStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReserveRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Reserve reserve) {
        em.persist(reserve);
    }

    public Reserve findOne(Long reserveId) {
        return em.find(Reserve.class, reserveId);
    }

    public List<Reserve> findAll() {
        return em.createQuery("select r from Reserve r",Reserve.class)
                .getResultList();
    }

    public List<Reserve> findByPatientName(String patientName) {
        return em.createQuery("select r from Reserve r where r.patient.name = :patientName", Reserve.class)
                .setParameter("patientName", patientName)
                .getResultList();
    }

    public List<Reserve> findByStatus(ReserveStatus status) {
        return em.createQuery("select r from Reserve r where r.reserveStatus = :status",Reserve.class)
                .setParameter("status", status)
                .getResultList();
    }
}
