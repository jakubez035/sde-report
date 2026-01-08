package com.smarthome.observer;

/**
 * Observer pattern: Interface for objects that observe sensor events.
 */
public interface DeviceObserver {
    void update(SensorEvent event);
}

