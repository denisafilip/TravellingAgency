package service.Validators;

import service.Exceptions.InvalidDataException;

import java.util.regex.Pattern;

public class VacationPackageNameValidator implements Validator {
    private static final String NAME_PATTERN = "[a-zA-Z0-9- ăîâșț]+";

    /** {@inheritDoc} */
    @Override
    public void validate(Object nameObject) throws InvalidDataException {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        String name = (String) nameObject;
        if (name == null || name.isEmpty() || !pattern.matcher(name).matches()) {
            throw new InvalidDataException("The name " + name + " is not a valid name!");
        }
    }

}
