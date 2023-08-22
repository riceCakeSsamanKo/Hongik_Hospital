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
        return em.createQuery("select r from Reserve r " +
                        "join fetch r.user p " +
                        "join fetch r.doctor d " +
                        "join fetch r.department dp " +
                        "join fetch r.hospital h" ,Reserve.class)
                .getResultList();
    }

    public List<Reserve> findByUserName(String userName) {
        return em.createQuery("select r from Reserve r " +
                        "join fetch r.user p " +
                        "join fetch r.doctor d " +
                        "join fetch r.department dp " +
                        "join fetch r.hospital h " +
                        "where r.user.name = :userName", Reserve.class)
                .setParameter("userName", userName)
                .getResultList();
    }

    public List<Reserve> findByStatus(ReserveStatus status) {
        return em.createQuery("select r from Reserve r " +
                        "join fetch r.user p " +
                        "join fetch r.doctor d " +
                        "join fetch r.department dp " +
                        "join fetch r.hospital h " +
                        "where r.reserveStatus = :status",Reserve.class)
                .setParameter("status", status)
                .getResultList();
    }
}
