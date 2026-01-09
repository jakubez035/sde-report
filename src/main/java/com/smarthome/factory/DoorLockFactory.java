package com.smarthome.factory;

import com.smarthome.devices.Device;
import com.smarthome.devices.DoorLock;

/**
 * Factory for creating DoorLock devices.
 * Demonstrates Factory Method pattern.
 */
public class DoorLockFactory implements DeviceFactory {
    @Override
    public Device createDevice(String id, String name) {
        return new DoorLock(id, name);
    }

    /**
     * Creates a door lock with a specific initial lock state.
     * @param id Unique identifier
     * @param name Display name
     * @param initiallyLocked Initial lock state
     * @return New DoorLock instance
     */
    public Device createDevice(String id, String name, boolean initiallyLocked) {
        return new DoorLock(id, name, initiallyLocked);
    }
}

