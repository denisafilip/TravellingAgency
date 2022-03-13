package service;

import model.VacationDestination;
import org.hibernate.exception.ConstraintViolationException;
import repository.VacationDestinationRepository;
import service.Exceptions.InvalidDataException;
import service.Validators.NameValidator;

import javax.persistence.PersistenceException;
import java.util.List;

public class VacationDestinationService {

    private final VacationDestinationRepository vacationDestinationRepository = new VacationDestinationRepository();

    public String insertVacationDestination(String name) {
        try {
            new NameValidator().validate(name);
            vacationDestinationRepository.insert(name);
            return "The vacation destination was inserted successfully!";
        } catch (InvalidDataException | PersistenceException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String delete(Integer id) {
        try {
            vacationDestinationRepository.delete(id);
            return "The vacation destination was deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "The vacation destination was not deleted successfully!";
        }
    }

    public List<VacationDestination> getAll() {
        try {
            return vacationDestinationRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
