package com.smarthome.devices;

/**
 * Base interface for all smart home devices.
 */
public interface Device {
    String getId();
    String getName();
    void turnOn();
    void turnOff();
    boolean isOn();
}

