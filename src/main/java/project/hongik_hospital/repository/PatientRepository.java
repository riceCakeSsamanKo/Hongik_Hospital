package project.hongik_hospital.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Patient;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Patient patient) {
        em.persist(patient);
    }

    public void remove(Patient patient) {
        em.remove(patient);
    }

    public Patient findOne(Long patientId) {
        return em.find(Patient.class, patientId);
    }

    public List<Patient> findAll() {
        return em.createQuery("select p from Patient p",Patient.class)
                .getResultList();
    }

    public List<Patient> findByName(String name) {
        return em.createQuery("select p from Patient p where p.name = :name", Patient.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Optional<Patient> findByLogInfo(String login_id) {
        try {
            Patient result = em.createQuery("select p from Patient p " +
                            "where p.logIn.login_id = :id ", Patient.class)
                    .setParameter("id", login_id)
                    .getSingleResult();

            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Patient> findByLogInfo(String id, String pw) {
        try {
            Patient result = em.createQuery("select p from Patient p " +
                            "where p.logIn.login_id = :id " +
                            "and p.logIn.login_pw = :pw", Patient.class)
                    .setParameter("id", id)
                    .setParameter("pw", pw)
                    .getSingleResult();

            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
