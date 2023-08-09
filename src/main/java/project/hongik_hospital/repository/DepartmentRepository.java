package project.hongik_hospital.repository;

import org.springframework.stereotype.Repository;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Patient;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        return em.createQuery("select d from Department d",Department.class)
                .getResultList();
    }

    public List<Department> findByName(String name) {
        return em.createQuery("select d from Department d where d.name = :name", Department.class)
                .setParameter("name", name)
                .getResultList();
    }
}
