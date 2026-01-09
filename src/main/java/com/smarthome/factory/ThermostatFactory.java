package com.smarthome.factory;

import com.smarthome.devices.Device;
import com.smarthome.devices.Thermostat;

/**
 * Factory for creating Thermostat devices.
 * Demonstrates Factory Method pattern.
 */
public class ThermostatFactory implements DeviceFactory {
    @Override
    public Device createDevice(String id, String name) {
        return new Thermostat(id, name);
    }

    /**
     * Creates a thermostat with a specific initial temperature.
     * @param id Unique identifier
     * @param name Display name
     * @param initialTemperature Initial temperature setting
     * @return New Thermostat instance
     */
    public Device createDevice(String id, String name, double initialTemperature) {
        return new Thermostat(id, name, initialTemperature);
    }
}

