package repository;

import model.VacationDestination;
import model.VacationPackage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class VacationPackageRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insert(VacationDestination vacationDestination, String name, Integer price, LocalDate startDate,
                       LocalDate endDate, String extraDetails, Integer maxBookingUsers) {
        EntityManager em = entityManagerFactory.createEntityManager();
        vacationDestination = em.find(VacationDestination.class, vacationDestination.getId());
        VacationPackage vacationPackage = new VacationPackage(name, price, startDate, endDate, extraDetails,
                maxBookingUsers, vacationDestination);
        em.getTransaction().begin();
        em.persist(vacationPackage);
        em.getTransaction().commit();
        em.close();
    }

    @Transactional
    public void edit(Integer id, VacationDestination vacationDestination, String name, Integer price, LocalDate startDate,
                     LocalDate endDate, String extraDetails, Integer maxBookingUsers) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "UPDATE VacationPackage v SET v.name=:name, v.price=:price, v.startDate=:startDate, v.endDate=:endDate," +
                " v.extraDetails=:extraDetails, v.maxBookingUsers=:maxBookingUsers, v.destination=:destination WHERE v.id=:id";
        em.getTransaction().begin();
        vacationDestination = em.find(VacationDestination.class, vacationDestination.getId());
        Query query = em.createQuery(sql);
        query.setParameter("name", name);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("price", price);
        query.setParameter("extraDetails", extraDetails);
        query.setParameter("maxBookingUsers", maxBookingUsers);
        query.setParameter("destination", vacationDestination);
        query.setParameter("id", id);
        query.executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public void delete(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        VacationPackage vacationPackage = getById(id);
        if (vacationPackage == null) {
            throw new NoSuchElementException("No vacation package was found!");
        }
        em.getTransaction().begin();
        vacationPackage = em.find(VacationPackage.class, vacationPackage.getId());
        em.remove(vacationPackage);
        em.flush();
        em.clear();
        em.getTransaction().commit();
        em.close();
    }

    public List<VacationPackage> getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT v FROM VacationPackage v";

        List<VacationPackage> vacationPackages = em.createQuery(sql, VacationPackage.class).getResultList();
        em.close();
        return vacationPackages;
    }

    public VacationPackage getById(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT v FROM VacationPackage v WHERE v.id=:id";

        VacationPackage vacationPackage = em.createQuery(sql, VacationPackage.class).setParameter("id", id).getSingleResult();
        em.close();
        return vacationPackage;
    }

    public List<VacationPackage> filterByPrice(Double price) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT v FROM VacationPackage v WHERE v.price = :price";

        List<VacationPackage> vacationPackages = em.createQuery(sql, VacationPackage.class).setParameter("price", price).getResultList();
        em.close();
        return vacationPackages;
    }

    public List<VacationPackage> filterByStartDate(LocalDate startDate) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT v FROM VacationPackage v WHERE v.startDate = :startDate";

        List<VacationPackage> vacationPackages = em.createQuery(sql, VacationPackage.class).setParameter("startDate", startDate).getResultList();
        em.close();
        return vacationPackages;
    }

    public List<VacationPackage> filterPackagesByDestinationPriceAndStartDate(VacationDestination vacationDestination,
                                                                              Integer price, LocalDate startDate) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = "SELECT v FROM VacationPackage v WHERE (:vacationDestination is null or v.destination = " +
                ":vacationDestination) and (:price is null or v.price = :price) and (:startDate is null or v.startDate = " +
                ":startDate)";


        Query query = em.createQuery(sql, VacationPackage.class);
        query.setParameter("vacationDestination", vacationDestination);
        query.setParameter("price", price);
        query.setParameter("startDate", startDate);

        List<VacationPackage> vacationPackages = query.getResultList();
        em.close();
        return vacationPackages;
    }
}
