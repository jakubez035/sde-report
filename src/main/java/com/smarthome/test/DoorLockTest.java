package com.smarthome.test;

import com.smarthome.command.LockDoorCommand;
import com.smarthome.command.UnlockDoorCommand;
import com.smarthome.devices.DoorLock;
import com.smarthome.factory.DoorLockFactory;

/**
 * Test class to demonstrate door lock functionality.
 * Run this to see all patterns in action.
 */
public class DoorLockTest {
    public static void main(String[] args) {
        System.out.println("=== Door Lock Functionality Test ===\n");
        
        // Test 1: Factory Pattern - Create door lock
        System.out.println("--- Test 1: Factory Pattern ---");
        DoorLockFactory factory = new DoorLockFactory();
        DoorLock doorLock = (DoorLock) factory.createDevice("DL1", "Front Door Lock");
        System.out.println("Created: " + doorLock);
        System.out.println();
        
        // Test 2: Device functionality
        System.out.println("--- Test 2: Device Functionality ---");
        doorLock.turnOn();
        System.out.println("Is on: " + doorLock.isOn());
        System.out.println("Is locked: " + doorLock.isLocked());
        System.out.println();
        
        // Test 3: Direct lock/unlock operations
        System.out.println("--- Test 3: Direct Lock/Unlock Operations ---");
        doorLock.lock();
        System.out.println("Is locked: " + doorLock.isLocked());
        
        doorLock.unlock();
        System.out.println("Is locked: " + doorLock.isLocked());
        
        doorLock.lock();
        System.out.println("Is locked: " + doorLock.isLocked());
        System.out.println();
        
        // Test 4: Command Pattern with Undo
        System.out.println("--- Test 4: Command Pattern with Undo ---");
        doorLock.unlock(); // Start unlocked
        System.out.println("Initial state - Is locked: " + doorLock.isLocked());
        
        LockDoorCommand lockCmd1 = new LockDoorCommand(doorLock);
        lockCmd1.execute();
        System.out.println("Current state - Is locked: " + doorLock.isLocked());
        
        UnlockDoorCommand unlockCmd = new UnlockDoorCommand(doorLock);
        unlockCmd.execute();
        System.out.println("Current state - Is locked: " + doorLock.isLocked());
        
        LockDoorCommand lockCmd2 = new LockDoorCommand(doorLock);
        lockCmd2.execute();
        System.out.println("Current state - Is locked: " + doorLock.isLocked());
        
        System.out.println("\n--- Undoing commands ---");
        lockCmd2.undo();
        System.out.println("Current state - Is locked: " + doorLock.isLocked());
        
        unlockCmd.undo();
        System.out.println("Current state - Is locked: " + doorLock.isLocked());
        
        lockCmd1.undo();
        System.out.println("Current state - Is locked: " + doorLock.isLocked());
        System.out.println();
        
        // Test 5: Factory with initial lock state
        System.out.println("--- Test 5: Factory with Initial Lock State ---");
        DoorLock doorLock2 = (DoorLock) factory.createDevice("DL2", "Back Door Lock", true);
        doorLock2.turnOn();
        System.out.println("Created with initial lock state: " + doorLock2);
        System.out.println("Is locked: " + doorLock2.isLocked());
        System.out.println();
        
        // Test 6: Lock/unlock when device is off
        System.out.println("--- Test 6: Lock/Unlock When Device is Off ---");
        doorLock.turnOff();
        System.out.println("Device is off. Attempting to lock...");
        doorLock.lock();
        System.out.println("Attempting to unlock...");
        doorLock.unlock();
        System.out.println();
        
        // Test 7: Multiple lock/unlock operations
        System.out.println("--- Test 7: Multiple Lock/Unlock Operations ---");
        doorLock.turnOn();
        doorLock.lock();
        doorLock.lock(); // Already locked
        doorLock.unlock();
        doorLock.unlock(); // Already unlocked
        System.out.println();
        
        // Test 8: Turn off
        System.out.println("--- Test 8: Turn Off ---");
        doorLock.turnOff();
        System.out.println("Is on: " + doorLock.isOn());
        System.out.println();
        
        System.out.println("=== All Tests Complete ===");
    }
}
