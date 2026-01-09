package com.smarthome.devices;

/**
 * Represents a smart door lock device.
 */
public class DoorLock implements Device {
    private String id;
    private String name;
    private boolean isOn; // device power state
    private boolean isLocked; // lock state

    /**
     * Constructor for DoorLock.
     * @param id Unique identifier for the door lock
     * @param name Display name of the door lock
     */
    public DoorLock(String id, String name) {
        this.id = id;
        this.name = name;
        this.isOn = false;
        this.isLocked = false; // Default to unlocked
    }

    /**
     * Constructor with initial lock state.
     * @param id Unique identifier for the door lock
     * @param name Display name of the door lock
     * @param initiallyLocked Initial lock state
     */
    public DoorLock(String id, String name, boolean initiallyLocked) {
        this.id = id;
        this.name = name;
        this.isOn = false;
        this.isLocked = initiallyLocked;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println("Door lock " + name + " is now ON. Lock state: " + 
                         (isLocked ? "LOCKED" : "UNLOCKED"));
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println("Door lock " + name + " is now OFF");
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     * Locks the door.
     */
    public void lock() {
        if (!isOn) {
            System.out.println("Cannot lock " + name + " - device is OFF");
            return;
        }
        boolean wasLocked = this.isLocked;
        this.isLocked = true;
        if (!wasLocked) {
            System.out.println("Door lock " + name + " is now LOCKED");
        } else {
            System.out.println("Door lock " + name + " is already LOCKED");
        }
    }

    /**
     * Unlocks the door.
     */
    public void unlock() {
        if (!isOn) {
            System.out.println("Cannot unlock " + name + " - device is OFF");
            return;
        }
        boolean wasLocked = this.isLocked;
        this.isLocked = false;
        if (wasLocked) {
            System.out.println("Door lock " + name + " is now UNLOCKED");
        } else {
            System.out.println("Door lock " + name + " is already UNLOCKED");
        }
    }

    /**
     * Gets the current lock state.
     * @return true if locked, false if unlocked
     */
    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public String toString() {
        return "DoorLock{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isOn=" + isOn +
                ", isLocked=" + isLocked +
                '}';
    }
}

