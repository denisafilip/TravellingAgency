package repository;

import model.Booking;
import model.User;
import model.VacationDestination;
import model.VacationPackage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class BookingRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insert(User user, VacationPackage vacationPackage) {

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        user = em.find(User.class, user.getId());
        vacationPackage = em.find(VacationPackage.class, vacationPackage.getId());
        Booking booking = new Booking(user, vacationPackage);
        em.persist(booking);
        em.getTransaction().commit();
        em.close();
    }

    public List<VacationPackage> getBookedVacations(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT b.vacationPackage FROM Booking b WHERE b.user = :user";

        List<VacationPackage> bookedVacations = em.createQuery(sql, VacationPackage.class).setParameter("user", user).getResultList();
        em.close();
        return bookedVacations;
    }
}
