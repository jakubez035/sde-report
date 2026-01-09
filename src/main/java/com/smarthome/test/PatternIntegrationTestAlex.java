package com.smarthome.test;

import java.util.ArrayList;
import java.util.List;

import com.smarthome.adapter.DeviceAdapter;
import com.smarthome.adapter.ThirdPartyDevice;
import com.smarthome.builder.Scene;
import com.smarthome.builder.SceneBuilder;
import com.smarthome.command.Command;
import com.smarthome.command.CommandInvoker;
import com.smarthome.command.TurnLightOffCommand;
import com.smarthome.command.TurnLightOnCommand;
import com.smarthome.devices.Light;

/**
 * Integration tests for design patterns.
 * Tests the interaction between Builder, Command, and Adapter patterns.
 */
public class PatternIntegrationTestAlex {
    private static int testsPassed = 0;
    private static int totalTests = 0;

    public static void main(String[] args) {
        System.out.println("========== MASTER PATTERN INTEGRATION SUITE ==========");
        
        testCommandHistoryStress();
        testComplexSceneBuilder();
        testAdapterInterfaceMapping();
        testBuilderValidationEdgeCase();
        testFullSystemWorkflow();

        System.out.println("\n======================================================");
        System.out.println("INTEGRATION SUMMARY: " + testsPassed + "/" + totalTests + " PASSED");
        System.out.println("======================================================");
    }

    private static void testCommandHistoryStress() {
        totalTests++;
        System.out.println("[TEST] Command Pattern: History & Undo Stress Test...");
        CommandInvoker invoker = new CommandInvoker();
        Light testLight = new Light("L-STRESS", "Stress Test Light");
        
        // Execute 10 toggle commands
        for (int i = 0; i < 5; i++) {
            invoker.executeCommand(new TurnLightOnCommand(testLight));
            invoker.executeCommand(new TurnLightOffCommand(testLight));
        }

        // Undo all 10 commands
        boolean logicMaintained = true;
        for (int i = 0; i < 10; i++) {
            invoker.undoLastCommand();
        }

        if (!testLight.isOn()) { // Should be back to initial state (OFF)
            System.out.println("  PASS: Stack maintained integrity through 10 rapid state changes.");
            testsPassed++;
        } else {
            System.out.println("  FAIL: Command history corruption detected.");
        }
    }

    private static void testComplexSceneBuilder() {
        totalTests++;
        System.out.println("[TEST] Builder Pattern: Multi-Device Scene Construction...");
        SceneBuilder builder = new SceneBuilder();
        
        List<Light> lights = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            lights.add(new Light("ID-" + i, "Light " + i));
        }

        builder.setName("Grand Entrance");
        for (Light l : lights) {
            builder.addDevice(l, "ON");
        }
        
        Scene grandEntrance = builder.build();
        grandEntrance.execute();

        boolean allOn = lights.stream().allMatch(Light::isOn);
        if (allOn && grandEntrance.getName().equals("Grand Entrance")) {
            System.out.println("  PASS: Complex Scene with 5 devices built and executed correctly.");
            testsPassed++;
        } else {
            System.out.println("  FAIL: Scene execution failed for one or more devices.");
        }
    }

    private static void testAdapterInterfaceMapping() {
        totalTests++;
        System.out.println("[TEST] Adapter Pattern: Third-Party API Mapping...");
        ThirdPartyDevice legacyHardware = new ThirdPartyDevice("HW-99", "LegacyFan");
        DeviceAdapter adapter = new DeviceAdapter(legacyHardware);

        adapter.turnOn();
        boolean activated = adapter.isOn();
        
        adapter.turnOff();
        boolean deactivated = !adapter.isOn();

        if (activated && deactivated && adapter.getName().contains("Adapted-")) {
            System.out.println("  PASS: Adapter correctly translated standard calls to third-party API.");
            testsPassed++;
        } else {
            System.out.println("  FAIL: Adapter mapping error.");
        }
    }

    private static void testBuilderValidationEdgeCase() {
        totalTests++;
        System.out.println("[TEST] Risk Assessment: Builder Validation Logic...");
        SceneBuilder faultyBuilder = new SceneBuilder();
        
        try {
            // Attempting to build without setting a name
            faultyBuilder.build();
            System.out.println("  FAIL: Builder allowed creation without a required name.");
        } catch (IllegalStateException e) {
            System.out.println("  PASS: Builder correctly threw IllegalStateException for missing name.");
            testsPassed++;
        }
    }

    private static void testFullSystemWorkflow() {
        totalTests++;
        System.out.println("[TEST] Full System: Command + Adapter Integration...");
        CommandInvoker invoker = new CommandInvoker();
        ThirdPartyDevice hardware = new ThirdPartyDevice("HW-XYZ", "AC_Unit");
        DeviceAdapter adaptedAC = new DeviceAdapter(hardware);

        // Even an adapted device should work with the Command Pattern
        // We need a TurnOnCommand that takes the Device interface
        // For this test, we demonstrate the adapter's compatibility
        invoker.executeCommand(new Command() {
            @Override public void execute() { adaptedAC.turnOn(); }
            @Override public void undo() { adaptedAC.turnOff(); }
        });

        if (adaptedAC.isOn()) {
            invoker.undoLastCommand();
            if (!adaptedAC.isOn()) {
                System.out.println("  PASS: Adapted hardware successfully integrated with Command history.");
                testsPassed++;
                return;
            }
        }
        System.out.println("  FAIL: System integration failure.");
    }
}