package services;

import Events.MyEvent;

public interface Observer<E extends MyEvent> {
    void update();
}
