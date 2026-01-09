package com.smarthome.test;

import com.smarthome.devices.Light;
import com.smarthome.factory.LightFactory;

/**
 * Comprehensive test suite for Light and LightFactory.
 * This ensures high-quality contributions for the GitHub repository.
 */
public class LightTest {
    private static int testsPassed = 0;
    private static int totalTests = 0;

    public static void main(String[] args) {
        System.out.println("========== SMART HOME SYSTEM: LIGHT TEST SUITE ==========");
        
        testLightInitialization();
        testLightStateTransitions();
        testBrightnessEdgeCases();
        testLightFactoryInstantiation();
        testToStringFormatting();

        System.out.println("\n========================================================");
        System.out.println("TEST SUMMARY: " + testsPassed + "/" + totalTests + " PASSED");
        System.out.println("========================================================");
    }

    private static void testLightInitialization() {
        totalTests++;
        System.out.println("[TEST] Initial State Check...");
        Light light = new Light("TEST-01", "Basement Light");
        
        if (light.getId().equals("TEST-01") && 
            light.getName().equals("Basement Light") && 
            !light.isOn() && 
            light.getBrightness() == 100) {
            System.out.println("  PASS: Light initialized with correct default values.");
            testsPassed++;
        } else {
            System.out.println("  FAIL: Initial values are incorrect.");
        }
    }

    private static void testLightStateTransitions() {
        totalTests++;
        System.out.println("[TEST] State Transitions (On/Off)...");
        Light light = new Light("TEST-02", "Kitchen Light");
        
        light.turnOn();
        boolean turnedOn = light.isOn();
        
        light.turnOff();
        boolean turnedOff = !light.isOn();
        
        if (turnedOn && turnedOff) {
            System.out.println("  PASS: State correctly toggled between ON and OFF.");
            testsPassed++;
        } else {
            System.out.println("  FAIL: State transition failed logic check.");
        }
    }

    private static void testBrightnessEdgeCases() {
        totalTests++;
        System.out.println("[TEST] Brightness Level Tracking...");
        Light light = new Light("TEST-03", "Bedroom Lamp");
        
        light.setBrightness(50);
        int midValue = light.getBrightness();
        
        light.setBrightness(0);
        int lowValue = light.getBrightness();
        
        if (midValue == 50 && lowValue == 0) {
            System.out.println("  PASS: Brightness values correctly stored and retrieved.");
            testsPassed++;
        } else {
            System.out.println("  FAIL: Brightness tracking error.");
        }
    }

    private static void testLightFactoryInstantiation() {
        totalTests++;
        System.out.println("[TEST] Factory Pattern Implementation...");
        LightFactory factory = new LightFactory();
        
        // Testing that the factory returns a valid Object of the correct type
        Object created = factory.createDevice("F-01", "Factory Light");
        
        if (created instanceof Light) {
            Light castedLight = (Light) created;
            if (castedLight.getName().equals("Factory Light")) {
                System.out.println("  PASS: LightFactory correctly produces Light instances.");
                testsPassed++;
            }
        } else {
            System.out.println("  FAIL: Factory produced incorrect object type.");
        }
    }

    private static void testToStringFormatting() {
        totalTests++;
        System.out.println("[TEST] String Representation Format...");
        Light light = new Light("ID-99", "Porch Light");
        String output = light.toString();
        
        // Verifying the required parts of the string exist
        if (output.contains("ID-99") && output.contains("Porch Light") && output.contains("100%")) {
            System.out.println("  PASS: toString() contains all relevant metadata.");
            testsPassed++;
        } else {
            System.out.println("  FAIL: toString() format is missing information.");
        }
    }
}