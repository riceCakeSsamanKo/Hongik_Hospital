package project.hongik_hospital.repository;

import org.springframework.stereotype.Repository;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Doctor doctor) {
        em.persist(doctor);
    }

    public Doctor findOne(Long doctorId) {
        return em.find(Doctor.class, doctorId);
    }

    public List<Doctor> findAll() {
        return em.createQuery("select d from Doctor d " +
                        "join fetch d.department dp",Doctor.class)
                .getResultList();
    }

    public List<Doctor> findByName(String name) {
        return em.createQuery("select d from Doctor d " +
                        "join fetch d.department " +
                        "where d.name = :name", Doctor.class)
                .setParameter("name", name)
                .getResultList();
    }
}
