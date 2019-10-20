package repositories;

import domain.Entity;
import domain.Student;
import exceptions.ValidationException;
import javafx.scene.control.skin.CellSkinBase;
import validators.StudentValidator;
import validators.Validator;
import validators.ValidatorFactory;

import java.lang.reflect.Array;
import java.util.*;


public class InMemoryRepository<ID, E extends Entity<ID>> implements CrudRepository<ID, E> {
    private Map<ID,E> allEntities;
    private Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        allEntities = new HashMap<>();
    }

    /**
     *  @param id -the id of the entity to be returned  (id must not be null)
     * @return the entity with the specified id ,or null - if there is no entity with the given id
     * @throws IllegalArgumentException, if id is null.
     */
    @Override
    public E findOne(ID id) {
        if (id==null)
            throw new IllegalArgumentException();
        if (allEntities.get(id)==null)
            return null;
        else
            return  allEntities.get(id);
    }

    /**
     * @return all entities
     */
    @Override
    public Iterable<E> findAll() {
        return allEntities.values();
    }

    /**
     *  @param entity  (entity must be not null)
     *  @return null- if the given entity is saved otherwise returns the entity (id already exists)
     *  @throws ValidationException  - if the entity is not valid
     *  @throws IllegalArgumentException  - if the given entity is null.
     */
    @Override
    public E save(E entity) throws ValidationException {
        if (entity==null)
            throw new IllegalArgumentException();
        validator.validate(entity);
        E oldElement = findOne(entity.getId());
        allEntities.put(entity.getId(),entity);
        return oldElement;
    }

    /**
     *  removes the entity with the specified id
     *  @param id - id must be not null
     *  @return the removed entity or null if there is no entity with the given id
     *  @throws IllegalArgumentException - if the given id is null.
    */
    @Override
    public E delete(ID id) {
        if (id==null)
            throw new IllegalArgumentException();
        E oldElement = findOne(id);
        allEntities.remove(id);
        return oldElement;
    }

    /**
     * @param entity - entity must not be null      ]
     * @return null - if the entity is updated, otherwise  returns the entity  - (e.g id does not exist).
     * @throws IllegalArgumentException - if the given entity is null.
     * @throws ValidationException  -  if the entity is not valid.
     */
    @Override
    public E update(E entity) {
        if (entity==null)
            throw new IllegalArgumentException();
        validator.validate(entity);
        E oldElement = findOne(entity.getId());
        if (oldElement==null) {
            return save(entity);
        }
        return entity;
    }
}
