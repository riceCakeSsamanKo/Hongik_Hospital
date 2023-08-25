package project.hongik_hospital.repository.update;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Doctor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class DoctorUpdateRepository {
    @PersistenceContext
    EntityManager em;

    public void updateName(Long doctorId, String name) {
        em.createQuery("update Doctor d set d.name=:name " +
                        "where d.id =: doctorId")
                .setParameter("name", name)
                .setParameter("doctorId", doctorId)
                .executeUpdate();
    }

    public void updateCareer(Long doctorId, int career) {
        em.createQuery("update Doctor d set d.career=:career " +
                        "where d.id =: doctorId")
                .setParameter("career", career)
                .setParameter("doctorId", doctorId)
                .executeUpdate();
    }

    public void updateDepartment(Long doctorId, Department department) {
        em.createQuery("update Doctor d set d.department =:department " +
                        "where d.id =: doctorId")
                .setParameter("department", department)
                .setParameter("doctorId", doctorId)
                .executeUpdate();
    }
}
