package com.smarthome.command;

import com.smarthome.devices.Light;

/**
 * Command to turn a light off.
 */
public class TurnLightOffCommand implements Command {
    private Light light;
    private boolean previousState;
    
    // Implementation will be added later
    
    @Override
    public void execute() {}
    
    @Override
    public void undo() {}
}

