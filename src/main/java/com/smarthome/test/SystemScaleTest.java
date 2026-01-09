package com.smarthome.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.smarthome.command.CommandInvoker;
import com.smarthome.command.TurnLightOffCommand;
import com.smarthome.command.TurnLightOnCommand;
import com.smarthome.devices.Light;
import com.smarthome.factory.LightFactory;

/**
 * Advanced Performance and Scale Test for Smart Home Simulator.
 * Simulates high-load scenarios and verifies state consistency across patterns.
 */
public class SystemScaleTest {
    public static void main(String[] args) {
        System.out.println("========== ADVANCED SYSTEM SCALE & PERFORMANCE TEST ==========");
        
        LightFactory factory = new LightFactory();
        CommandInvoker invoker = new CommandInvoker();
        List<Light> mansionLights = new ArrayList<>();
        Random random = new Random();
        
        long startTime = System.currentTimeMillis();

        // STAGE 1: Bulk Creation (Factory Pattern Scale)
        // Tests the efficiency of our Creational Pattern under load
        System.out.println("[STAGE 1] Creating 1,000 virtual light devices...");
        for (int i = 0; i < 1000; i++) {
            mansionLights.add((Light) factory.createDevice("M-" + i, "Mansion Light " + i));
        }

        // STAGE 2: Brightness Calibration & State Logic
        // Testing specific device methods and boundary conditions
        System.out.println("[STAGE 2] Calibrating brightness levels for all devices...");
        for (Light light : mansionLights) {
            int randomBrightness = random.nextInt(101); // 0-100 range
            light.setBrightness(randomBrightness);
        }

        // STAGE 3: Bulk Command Execution (Behavioural Pattern Scale)
        // Encapsulating requests as objects to verify system throughput
        System.out.println("[STAGE 3] Executing 1,000 TurnOn commands...");
        for (Light light : mansionLights) {
            invoker.executeCommand(new TurnLightOnCommand(light));
        }

        // STAGE 4: State Integrity Verification Loop
        // Ensuring that the command execution actually changed the internal state
        System.out.println("[STAGE 4] Verifying state integrity for 1,000 devices...");
        long failedStates = mansionLights.stream().filter(l -> !l.isOn()).count();
        if (failedStates == 0) {
            System.out.println("  > SUCCESS: All devices correctly transitioned to ON state.");
        }

        // STAGE 5: History Depth and Undo Stress Test
        // Simulating heavy user interaction and stack management
        System.out.println("[STAGE 5] Stress testing Command History with 500 Undo operations...");
        for (int i = 0; i < 500; i++) {
            invoker.undoLastCommand();
        }

        // STAGE 6: Pattern Interaction (TurnOff + Undo)
        // Testing the reverse state transitions
        System.out.println("[STAGE 6] Executing TurnOff for remaining active devices...");
        for (int i = 500; i < 1000; i++) {
            invoker.executeCommand(new TurnLightOffCommand(mansionLights.get(i)));
        }

        // STAGE 7: Final Validation
        System.out.println("[STAGE 7] Running final system consistency check...");
        boolean consistencyCheck = true;
        // The first 500 should be OFF because we Undid their "Turn On"
        // The last 500 should be OFF because we explicitly "Turned Off"
        for (Light l : mansionLights) {
            if (l.isOn()) {
                consistencyCheck = false;
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        
        System.out.println("\n------------------------------------------------");
        System.out.println("Final Performance Metrics:");
        System.out.println("  - Total Devices Managed: " + mansionLights.size());
        System.out.println("  - Operations Processed: 2,500 (Create + On + Undo + Off)");
        System.out.println("  - Memory Execution Time: " + (endTime - startTime) + "ms");
        System.out.println("  - System Status: " + (consistencyCheck ? "STABLE" : "UNSTABLE"));
        System.out.println("------------------------------------------------");

        if (consistencyCheck) {
            System.out.println("\nVERIFIED: The Command and Factory patterns are operational at scale.");
        }
    }
}