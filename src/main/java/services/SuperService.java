package services;

import domain.Entity;
import repositories.CrudRepository;

public class SuperService<ID,E extends Entity<ID>> {
    CrudRepository<ID,E> repository;

    public SuperService(CrudRepository repository) {
        this.repository = repository;
    }

    public E findOne(ID id) {
        return repository.findOne(id);
    }

    public Iterable<E> findAll(){
        return repository.findAll();
    }

    public E delete(ID id){
        return repository.delete(id);
    }
}
