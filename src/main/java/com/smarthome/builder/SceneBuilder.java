package com.smarthome.builder;

import com.smarthome.devices.Device;
import java.util.HashMap;
import java.util.Map;

/**
 * Builder pattern: Builds a Scene step-by-step.
 */
public class SceneBuilder {
    private String name;
    private Map<Device, String> deviceStates = new HashMap<>();

    public SceneBuilder setName(String name) {
        this.name = name;
        return this; //Returning 'this' allows method changing
    }
    
    public SceneBuilder addDevice(Device device, String targetState) {
        this.deviceStates.put(device, targetState);
        return this;
    }
    
    public Scene build() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Scene name must be set before building.");
        }
        return new Scene(name, deviceStates);
    }
}

