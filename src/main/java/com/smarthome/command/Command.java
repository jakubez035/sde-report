package com.smarthome.command;

/**
 * Command pattern: Base interface for all commands.
 */
public interface Command {
    void execute();
    void undo();
}

