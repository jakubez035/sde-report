package com.smarthome.observer;

/**
 * Subject interface for the Observer pattern.
 */
public interface SensorSubject {
    void attach(DeviceObserver observer);
    void detach(DeviceObserver observer);
    void notifyObservers(SensorEvent event);
}

