package project.hongik_hospital.repository;

import org.springframework.stereotype.Repository;
import project.hongik_hospital.domain.Hospital;
import project.hongik_hospital.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HospitalRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Hospital hospital) {
        em.persist(hospital);
    }

    public Hospital findOne(Long hospitalId) {
        return em.find(Hospital.class, hospitalId);
    }

    public List<Hospital> findAll() {
        return em.createQuery("select h from Hospital h",Hospital.class)
                .getResultList();
    }

    public List<Hospital> findByName(String name) {
        return em.createQuery("select h from Hospital h where h.name = :name", Hospital.class)
                .setParameter("name", name)
                .getResultList();
    }
}
