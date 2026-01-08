package com.smarthome.factory;

import com.smarthome.devices.Device;

/**
 * Abstract Factory pattern: Creates families of related devices.
 */
public abstract class AbstractDeviceFactory {
    public abstract DeviceFactory getLightFactory();
    public abstract DeviceFactory getThermostatFactory();
    public abstract DeviceFactory getDoorLockFactory();
}

