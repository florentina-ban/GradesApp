package repositories;

import domain.Entity;
import exceptions.ValidationException;

public interface CrudRepository<ID,E extends Entity> {
    E findOne(ID id);
    Iterable<E> findAll();
    E save(E entity) throws ValidationException;
    E delete(ID id);
    E update(E entity);
}
