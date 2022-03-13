package service;

import model.User;
import repository.UserRepository;
import service.Exceptions.InvalidDataException;
import service.Validators.UserEmailValidator;
import service.Validators.NameValidator;
import service.Validators.UserPasswordValidator;

import java.util.NoSuchElementException;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public String validateUser(String firstName, String lastName, String email, String password, String confirmedPassword) {
        try {
            new NameValidator().validate(firstName);
            new NameValidator().validate(lastName);
            new UserEmailValidator().validate(email);
            new UserPasswordValidator().validate(password);
            new UserPasswordValidator().checkIfSamePassword(password, confirmedPassword);
            return null;
        } catch (InvalidDataException e) {
            return e.getMessage();
        }
    }

    public String insert(String firstName, String lastName, String email, String password, String confirmedPassword) {
        String validationMsg = validateUser(firstName, lastName, email, password, confirmedPassword);
        if (validationMsg == null) {
            userRepository.insert(email, firstName, lastName, password);
            return null;
        } else {
            return validationMsg;
        }
    }

    public User getUser(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }

    }

    private void validateUserCredentialsFunctionality(String email, String password) throws InvalidDataException {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new NoSuchElementException("No user with email " + email + " was found.");
            } else {
                if (!user.getPassword().equals(password)) {
                    throw new InvalidDataException("Wrong password!");
                }
            }
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }


    }

    public String validateUserCredentials(String email, String password) {
        try {
            validateUserCredentialsFunctionality(email, password);
            return null;
        } catch (InvalidDataException | NoSuchElementException e) {
            return e.getMessage();
        }
    }
}
