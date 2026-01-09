package com.smarthome.command;

import com.smarthome.devices.Light;

/**
 * Command to turn a light on.
 */
public class TurnLightOnCommand implements Command {
    private Light light;
    private boolean previousState;
    
    public TurnLightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        //Store state before changing it for udo support
        previousState = light.isOn();
        light.turnOn();
    }
    
    @Override
    public void undo() {
        if (!previousState) {
            light.turnOff();
        } else {
            light.turnOn();
        }
    }
}

