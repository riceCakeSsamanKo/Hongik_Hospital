package project.hongik_hospital.repository;

import org.springframework.stereotype.Repository;
import project.hongik_hospital.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public void remove(User user) {
        em.remove(user);
    }

    public User findOne(Long userId) {
        return em.find(User.class, userId);
    }

    public List<User> findAll() {
        return em.createQuery("select p from User p",User.class)
                .getResultList();
    }

    public List<User> findByName(String name) {
        return em.createQuery("select p from User p where p.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Optional<User> findByLogInfo(String login_id) {
        try {
            User result = em.createQuery("select p from User p " +
                            "where p.logIn.login_id = :id ", User.class)
                    .setParameter("id", login_id)
                    .getSingleResult();

            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByLogInfo(String id, String pw) {
        try {
            User result = em.createQuery("select p from User p " +
                            "where p.logIn.login_id = :id " +
                            "and p.logIn.login_pw = :pw", User.class)
                    .setParameter("id", id)
                    .setParameter("pw", pw)
                    .getSingleResult();

            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
