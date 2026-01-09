package com.smarthome.command;

import com.smarthome.devices.Thermostat;

/**
 * Command to set thermostat temperature.
 * Demonstrates Command pattern with undo functionality.
 */
public class SetTempCommand implements Command {
    private Thermostat thermostat;
    private double targetTemperature;
    private double previousTemperature;
    private boolean executed = false;

    /**
     * Constructor for SetTempCommand.
     * @param thermostat The thermostat to control
     * @param targetTemperature The target temperature to set
     */
    public SetTempCommand(Thermostat thermostat, double targetTemperature) {
        this.thermostat = thermostat;
        this.targetTemperature = targetTemperature;
    }

    @Override
    public void execute() {
        if (!executed) {
            previousTemperature = thermostat.getTemperature();
            thermostat.setTemperature(targetTemperature);
            executed = true;
            System.out.println("Command executed: Set temperature to " + targetTemperature + "°C");
        } else {
            System.out.println("Command already executed");
        }
    }

    @Override
    public void undo() {
        if (executed) {
            thermostat.setTemperature(previousTemperature);
            executed = false;
            System.out.println("Command undone: Temperature restored to " + previousTemperature + "°C");
        } else {
            System.out.println("Nothing to undo - command not executed yet");
        }
    }

    /**
     * Gets the target temperature for this command.
     * @return Target temperature
     */
    public double getTargetTemperature() {
        return targetTemperature;
    }

    /**
     * Gets the previous temperature before command execution.
     * @return Previous temperature
     */
    public double getPreviousTemperature() {
        return previousTemperature;
    }
}

