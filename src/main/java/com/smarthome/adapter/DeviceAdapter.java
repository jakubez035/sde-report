package com.smarthome.adapter;

import com.smarthome.devices.Device;

/**
 * Adapter pattern: Adapts ThirdPartyDevice to the Device interface.
 */
public class DeviceAdapter implements Device {
    private ThirdPartyDevice thirdPartyDevice;
    
    public DeviceAdapter(ThirdPartyDevice thirdPartyDevice) {
        this.thirdPartyDevice = thirdPartyDevice;
    }
    
    @Override
    public String getId() { 
        return thirdPartyDevice.getDeviceId();
    }
    
    @Override
    public String getName() { 
        return "Adapted-" + thirdPartyDevice.getDeviceId();
    }
    
    @Override
    public void turnOn() {
        // Mapping 'turnOn' to the third-party 'activate' method
        thirdPartyDevice.activate();
    }
    
    @Override
    public void turnOff() {
        // Mapping 'turnOff' to the third-party 'deactivate' method
        thirdPartyDevice.deactivate();
    }
    
    @Override
    public boolean isOn() { 
        return thirdPartyDevice.getStatus().equals("ACTIVE");
     }
}

