package com.smarthome.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Temperature sensor that detects temperature changes.
 * Demonstrates Observer pattern by implementing both Sensor and SensorSubject.
 */
public class TemperatureSensor implements Sensor, SensorSubject {
    private String id;
    private double currentTemperature;
    private List<DeviceObserver> observers;
    private Random random;
    private static final double MIN_TEMP = 15.0;
    private static final double MAX_TEMP = 25.0;
    private static final double TEMP_CHANGE_THRESHOLD = 2.0; // Notify if change > 2°C

    /**
     * Constructor for TemperatureSensor.
     * @param id Unique identifier for the sensor
     */
    public TemperatureSensor(String id) {
        this.id = id;
        this.observers = new ArrayList<>();
        this.random = new Random();
        // Initialize with random temperature in range
        this.currentTemperature = MIN_TEMP + (MAX_TEMP - MIN_TEMP) * random.nextDouble();
    }

    /**
     * Constructor with initial temperature.
     * @param id Unique identifier for the sensor
     * @param initialTemperature Initial temperature reading
     */
    public TemperatureSensor(String id, double initialTemperature) {
        this.id = id;
        this.observers = new ArrayList<>();
        this.random = new Random();
        this.currentTemperature = initialTemperature;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return "Temperature";
    }

    @Override
    public void detect() {
        // Simulate temperature reading (random change)
        double previousTemperature = currentTemperature;
        double change = (random.nextDouble() - 0.5) * 4.0; // Random change between -2 and +2
        currentTemperature = Math.max(MIN_TEMP, Math.min(MAX_TEMP, currentTemperature + change));

        // If significant change detected, notify observers
        if (Math.abs(currentTemperature - previousTemperature) >= TEMP_CHANGE_THRESHOLD) {
            SensorEvent event = new SensorEvent(
                this,
                "TEMPERATURE_CHANGED",
                currentTemperature
            );
            notifyObservers(event);
            System.out.println("TemperatureSensor " + id + ": Temperature changed from " +
                             String.format("%.1f", previousTemperature) + "°C to " +
                             String.format("%.1f", currentTemperature) + "°C");
        }
    }

    /**
     * Gets the current temperature reading.
     * @return Current temperature
     */
    public double getCurrentTemperature() {
        return currentTemperature;
    }

    /**
     * Manually set temperature (for testing/demo purposes).
     * @param temperature Temperature to set
     */
    public void setTemperature(double temperature) {
        double previousTemperature = this.currentTemperature;
        this.currentTemperature = temperature;
        
        // Notify observers of change
        if (Math.abs(currentTemperature - previousTemperature) >= TEMP_CHANGE_THRESHOLD) {
            SensorEvent event = new SensorEvent(
                this,
                "TEMPERATURE_CHANGED",
                currentTemperature
            );
            notifyObservers(event);
        }
    }

    // SensorSubject interface implementation

    @Override
    public void attach(DeviceObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer attached to TemperatureSensor " + id);
        }
    }

    @Override
    public void detach(DeviceObserver observer) {
        observers.remove(observer);
        System.out.println("Observer detached from TemperatureSensor " + id);
    }

    @Override
    public void notifyObservers(SensorEvent event) {
        for (DeviceObserver observer : observers) {
            observer.update(event);
        }
    }

    /**
     * Gets the number of attached observers.
     * @return Number of observers
     */
    public int getObserverCount() {
        return observers.size();
    }

    @Override
    public String toString() {
        return "TemperatureSensor{" +
                "id='" + id + '\'' +
                ", currentTemperature=" + String.format("%.1f", currentTemperature) + "°C" +
                ", observers=" + observers.size() +
                '}';
    }
}

