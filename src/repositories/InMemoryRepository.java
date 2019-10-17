package repositories;

import domain.Entity;
import exceptions.ValidationException;

public class InMemoryRepository<ID, E extends Entity<ID>> implements CrudRepository<ID, E extends Entity<ID>> {

    @Override
    public E findOne(ID id) {
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return null;
    }

    @Override
    public E save(E entity) throws ValidationException {
        return null;
    }

    @Override
    public E delete(ID id) {
        return null;
    }

    @Override
    public E update(E entity) {
        return null;
    }
}
