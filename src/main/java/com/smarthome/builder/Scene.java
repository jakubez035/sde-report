package com.smarthome.builder;

import java.util.HashMap;
import java.util.Map;

import com.smarthome.devices.Device;

/**
 * Represents a scene configuration (e.g., Away mode, Night mode).
 */
public class Scene {
    private String name;
    private Map<Device, String> deviceStates; // Device -> target state

    public Scene(String name, Map<Device, String> deviceStates) {
        this.name = name;
        this.deviceStates = new HashMap<>(deviceStates);
    }

    public void execute() {
        System.out.println("Executing Scene: " + name);
        deviceStates.forEach((device, state) -> {
            if (state.equalsIgnoreCase("ON")) {
                device.turnOn();
            } else if (state.equalsIgnoreCase("OFF")) {
                device.turnOff();
            }
        });
    }

    public String getName() { return name;}

    @Override
    public String toString() {
        return "Scene " + name + " with " + deviceStates.size() + " devices.";
    }
    
}

