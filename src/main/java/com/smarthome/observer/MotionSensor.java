package com.smarthome.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Motion sensor that detects movement.
 * Demonstrates Observer pattern by implementing both Sensor and SensorSubject.
 */
public class MotionSensor implements Sensor, SensorSubject {
    private String id;
    private boolean motionDetected;
    private List<DeviceObserver> observers;
    private Random random;
    
    /**
     * Constructor for MotionSensor.
     * @param id Unique identifier for the sensor
     */
    public MotionSensor(String id) {
        this.id = id;
        this.motionDetected = false;
        this.observers = new ArrayList<>();
        this.random = new Random();
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String getType() {
        return "Motion";
    }
    
    @Override
    public void detect() {
        // Simulate motion detection (random chance of detecting motion)
        boolean previousState = motionDetected;
        motionDetected = random.nextDouble() < 0.3; // 30% chance of detecting motion
        
        // If motion state changed, notify observers
        if (motionDetected != previousState) {
            SensorEvent event = new SensorEvent(
                this,
                motionDetected ? "MOTION_DETECTED" : "MOTION_STOPPED",
                motionDetected
            );
            notifyObservers(event);
            System.out.println("MotionSensor " + id + ": Motion " + 
                             (motionDetected ? "detected" : "stopped"));
        }
    }
    
    /**
     * Gets the current motion detection state.
     * @return true if motion is currently detected, false otherwise
     */
    public boolean isMotionDetected() {
        return motionDetected;
    }
    
    /**
     * Manually set motion state (for testing/demo purposes).
     * @param detected Motion detection state
     */
    public void setMotionDetected(boolean detected) {
        boolean previousState = this.motionDetected;
        this.motionDetected = detected;
        
        // Notify observers of change
        if (motionDetected != previousState) {
            SensorEvent event = new SensorEvent(
                this,
                motionDetected ? "MOTION_DETECTED" : "MOTION_STOPPED",
                motionDetected
            );
            notifyObservers(event);
        }
    }
    
    // SensorSubject interface implementation
    
    @Override
    public void attach(DeviceObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer attached to MotionSensor " + id);
        }
    }
    
    @Override
    public void detach(DeviceObserver observer) {
        observers.remove(observer);
        System.out.println("Observer detached from MotionSensor " + id);
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
        return "MotionSensor{" +
                "id='" + id + '\'' +
                ", motionDetected=" + motionDetected +
                ", observers=" + observers.size() +
                '}';
    }
}

