package com.smarthome.factory;

import com.smarthome.devices.Device;

/**
 * Factory Method pattern: Creates devices by type.
 */
public interface DeviceFactory {
    Device createDevice(String id, String name);
}

