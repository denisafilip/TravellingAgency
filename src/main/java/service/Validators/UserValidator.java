package service.Validators;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserValidator {
    private List<Validator> validators;

    /**
     * <p>Constructor without arguments for the UserValidator class. It initializes the validators list.</p>
     */
    public UserValidator() {
        validators = new ArrayList<>();
        validators.add(new NameValidator());
        validators.add(new UserEmailValidator());
        validators.add(new UserPasswordValidator());
    }
}
