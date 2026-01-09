package com.smarthome.test;

import com.smarthome.command.SetTempCommand;
import com.smarthome.devices.Thermostat;
import com.smarthome.factory.ThermostatFactory;
import com.smarthome.observer.DeviceObserver;
import com.smarthome.observer.SensorEvent;
import com.smarthome.observer.TemperatureSensor;

/**
 * Test class to demonstrate thermostat functionality.
 * Run this to see all patterns in action.
 */
public class ThermostatTest {
    public static void main(String[] args) {
        System.out.println("=== Thermostat Functionality Test ===\n");
        
        // Test 1: Factory Pattern - Create thermostat
        System.out.println("--- Test 1: Factory Pattern ---");
        ThermostatFactory factory = new ThermostatFactory();
        Thermostat thermostat = (Thermostat) factory.createDevice("T1", "Living Room Thermostat");
        System.out.println("Created: " + thermostat);
        System.out.println();
        
        // Test 2: Device functionality
        System.out.println("--- Test 2: Device Functionality ---");
        thermostat.turnOn();
        System.out.println("Is on: " + thermostat.isOn());
        System.out.println("Current temperature: " + thermostat.getTemperature() + "°C");
        System.out.println();
        
        // Test 3: Direct temperature setting
        System.out.println("--- Test 3: Direct Temperature Setting ---");
        thermostat.setTemperature(22.5);
        thermostat.setTemperature(18.0);
        thermostat.setTemperature(25.0);
        System.out.println();
        
        // Test 4: Command Pattern with Undo
        System.out.println("--- Test 4: Command Pattern with Undo ---");
        SetTempCommand setTemp1 = new SetTempCommand(thermostat, 24.0);
        setTemp1.execute();
        System.out.println("Current temp: " + thermostat.getTemperature() + "°C");
        
        SetTempCommand setTemp2 = new SetTempCommand(thermostat, 19.0);
        setTemp2.execute();
        System.out.println("Current temp: " + thermostat.getTemperature() + "°C");
        
        System.out.println("\n--- Undoing commands ---");
        setTemp2.undo();
        System.out.println("Current temp: " + thermostat.getTemperature() + "°C");
        
        setTemp1.undo();
        System.out.println("Current temp: " + thermostat.getTemperature() + "°C");
        System.out.println();
        
        // Test 5: Factory with initial temperature
        System.out.println("--- Test 5: Factory with Initial Temperature ---");
        Thermostat thermostat2 = (Thermostat) factory.createDevice("T2", "Bedroom Thermostat", 21.0);
        thermostat2.turnOn();
        System.out.println("Created with initial temp: " + thermostat2);
        System.out.println();
        
        // Test 6: Temperature Sensor and Observer Pattern
        System.out.println("--- Test 6: Temperature Sensor and Observer Pattern ---");
        TemperatureSensor sensor = new TemperatureSensor("S1", 20.0);
        System.out.println("Created sensor: " + sensor);
        
        // Create an observer that reacts to temperature changes
        DeviceObserver observer = new DeviceObserver() {
            @Override
            public void update(SensorEvent event) {
                if ("TEMPERATURE_CHANGED".equals(event.getEventType())) {
                    double newTemp = (Double) event.getEventData();
                    System.out.println("  [Observer] Temperature changed to " + 
                                     String.format("%.1f", newTemp) + "°C - adjusting thermostat...");
                    // In a real scenario, this would adjust the thermostat
                    if (newTemp > 23.0) {
                        System.out.println("  [Observer] Temperature too high! Cooling down...");
                    } else if (newTemp < 18.0) {
                        System.out.println("  [Observer] Temperature too low! Heating up...");
                    }
                }
            }
        };
        
        sensor.attach(observer);
        System.out.println("Observer attached. Sensor has " + sensor.getObserverCount() + " observer(s)");
        System.out.println();
        
        // Simulate temperature changes
        System.out.println("Simulating temperature changes:");
        sensor.setTemperature(24.5); // Should trigger notification (>2°C change)
        System.out.println();
        
        sensor.setTemperature(16.0); // Should trigger notification (>2°C change)
        System.out.println();
        
        sensor.setTemperature(17.5); // Should NOT trigger (change < 2°C)
        System.out.println("Current sensor temp: " + sensor.getCurrentTemperature() + "°C (no notification)");
        System.out.println();
        
        // Test 7: Temperature clamping
        System.out.println("--- Test 7: Temperature Clamping ---");
        thermostat.setTemperature(5.0);  // Below minimum (10°C)
        System.out.println("Tried to set 5°C, actual: " + thermostat.getTemperature() + "°C");
        
        thermostat.setTemperature(35.0); // Above maximum (30°C)
        System.out.println("Tried to set 35°C, actual: " + thermostat.getTemperature() + "°C");
        System.out.println();
        
        // Test 8: Turn off
        System.out.println("--- Test 8: Turn Off ---");
        thermostat.turnOff();
        System.out.println("Is on: " + thermostat.isOn());
        System.out.println();
        
        System.out.println("=== All Tests Complete ===");
    }
}
