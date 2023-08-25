package project.hongik_hospital.repository.update;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class DepartmentUpdateRepository {
    @PersistenceContext
    EntityManager em;

    public void updateName(Long departmentId, String name) {
        em.createQuery("update Department d set d.name=:name " +
                        "where d.id =: departmentId")
                .setParameter("name", name)
                .setParameter("departmentId", departmentId)
                .executeUpdate();
    }

    public void updatePhoneNumber(Long departmentId, String phoneNumber) {
        em.createQuery("update Department d set d.name=:phoneNumber " +
                        "where d.id =: departmentId")
                .setParameter("phoneNumber", phoneNumber)
                .setParameter("departmentId", departmentId)
                .executeUpdate();
    }


}
