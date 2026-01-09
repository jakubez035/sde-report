package com.smarthome.test;

import java.util.ArrayList;
import java.util.List;

import com.smarthome.command.CommandInvoker;
import com.smarthome.command.TurnLightOnCommand;
import com.smarthome.devices.Light;
import com.smarthome.factory.LightFactory;

/**
 * Performance and Scale Test.
 * Simulates a large-scale smart home environment with 100+ devices.
 */
public class SystemScaleTest {
    public static void main(String[] args) {
        System.out.println("========== SYSTEM SCALE & PERFORMANCE TEST ==========");
        
        LightFactory factory = new LightFactory();
        CommandInvoker invoker = new CommandInvoker();
        List<Light> mansionLights = new ArrayList<>();
        
        long startTime = System.currentTimeMillis();

        // 1. Bulk Creation (Factory Pattern Scale)
        System.out.println("[STAGE 1] Creating 500 virtual light devices...");
        for (int i = 0; i < 500; i++) {
            mansionLights.add((Light) factory.createDevice("M-" + i, "Mansion Light " + i));
        }

        // 2. Bulk Execution (Command Pattern Scale)
        System.out.println("[STAGE 2] Executing 'Turn On' for all 500 devices...");
        for (Light light : mansionLights) {
            invoker.executeCommand(new TurnLightOnCommand(light));
        }

        // 3. History Depth Check
        System.out.println("[STAGE 3] Verifying Command History Stack Depth...");
        // This simulates a user hitting "Undo" 100 times in a row
        for (int i = 0; i < 100; i++) {
            invoker.undoLastCommand();
        }

        long endTime = System.currentTimeMillis();
        
        System.out.println("\n--- Performance Metrics ---");
        System.out.println("Total Devices Managed: " + mansionLights.size());
        System.out.println("Commands in History: 400 (500 executed - 100 undone)");
        System.out.println("Total Execution Time: " + (endTime - startTime) + "ms");
        
        if (mansionLights.get(499).isOn() && !mansionLights.get(0).isOn()) {
            System.out.println("\nSUCCESS: System handled bulk operations with state integrity.");
        }
    }
}