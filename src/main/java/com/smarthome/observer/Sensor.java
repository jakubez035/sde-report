package com.smarthome.observer;

/**
 * Base interface for sensors that can detect events.
 */
public interface Sensor {
    String getId();
    String getType();
    void detect();
}

