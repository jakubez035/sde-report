package com.smarthome.main;

import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import com.smarthome.devices.DoorLock;
import com.smarthome.factory.LightFactory;
import com.smarthome.factory.ThermostatFactory;
import com.smarthome.factory.DoorLockFactory;
import com.smarthome.observer.MotionSensor;
import com.smarthome.observer.TemperatureSensor;
import com.smarthome.observer.SmartHomeController;
import com.smarthome.command.Command;
import com.smarthome.command.CommandInvoker;
import com.smarthome.command.TurnLightOnCommand;
import com.smarthome.command.SetTempCommand;
import com.smarthome.command.LockDoorCommand;
import com.smarthome.command.UnlockDoorCommand;
import com.smarthome.builder.Scene;
import com.smarthome.builder.SceneBuilder;
import com.smarthome.adapter.DeviceAdapter;
import com.smarthome.adapter.ThirdPartyDevice;

/**
 * Main class for the Smart Home Simulator CLI application.
 * This comprehensive simulation demonstrates all design patterns implemented in the system:
 * - Factory Pattern: Creating devices using factories
 * - Observer Pattern: Sensor-driven automation with SmartHomeController
 * - Command Pattern: Device control with undo functionality
 * - Builder Pattern: Creating and executing scenes
 * - Adapter Pattern: Integrating third-party devices
 */
public class SmartHomeSimulator {
    
    public static void main(String[] args) {
        System.out.println("Smart Home Simulator - Full Demonstration");
        System.out.println();
        
        // PHASE 1: FACTORY PATTERN - Creating Devices
        System.out.println("1: Factory Pattern - Device Creation");
        System.out.println();
        
        // Create factories
        LightFactory lightFactory = new LightFactory();
        ThermostatFactory thermostatFactory = new ThermostatFactory();
        DoorLockFactory doorLockFactory = new DoorLockFactory();
        
        // Create devices using factories
        
        Light livingRoomLight = (Light) lightFactory.createDevice("L1", "Living Room Light");
        Light kitchenLight = (Light) lightFactory.createDevice("L2", "Kitchen Light");
        Light bedroomLight = (Light) lightFactory.createDevice("L3", "Bedroom Light");
        
        Thermostat mainThermostat = (Thermostat) thermostatFactory.createDevice("T1", "Main Thermostat", 21.0);
        
        DoorLock frontDoorLock = (DoorLock) doorLockFactory.createDevice("DL1", "Front Door Lock");
        
        System.out.println("Created " + livingRoomLight.getName());
        System.out.println("Created " + kitchenLight.getName());
        System.out.println("Created " + bedroomLight.getName());
        System.out.println("Created " + mainThermostat.getName() + " (set to " + mainThermostat.getTemperature() + "°C)");
        System.out.println("Created " + frontDoorLock.getName());
        System.out.println();
        
        // PHASE 2: OBSERVER PATTERN - Sensor-Driven Automation
        System.out.println("2: Observer Pattern - Sensor-Driven Automation");
        System.out.println();
        
        // Create sensors
        MotionSensor hallwayMotionSensor = new MotionSensor("MS1");
        TemperatureSensor livingRoomTempSensor = new TemperatureSensor("TS1", 19.5);
        
        System.out.println("Created Motion Sensor: " + hallwayMotionSensor.getId());
        System.out.println("Created Temperature Sensor: " + livingRoomTempSensor.getId() + 
                          " (initial temp: " + String.format("%.1f", livingRoomTempSensor.getCurrentTemperature()) + "°C)");
        System.out.println();
        
        // Create controller and register devices
        SmartHomeController controller = new SmartHomeController();
        controller.registerDevice(livingRoomLight);
        controller.registerDevice(kitchenLight);
        controller.registerDevice(bedroomLight);
        controller.registerDevice(mainThermostat);
        System.out.println();
        
        // Attach controller as observer to sensors
        hallwayMotionSensor.attach(controller);
        livingRoomTempSensor.attach(controller);
        System.out.println();
        
        // Simulate sensor events
        System.out.println("Motion detected in hallway");
        hallwayMotionSensor.setMotionDetected(true);
        System.out.println();
        
        // Wait a moment (simulated)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        // Temperature change - should adjust thermostat
        System.out.println("Temperature change detected");
        livingRoomTempSensor.setTemperature(16.5); // Low temperature
        System.out.println();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        // Another temperature change
        System.out.println("Temperature change detected");
        livingRoomTempSensor.setTemperature(24.5); // High temperature
        System.out.println();
        
        // PHASE 3: COMMAND PATTERN - Device Control with Undo
        System.out.println("3: Command Pattern - Device Control with Undo");
        System.out.println();
        
        CommandInvoker invoker = new CommandInvoker();
        
        // Turn on front door lock first (required for lock/unlock commands)
        frontDoorLock.turnOn();
        System.out.println();
        
        // Create and execute commands
        Command lightOnCmd = new TurnLightOnCommand(bedroomLight);
        System.out.print("1. ");
        invoker.executeCommand(lightOnCmd);
        
        Command tempCmd = new SetTempCommand(mainThermostat, 23.0);
        System.out.print("2. ");
        invoker.executeCommand(tempCmd);
        
        Command lockCmd = new LockDoorCommand(frontDoorLock);
        System.out.print("3. ");
        invoker.executeCommand(lockCmd);
        
        System.out.println();
        System.out.println("Demonstrating undo functionality:");
        
        // Undo commands in reverse order
        System.out.print("Undo 1: ");
        invoker.undoLastCommand(); // Undo lock
        
        System.out.print("Undo 2: ");
        invoker.undoLastCommand(); // Undo temperature
        
        System.out.print("Undo 3: ");
        invoker.undoLastCommand(); // Undo light
        System.out.println();
        
        // PHASE 4: BUILDER PATTERN - Creating Scenes
        System.out.println("4: Builder Pattern - Creating and Executing Scenes");
        System.out.println();
        
        // Build "Away Mode" scene
        SceneBuilder builder = new SceneBuilder();
        Scene awayMode = builder
            .setName("Away Mode")
            .addDevice(livingRoomLight, "OFF")
            .addDevice(kitchenLight, "OFF")
            .addDevice(bedroomLight, "OFF")
            .addDevice(mainThermostat, "OFF")
            .addDevice(frontDoorLock, "ON")
            .build();
        System.out.println("Built scene: " + awayMode.getName());
        
        // Build "Night Mode" scene
        builder = new SceneBuilder();
        Scene nightMode = builder
            .setName("Night Mode")
            .addDevice(livingRoomLight, "OFF")
            .addDevice(kitchenLight, "OFF")
            .addDevice(bedroomLight, "ON")
            .addDevice(mainThermostat, "ON")
            .build();
        System.out.println("Built scene: " + nightMode.getName());
        
        // Build "Movie Night" scene
        builder = new SceneBuilder();
        Scene movieNight = builder
            .setName("Movie Night")
            .addDevice(livingRoomLight, "OFF")
            .addDevice(kitchenLight, "OFF")
            .addDevice(bedroomLight, "OFF")
            .addDevice(mainThermostat, "ON")
            .build();
        System.out.println("Built scene: " + movieNight.getName());
        System.out.println();
        
        // Turn on devices first for scene execution
        livingRoomLight.turnOn();
        kitchenLight.turnOn();
        bedroomLight.turnOn();
        mainThermostat.turnOn();
        System.out.println();
        System.out.println("Executing Movie Night Scene");
        movieNight.execute();
        System.out.println();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        System.out.println("Executing Night Mode Scene");
        nightMode.execute();
        System.out.println();
        
        // PHASE 5: ADAPTER PATTERN - Third-Party Device Integration
        System.out.println("5: Adapter Pattern - Third-Party Device Integration");
        System.out.println();
        
        // Create third-party device
        ThirdPartyDevice legacyFan = new ThirdPartyDevice("F99", "Legacy Fan");
        System.out.println("Created third-party device: " + legacyFan.getDeviceType() + " (" + legacyFan.getDeviceId() + ")");
        
        // Adapt it to Device interface
        DeviceAdapter adaptedFan = new DeviceAdapter(legacyFan);
        System.out.println("Adapted device: " + adaptedFan.getName());
        System.out.println();
        System.out.println("Device ID: " + adaptedFan.getId());
        System.out.println("Device Name: " + adaptedFan.getName());
        System.out.println("Status: " + (adaptedFan.isOn() ? "ON" : "OFF"));
        System.out.print("Turning on: ");
        adaptedFan.turnOn();
        System.out.println("Status after turnOn: " + (adaptedFan.isOn() ? "ON" : "OFF"));
        System.out.print("Turning off: ");
        adaptedFan.turnOff();
        System.out.println("Status after turnOff: " + (adaptedFan.isOn() ? "ON" : "OFF"));
        System.out.println();
        
        // PHASE 6: INTEGRATED SCENARIO - Complete Smart Home Flow
        System.out.println("6: Integrated Scenario - Complete Smart Home Flow");
        System.out.println();
        
        // Morning: Wake up
        System.out.println("MORNING - 7:00 AM");
        System.out.println("Executing Morning Routine");
        SceneBuilder morningBuilder = new SceneBuilder();
        Scene morningScene = morningBuilder
            .setName("Morning Routine")
            .addDevice(bedroomLight, "ON")
            .addDevice(mainThermostat, "ON")
            .build();
        morningScene.execute();
        System.out.println();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        // Motion detected - lights turn on automatically
        System.out.println("Motion detected in hallway");
        hallwayMotionSensor.setMotionDetected(true);
        System.out.println();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        // Afternoon: Leaving home
        System.out.println("AFTERNOON - 2:00 PM");
        System.out.println("Leaving home - Executing Away Mode");
        awayMode.execute();
        System.out.println();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        // Evening: Returning home
        System.out.println("EVENING - 6:00 PM");
        System.out.println("Returning home");
        
        // Unlock door using command
        frontDoorLock.turnOn();
        Command unlockCmd = new UnlockDoorCommand(frontDoorLock);
        invoker.executeCommand(unlockCmd);
        System.out.println();
        
        // Motion detected - lights turn on
        System.out.println("Motion detected - entering home");
        hallwayMotionSensor.setMotionDetected(true);
        System.out.println();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        // Temperature adjustment
        System.out.println("Adjusting temperature");
        Command setTempCmd = new SetTempCommand(mainThermostat, 22.0);
        invoker.executeCommand(setTempCmd);
        System.out.println();
        
        // Night: Bedtime
        System.out.println("NIGHT - 11:00 PM");
        System.out.println("Executing Night Mode");
        nightMode.execute();
        System.out.println();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        // Motion stops
        System.out.println("Motion stopped");
        hallwayMotionSensor.setMotionDetected(false);
        System.out.println();
        
        // SUMMARY
        System.out.println("Simulation Summary");
        System.out.println();
        System.out.println("Design Patterns Demonstrated:");
        System.out.println("- Factory Pattern: Created devices using LightFactory, ThermostatFactory, DoorLockFactory");
        System.out.println("- Observer Pattern: Sensors (MotionSensor, TemperatureSensor) notify SmartHomeController");
        System.out.println("- Command Pattern: Device control with undo functionality via CommandInvoker");
        System.out.println("- Builder Pattern: Created scenes (Away Mode, Night Mode, Movie Night) using SceneBuilder");
        System.out.println("- Adapter Pattern: Integrated third-party device using DeviceAdapter");
        System.out.println();
        System.out.println("System Components:");
        System.out.println("- Devices: " + controller.getDevices().size() + " registered devices");
        System.out.println("- Sensors: Motion Sensor (MS1), Temperature Sensor (TS1)");
        System.out.println("- Controller: SmartHomeController with " + controller.getDevices().size() + " devices");
        System.out.println("- Scenes: 3 pre-configured scenes");
        System.out.println();
        System.out.println("Simulation Complete");
    }
}
