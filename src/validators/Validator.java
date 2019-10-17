package validators;

import domain.Entity;
import exceptions.ValidationException;

public interface Validator<E extends Entity> {
    void validate(E entity) throws ValidationException;
}
