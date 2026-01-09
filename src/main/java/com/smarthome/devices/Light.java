package com.smarthome.devices;

/**
 * Represents a smart light device.
 */
public class Light implements Device {
    private String id;
    private String name;
    private boolean isOn;
    private int brightness;

    public Light(String id, String name) {
        this.id = id;
        this.name = name;
        this.isOn = false;
        this.brightness = 100;
    }
    
    @Override
    public String getId() { return id; }
    
    @Override
    public String getName() { return name; }

    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println(name + " is now ON at " + brightness + "% brightness.");
    }
    
    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println(name + " is now OFF.");
    }
    
    @Override
    public boolean isOn() { return isOn; }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
        if (isOn) {
            System.out.println(name + " brightness adjusted to " + brightness + "%.");
        }
    }

    public int getBrightness() {
        return brightness;
    }

    @Override
    public String toString() {
        return "Light [ID=" + id + ", Name=" + name + ", Status=" + (isOn ? "ON" : "OFF") + ", Brightness=" + brightness + "%]";
    }
}

