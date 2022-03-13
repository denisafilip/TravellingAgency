package repository;

import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insert(String email, String firstName, String lastName, String password) {
        User user = new User(email, firstName, lastName, password);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public User findByEmail(String email) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT u FROM User u WHERE u.email = :email";

        User user = em.createQuery(sql, User.class).setParameter("email", email).getSingleResult();
        em.close();
        return user;
    }

    public User findById(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT u FROM User u WHERE u.id = :id";

        User user = em.createQuery(sql, User.class).setParameter("id", id).getSingleResult();
        em.close();
        return user;
    }
}
