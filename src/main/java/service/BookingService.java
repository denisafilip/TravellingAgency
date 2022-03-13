package service;

import model.User;
import model.VacationPackage;
import repository.BookingRepository;

import java.util.List;

public class BookingService {

    private final BookingRepository bookingRepository = new BookingRepository();

    public String insert(User user, VacationPackage vacationPackage) {
        try {
            bookingRepository.insert(user, vacationPackage);
            return "Success!";
        } catch (Exception e) {
           return e.getMessage();
        }
    }

    public List<VacationPackage> getBookedVacations(User user) {
        return bookingRepository.getBookedVacations(user);
    }
}
