package com.smarthome.command;

import com.smarthome.devices.Light;

/**
 * Command to turn a light off.
 */
public class TurnLightOffCommand implements Command {
    private Light light;
    private boolean previousState;
    
    public TurnLightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        previousState = light.isOn();
        light.turnOff();
    }
    
    @Override
    public void undo() {
        if (previousState) {
            light.turnOn();
        } else {
            light.turnOff();
        }
    }
}

