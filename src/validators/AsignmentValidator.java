package validators;

import domain.Entity;
import exceptions.ValidationException;

public class AsignmentValidator implements Validator {
    @Override
    public void validate(Entity entity) throws ValidationException {
        ValidationException exception=new ValidationException();


    }
}
