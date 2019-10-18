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

    @Override
    public E findOne(ID id) {
        if (allEntities.get(id)==null)
            return null;
        else
            return  allEntities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        ArrayList<E> entities=new ArrayList<>();
        return allEntities.values();
    }

    @Override
    public E save(E entity) throws ValidationException {
        validator.validate(entity);
        E oldElement = findOne(entity.getId());
        allEntities.put(entity.getId(),entity);
        return oldElement;
    }

    @Override
    public E delete(ID id) {
        E oldElement = findOne(id);
        allEntities.remove(id);
        return oldElement;
    }

    @Override
    public E update(E entity) {
        E oldElement = findOne(entity.getId());
        if (oldElement!=null)
            save(entity);
        return null;
    }
}
