package com.smarthome.factory;

import com.smarthome.devices.Device;
import com.smarthome.devices.Light;

/**
 * Factory for creating Light devices.
 */
public class LightFactory implements DeviceFactory {
    @Override
    public Device createDevice(String id, String name) {
        // This follows the Factory Method pattern by encapsulating instantiation
        return new Light(id, name);
    }
}

