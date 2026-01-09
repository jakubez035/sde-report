package com.smarthome.main;

import com.smarthome.adapter.DeviceAdapter;
import com.smarthome.adapter.ThirdPartyDevice;
import com.smarthome.builder.Scene;
import com.smarthome.builder.SceneBuilder;
import com.smarthome.command.Command;
import com.smarthome.command.CommandInvoker;
import com.smarthome.command.TurnLightOnCommand;
import com.smarthome.devices.Light;
import com.smarthome.factory.LightFactory;

public class AlexDemo {
    public static void main(String[] args) {
        System.out.println("--- 1. Factory Pattern Demo ---");
        LightFactory lightFactory = new LightFactory();
        Light livingRoomLight = (Light) lightFactory.createDevice("L1", "Living Room Light");
        System.out.println("Created: " + livingRoomLight.getName());

        System.out.println("\n--- 2. Command Pattern (with Undo) Demo ---");
        CommandInvoker invoker = new CommandInvoker();
        Command lightOn = new TurnLightOnCommand(livingRoomLight);
        
        System.out.print("Action: ");
        invoker.executeCommand(lightOn);
        
        System.out.print("Undo Action: ");
        invoker.undoLastCommand();

        System.out.println("\n--- 3. Builder Pattern Demo ---");
        SceneBuilder builder = new SceneBuilder();
        Scene movieScene = builder.setName("Movie Night")
                                  .addDevice(livingRoomLight, "OFF")
                                  .build();
        movieScene.execute();

        System.out.println("\n--- 4. Adapter Pattern Demo ---");
        ThirdPartyDevice legacyFan = new ThirdPartyDevice("F99", "OldFan");
        DeviceAdapter adaptedFan = new DeviceAdapter(legacyFan);
        
        System.out.println("Using Adapted Device: " + adaptedFan.getName());
        adaptedFan.turnOn(); // This internally calls legacyFan.activate()
    }
}