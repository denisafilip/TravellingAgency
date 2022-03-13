package repository;

import model.VacationDestination;

import javax.persistence.*;
import java.util.List;
import java.util.NoSuchElementException;

public class VacationDestinationRepository {

    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insert(String name) {
        VacationDestination vacationDestination = new VacationDestination(name);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(vacationDestination);
        em.getTransaction().commit();
        em.close();
    }

    public VacationDestination getById(Integer id) throws NoSuchElementException {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT v FROM VacationDestination v WHERE v.id = :id";

        VacationDestination vacationDestination = em.createQuery(sql, VacationDestination.class).setParameter("id", id).
                getSingleResult();
        em.close();
        return vacationDestination;
    }

    public void delete(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        VacationDestination vacationDestination = getById(id);
        if (vacationDestination == null) {
            throw new NoSuchElementException("No such element!");
        }
        em.getTransaction().begin();
        vacationDestination = em.find(VacationDestination.class, vacationDestination.getId());
        em.remove(vacationDestination);
        em.flush();
        em.clear();
        em.getTransaction().commit();
        em.close();
    }

    public List<VacationDestination> getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT v FROM VacationDestination v";

        List<VacationDestination> vacationDestinations = em.createQuery(sql, VacationDestination.class).getResultList();
        em.close();
        return vacationDestinations;
    }
}
