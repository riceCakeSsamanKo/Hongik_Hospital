package project.hongik_hospital.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Department;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Department department) {
        em.persist(department);
    }

    public Department findOne(Long departmentId) {
        return em.find(Department.class, departmentId);
    }

    public List<Department> findAll() {
        return em.createQuery("select d from Department d " +
                        "join fetch d.hospital", Department.class)
                .getResultList();
    }

    public Optional<Department> findByName(String name) {
        try {
            Department result = em.createQuery("select d from Department d " +
                            "join fetch d.hospital " +
                            "where d.name = :name", Department.class)
                    .setParameter("name", name)
                    .getSingleResult();

            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Department> findByPhoneNumber(String phoneNumber) {
        try {
            Department result = em.createQuery("select d from Department d " +
                            "join fetch d.hospital " +
                            "where d.phoneNumber = :phoneNumber", Department.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();

            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void remove(Department department) {
        em.createQuery("delete from Department d " +
                        "where d.id = :departmentId")
                .setParameter("departmentId", department.getId())
                .executeUpdate();
    }
}
