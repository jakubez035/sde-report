package com.smarthome.command;

import com.smarthome.devices.DoorLock;

/**
 * Command to unlock a door.
 * Demonstrates Command pattern with undo functionality.
 */
public class UnlockDoorCommand implements Command {
    private DoorLock doorLock;
    private boolean previousLockState;
    private boolean executed = false;

    /**
     * Constructor for UnlockDoorCommand.
     * @param doorLock The door lock to control
     */
    public UnlockDoorCommand(DoorLock doorLock) {
        this.doorLock = doorLock;
    }

    @Override
    public void execute() {
        if (!executed) {
            previousLockState = doorLock.isLocked();
            doorLock.unlock();
            executed = true;
            System.out.println("Command executed: Unlock door");
        } else {
            System.out.println("Command already executed");
        }
    }

    @Override
    public void undo() {
        if (executed) {
            if (previousLockState) {
                doorLock.lock(); // Restore to locked state
            } else {
                doorLock.unlock(); // Restore to unlocked state
            }
            executed = false;
            System.out.println("Command undone: Door lock state restored to " + 
                             (previousLockState ? "LOCKED" : "UNLOCKED"));
        } else {
            System.out.println("Nothing to undo - command not executed yet");
        }
    }

    /**
     * Gets the previous lock state before command execution.
     * @return Previous lock state
     */
    public boolean getPreviousLockState() {
        return previousLockState;
    }
}
