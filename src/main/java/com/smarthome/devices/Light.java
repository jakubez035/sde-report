package com.smarthome.devices;

/**
 * Represents a smart light device.
 */
public class Light implements Device {
    private String id;
    private String name;
    private boolean isOn;
    private int brightness;

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

