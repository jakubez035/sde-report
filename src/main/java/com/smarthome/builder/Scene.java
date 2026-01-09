package com.smarthome.builder;

import com.smarthome.devices.Device;
import java.util.HashMap;
import java.util.Map;

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
        System.out.ptintln("Executing Scene: " + name);
        deviceStates.forEach((device, state) -> {
            if (state.equaltsIgnoreCase("ON")) {
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

