package com.smarthome.command;

import com.smarthome.devices.Thermostat;

/**
 * Command to set thermostat temperature.
 */
public class SetTempCommand implements Command {
    private Thermostat thermostat;
    private double targetTemperature;
    private double previousTemperature;
    
    // Implementation will be added later
    
    @Override
    public void execute() {}
    
    @Override
    public void undo() {}
}

