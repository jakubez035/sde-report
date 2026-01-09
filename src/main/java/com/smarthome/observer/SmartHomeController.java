package com.smarthome.observer;

import java.util.ArrayList;
import java.util.List;
import com.smarthome.devices.Device;
import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;

/**
 * Controller that observes sensor events and reacts by controlling devices.
 */
public class SmartHomeController implements DeviceObserver {
    private List<Device> devices;
    
    /**
     * Constructor for SmartHomeController.
     */
    public SmartHomeController() {
        this.devices = new ArrayList<>();
    }
    
    /**
     * Registers a device to be controlled by this controller.
     * @param device Device to register
     */
    public void registerDevice(Device device) {
        if (!devices.contains(device)) {
            devices.add(device);
            System.out.println("SmartHomeController: Registered device " + device.getName());
        }
    }
    
    /**
     * Unregisters a device from this controller.
     * @param device Device to unregister
     */
    public void unregisterDevice(Device device) {
        devices.remove(device);
        System.out.println("SmartHomeController: Unregistered device " + device.getName());
    }
    
    /**
     * Gets the list of registered devices.
     * @return List of registered devices
     */
    public List<Device> getDevices() {
        return new ArrayList<>(devices);
    }
    
    @Override
    public void update(SensorEvent event) {
        String eventType = event.getEventType();
        Sensor sensor = event.getSensor();
        
        System.out.println("SmartHomeController: Received event " + eventType + 
                         " from sensor " + sensor.getId());
        
        // React to motion detection events
        if ("MOTION_DETECTED".equals(eventType)) {
            // Turn on all lights when motion is detected
            for (Device device : devices) {
                if (device instanceof Light && !device.isOn()) {
                    device.turnOn();
                }
            }
        } else if ("MOTION_STOPPED".equals(eventType)) {
            // Optionally turn off lights when motion stops (after a delay in real scenario)
            // For now, we'll just log it
            System.out.println("SmartHomeController: Motion stopped, lights remain on");
        } else if ("TEMPERATURE_CHANGED".equals(eventType)) {
            // Adjust thermostat based on temperature changes
            Object eventData = event.getEventData();
            if (eventData instanceof Double) {
                double temperature = (Double) eventData;
                for (Device device : devices) {
                    if (device instanceof Thermostat) {
                        Thermostat thermostat = (Thermostat) device;
                        // Adjust thermostat to maintain comfortable temperature
                        if (temperature < 18.0) {
                            thermostat.setTemperature(20.0);
                            System.out.println("SmartHomeController: Temperature low, setting thermostat to 20°C");
                        } else if (temperature > 23.0) {
                            thermostat.setTemperature(22.0);
                            System.out.println("SmartHomeController: Temperature high, setting thermostat to 22°C");
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "SmartHomeController{" +
                "devices=" + devices.size() +
                '}';
    }
}

