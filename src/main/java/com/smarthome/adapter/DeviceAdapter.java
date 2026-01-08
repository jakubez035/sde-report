package com.smarthome.adapter;

import com.smarthome.devices.Device;

/**
 * Adapter pattern: Adapts ThirdPartyDevice to the Device interface.
 */
public class DeviceAdapter implements Device {
    private ThirdPartyDevice thirdPartyDevice;
    
    // Implementation will be added later
    
    @Override
    public String getId() { return null; }
    
    @Override
    public String getName() { return null; }
    
    @Override
    public void turnOn() {}
    
    @Override
    public void turnOff() {}
    
    @Override
    public boolean isOn() { return false; }
}

