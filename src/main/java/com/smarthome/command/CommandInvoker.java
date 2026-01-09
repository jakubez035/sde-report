package com.smarthome.command;

import java.util.Stack;

/**
 * Invoker that executes commands and supports undo functionality.
 */
public class CommandInvoker {
    private Stack<Command> commandHistory = new Stack<>();
    
    public void executeCommans(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        } else {
            System.out.println("No commands to undo.");
        }
    }

    public void clearHistory() {
        commandHistory.clear();
    }

}

