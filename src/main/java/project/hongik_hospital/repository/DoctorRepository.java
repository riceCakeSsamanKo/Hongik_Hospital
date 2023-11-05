package project.hongik_hospital.repository;

import org.springframework.stereotype.Repository;
import project.hongik_hospital.domain.Doctor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    // Doctor의 경우에만 left join fetch를 사용한 이유
    // => department가 없는 doctor의 경우에도 조회를 하기 위해서
    public List<Doctor> findAll() {
        return em.createQuery("select d from Doctor d " +
                        "left join fetch d.department dp",Doctor.class)
                .getResultList();
    }

    public List<Doctor> findByName(String name) {
        return em.createQuery("select d from Doctor d " +
                        "left join fetch d.department " +
                        "where d.name = :name", Doctor.class)
                .setParameter("name", name)
                .getResultList();
    }

    public void remove(Long doctorId) {
        em.createQuery("delete from Doctor d where d.id = :doctorId")
                .setParameter("doctorId", doctorId)
                .executeUpdate();
    }

    public List<Doctor> findByDepartment(Long departmentId) {
        return em.createQuery("select d from Doctor d " +
                        "left join fetch d.department dp " +
                        "where dp.id =: departmentId",Doctor.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
    }
}
