package com.smarthome.observer;

/**
 * Represents an event published by a sensor.
 */
public class SensorEvent {
    private Sensor sensor;
    private String eventType;
    private Object eventData;
    
    public SensorEvent(Sensor sensor, String eventType, Object eventData) {
        this.sensor = sensor;
        this.eventType = eventType;
        this.eventData = eventData;
    }
    
    public Sensor getSensor() {
        return sensor;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public Object getEventData() {
        return eventData;
    }
    
    @Override
    public String toString() {
        return "SensorEvent{" +
                "sensor=" + sensor.getId() +
                ", eventType='" + eventType + '\'' +
                ", eventData=" + eventData +
                '}';
    }
}

