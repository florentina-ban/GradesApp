package services;

import Events.CustomEvent;
import domain.Entity;
import repositories.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class SuperService<ID,E extends Entity<ID>>  implements Observable<CustomEvent> {
    CrudRepository<ID,E> repository;
    List<Observer<CustomEvent>> observers=new ArrayList<>();

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
        notifyObservers(new CustomEvent());
        return repository.delete(id);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(CustomEvent event) {
        observers.forEach(x->x.update());

    }
}
