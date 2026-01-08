package com.smarthome.devices;

/**
 * Represents a smart door lock device.
 */
public class DoorLock implements Device {
    private String id;
    private String name;
    private boolean isOn; // locked/unlocked
    private boolean isLocked;

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

