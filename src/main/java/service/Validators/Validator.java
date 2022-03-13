package service.Validators;

import service.Exceptions.InvalidDataException;

/**
 * Validator is a parameterized interface responsible for validating the user input.
 *
 * @author Denisa Filip
 * @version $Id: $Id
 */
public interface Validator {

    /**
     * validate is the function that verifies if a field of a model class is correct and valid. If it isn't, it throws a IllegalArgumentException
     * exception.
     *
     * @param obj - object that is validated
     * @throws InvalidDataException if the fields of the object t contain invalid data.
     */
    void validate(Object obj) throws InvalidDataException;
}
