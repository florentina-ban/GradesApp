package services;

import Events.MyEvent;

public interface Observable<E extends MyEvent> {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(E event);
}
