package service.Validators;

import service.Exceptions.InvalidDataException;

public class NumberValidator implements Validator {
    @Override
    public void validate(Object obj) throws InvalidDataException {
        Integer number = (Integer) obj;
        if (number == null || number < 0) {
            throw new InvalidDataException("The number " + number + " is invalid!");
        }
    }
}
