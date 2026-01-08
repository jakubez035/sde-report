package com.smarthome.devices;

/**
 * Represents a smart thermostat device.
 */
public class Thermostat implements Device {
    private String id;
    private String name;
    private boolean isOn;
    private double temperature;

    // Constructor and methods will be implemented later
    
    @Override
    public String getId() { return null; }
    
    @Override
    public String getName() { return null; }
    
    @Override
    public void turnOn() {}
    
    @Override
    public void turnOff() {}
    
    @Override
    public boolean isOn() { return false; }
}

