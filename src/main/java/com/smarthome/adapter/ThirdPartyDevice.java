package com.smarthome.adapter;

/**
 * Represents a third-party device API (fake external interface).
 */
public class ThirdPartyDevice {
    private String deviceId;
    private String deviceType;
    private boolean active = false;;
    
    public ThirdPartyDevice(String deviceId, String deviceType) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
    }

    // Third-party API methods
    public void activate() {
        this.active = true;
        System.out.println("Third-party " + deviceType + " (" + deviceId + ") is now activated.");
    }
    
    public void deactivate() {
        this.active = false;
        System.out.println("Third-party " + deviceType + " (" + deviceId + ") is now deactivated.");}
    
    public String getStatus() {
        return active ? "ACTIVE" : "INACTIVE";
    }

    public String getDeviceId() { return deviceId; }
    public String getDeviceType() { return deviceType; }
}

