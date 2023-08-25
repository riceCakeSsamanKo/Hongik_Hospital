package project.hongik_hospital.repository.update;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Department;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class ReserveUpdateRepository {
    @PersistenceContext
    EntityManager em;

    public void updateDepartment(Long reserveId, Department department) {
        em.createQuery("update Reserve r set r.department=:department " +
                        "where r.id =: doctorId")
                .setParameter("department", department)
                .setParameter("doctorId",reserveId)
                .executeUpdate();
    }
}
