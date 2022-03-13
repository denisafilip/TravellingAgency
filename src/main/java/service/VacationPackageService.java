package service;

import model.VacationDestination;
import model.VacationPackage;
import model.VacationStatus;
import repository.VacationPackageRepository;
import service.Exceptions.InvalidDataException;
import service.Validators.DateValidator;
import service.Validators.NumberValidator;
import service.Validators.VacationPackageNameValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VacationPackageService {

    private final VacationPackageRepository vacationPackageRepository = new VacationPackageRepository();

    private String validate(String name, Integer price, String extraDetails, Integer maxBookingUsers, LocalDate startDate,
                           LocalDate endDate) {
        try {
            new VacationPackageNameValidator().validate(name);
            new NumberValidator().validate(price);
            new NumberValidator().validate(maxBookingUsers);
            new DateValidator().validate(startDate);
            new DateValidator().validate(endDate);

            if (extraDetails.isEmpty()) {
                throw new InvalidDataException("The extra details field MUST NOT be empty!");
            }

            if (endDate.isBefore(startDate)) {
                throw new InvalidDataException("The end date " + endDate + " cannot be before the start date " + startDate + "!");
            }

            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    public String insert(String name, Integer price, String extraDetails, Integer maxBookingUsers, LocalDate startDate,
                         LocalDate endDate, VacationDestination destination) {
        String validationMsg = this.validate(name, price, extraDetails, maxBookingUsers, startDate, endDate);

        if (validationMsg == null) {
            try {
                vacationPackageRepository.insert(destination, name, price, startDate, endDate, extraDetails, maxBookingUsers);
                return "Success! The vacation package was added successfully!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return validationMsg;
        }
    }

    public String edit(Integer id, VacationDestination vacationDestination, String name, Integer price, LocalDate startDate,
                       LocalDate endDate, String extraDetails, Integer maxBookingUsers) {
        String validationMsg = this.validate(name, price, extraDetails, maxBookingUsers, startDate, endDate);

        if (validationMsg == null) {
            vacationPackageRepository.edit(id, vacationDestination, name, price, startDate, endDate, extraDetails, maxBookingUsers);
            return "Success! The vacation package was modified successfully!";
        } else {
            return validationMsg;
        }
    }

    public String delete(Integer id) {
        try {
            vacationPackageRepository.delete(id);
            return "The vacation package was deleted successfully!";
        } catch (Exception e) {
            return "ERROR! The vacation package could not be deleted!";
        }
    }

    public List<VacationPackage> getAll() {
        try {
            return vacationPackageRepository.getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public VacationStatus getStatus(VacationPackage vacationPackage) {
        int numberOfBookings = vacationPackage.getBookings().size();
        if (numberOfBookings == vacationPackage.getMaxBookingUsers()) {
            return VacationStatus.BOOKED;
        } else if (numberOfBookings == 0) {
            return VacationStatus.NOT_BOOKED;
        } else {
            return VacationStatus.IN_PROGRESS;
        }
    }

    public Map<VacationStatus, List<VacationPackage>> sortPackages() {
        List<VacationPackage> vacationPackages = getAll();
        if (vacationPackages == null) {
            return null;
        }

        Map<VacationStatus, List<VacationPackage>> packagesByStatus = new HashMap<>();
        packagesByStatus.put(VacationStatus.NOT_BOOKED, new ArrayList<>());
        packagesByStatus.put(VacationStatus.IN_PROGRESS, new ArrayList<>());
        packagesByStatus.put(VacationStatus.BOOKED, new ArrayList<>());

        for (VacationPackage vacationPackage : vacationPackages) {
            VacationStatus status = getStatus(vacationPackage);
            packagesByStatus.get(status).add(vacationPackage);
        }

        return packagesByStatus;
    }

    public List<VacationPackage> getAvailablePackages() {
        List <VacationPackage> vacationPackages = vacationPackageRepository.getAll();
        if (vacationPackages == null) {
            return null;
        }

        Predicate<VacationPackage> byBookings = vacationPackage -> vacationPackage.getBookings().size() <
                vacationPackage.getMaxBookingUsers();

        return vacationPackages.stream().filter(byBookings).collect(Collectors.toList());
    }

    public List<VacationPackage> filterPackagesByDestinationPriceAndStartDate(VacationDestination destination, Integer
                                                                              price, LocalDate startDate) {
        try {
            return vacationPackageRepository.filterPackagesByDestinationPriceAndStartDate(destination, price, startDate);
        } catch (Exception e) {
            return null;
        }
    }
}
