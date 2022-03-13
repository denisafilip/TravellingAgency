package service.Validators;

import service.Exceptions.InvalidDataException;

import java.time.LocalDate;

public class DateValidator implements Validator {

    @Override
    public void validate(Object obj) throws InvalidDataException {
        LocalDate date = (LocalDate) obj;
        if (date == null || date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.of(2100, 12, 1))) {
            throw new InvalidDataException("The date " + date + " is invalid! It must be after the present day, but before December 2100!");
        }
    }
}
