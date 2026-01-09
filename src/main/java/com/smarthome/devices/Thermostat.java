package com.smarthome.devices;

/**
 * Represents a smart thermostat device.
 */
public class Thermostat implements Device {
    private String id;
    private String name;
    private boolean isOn;
    private double temperature;
    private static final double DEFAULT_TEMPERATURE = 20.0; // Celsius
    private static final double MIN_TEMPERATURE = 10.0;
    private static final double MAX_TEMPERATURE = 30.0;

    /**
     * Constructor for Thermostat.
     * @param id Unique identifier for the thermostat
     * @param name Display name of the thermostat
     */
    public Thermostat(String id, String name) {
        this.id = id;
        this.name = name;
        this.isOn = false;
        this.temperature = DEFAULT_TEMPERATURE;
    }

    /**
     * Constructor with initial temperature.
     * @param id Unique identifier for the thermostat
     * @param name Display name of the thermostat
     * @param initialTemperature Initial temperature setting
     */
    public Thermostat(String id, String name, double initialTemperature) {
        this.id = id;
        this.name = name;
        this.isOn = false;
        this.temperature = clampTemperature(initialTemperature);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println("Thermostat " + name + " is now ON. Current temperature: " + temperature + "°C");
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println("Thermostat " + name + " is now OFF");
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     * Gets the current temperature setting.
     * @return Current temperature in Celsius
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Sets the target temperature.
     * @param temperature Target temperature (will be clamped between MIN and MAX)
     */
    public void setTemperature(double temperature) {
        double oldTemperature = this.temperature;
        this.temperature = clampTemperature(temperature);
        if (isOn) {
            System.out.println("Thermostat " + name + ": Temperature changed from " + 
                             oldTemperature + "°C to " + this.temperature + "°C");
        }
    }

    /**
     * Clamps temperature to valid range.
     * @param temp Temperature to clamp
     * @return Clamped temperature value
     */
    private double clampTemperature(double temp) {
        if (temp < MIN_TEMPERATURE) {
            return MIN_TEMPERATURE;
        } else if (temp > MAX_TEMPERATURE) {
            return MAX_TEMPERATURE;
        }
        return temp;
    }

    @Override
    public String toString() {
        return "Thermostat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isOn=" + isOn +
                ", temperature=" + temperature + "°C" +
                '}';
    }
}

