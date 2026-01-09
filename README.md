# Smart Home Simulator

A Java CLI application demonstrating design patterns for managing smart home devices, scenes, and sensor events.

## Project Structure

```
src/main/java/com/smarthome/
├── devices/          # Device classes (Light, Thermostat, DoorLock)
├── factory/          # Factory Method & Abstract Factory patterns
├── builder/          # Builder pattern for Scene creation
├── facade/           # Facade pattern for simplified API
├── adapter/          # Adapter pattern for third-party devices
├── command/          # Command pattern with undo support
├── observer/         # Observer pattern for sensor events
├── test/             # Tests
└── main/             # Main application entry point
```

## Design Patterns Implemented

### Creational Patterns
- **Factory Method**: `DeviceFactory` interface with concrete factories (`LightFactory`, `ThermostatFactory`, `DoorLockFactory`)
- **Abstract Factory**: `AbstractDeviceFactory` for creating families of related devices

### Structural Patterns
- **Facade**: `SmartHomeFacade` provides simplified interface to complex system
- **Adapter**: `DeviceAdapter` adapts `ThirdPartyDevice` to `Device` interface

### Behavioural Patterns
- **Command**: `Command` interface with concrete commands (`TurnLightOnCommand`, `TurnLightOffCommand`, `SetTempCommand`, `LockDoorCommand`, `UnlockDoorCommand`) and `CommandInvoker` for undo support
- **Observer**: `SensorSubject`/`DeviceObserver` pattern for sensor event handling

## Building

This project uses Maven. 
To build run:

```bash
mvn compile
```

To run demo run:

```bash
mvn exec:java -Dexec.mainClass='com.smarthome.main.SmartHomeSimulator'
```

# Smart Home Simulator - Class Diagram
<img width="3941" height="902" alt="SDE Report Home Simulator Class Diagram" src="https://github.com/user-attachments/assets/7faadd87-3a33-40c0-a321-7771c92e0312" />

## Overview

The Smart Home Simulator class diagram illustrates a Java application that demonstrates multiple design patterns for managing smart home devices, sensors, and automation. The system is organized into seven main packages, each representing different design pattern implementations and functional areas.

## Package Structure

### 1. Devices Package (`com.smarthome.devices`) - Light Blue

**Purpose**: Defines the core device abstraction and concrete device implementations.

**Key Components**:
- **Device Interface**: The base interface that all smart home devices implement. It provides common operations: `getId()`, `getName()`, `turnOn()`, `turnOff()`, and `isOn()`.
- **Light Class**: Represents a smart light device with brightness control functionality. Implements the `Device` interface and adds specific methods like `setBrightness()` and `getBrightness()`.
- **Thermostat Class**: Represents a smart thermostat device with temperature control. Implements `Device` and provides temperature management methods (`getTemperature()`, `setTemperature()`).
- **DoorLock Class**: Represents a smart door lock device with lock/unlock functionality. Implements `Device` and adds security-specific methods (`lock()`, `unlock()`, `isLocked()`).

**Design Pattern**: Interface Segregation - All devices share a common interface while maintaining device-specific functionality.

### 2. Factory Package (`com.smarthome.factory`) - Light Orange

**Purpose**: Implements creational design patterns for device instantiation.

**Key Components**:
- **DeviceFactory Interface**: Defines the factory method pattern for creating devices. Provides a `createDevice()` method that returns a `Device` instance.
- **Concrete Factories**: 
  - `LightFactory`: Creates `Light` instances
  - `ThermostatFactory`: Creates `Thermostat` instances (with overloaded method for initial temperature)
  - `DoorLockFactory`: Creates `DoorLock` instances (with overloaded method for initial lock state)
- **AbstractDeviceFactory**: Abstract factory pattern that provides methods to obtain factory instances for different device types.
- **StandardDeviceFactory**: Concrete implementation of `AbstractDeviceFactory`.

**Design Patterns**: 
- **Factory Method Pattern**: Each concrete factory encapsulates device creation logic
- **Abstract Factory Pattern**: `AbstractDeviceFactory` provides a way to create families of related factories

**Relationships**: Factories create and return `Device` instances, maintaining loose coupling between device creation and usage.

### 3. Observer Package (`com.smarthome.observer`) - Light Green

**Purpose**: Implements the Observer pattern for sensor-driven automation and event handling.

**Key Components**:
- **Sensor Interface**: Base interface for all sensors with methods `getId()`, `getType()`, and `detect()`.
- **SensorSubject Interface**: Defines the subject role in the Observer pattern with methods `attach()`, `detach()`, and `notifyObservers()`.
- **DeviceObserver Interface**: Defines the observer role with an `update()` method that receives `SensorEvent` notifications.
- **MotionSensor Class**: Concrete sensor that detects motion. Implements both `Sensor` and `SensorSubject` interfaces, allowing it to notify observers when motion is detected or stopped.
- **TemperatureSensor Class**: Concrete sensor that monitors temperature changes. Also implements `Sensor` and `SensorSubject`, notifying observers when significant temperature changes occur.
- **SmartHomeController Class**: The main controller that implements `DeviceObserver` to react to sensor events. It maintains a list of registered devices and automatically controls them based on sensor events (e.g., turning on lights when motion is detected).
- **SensorEvent Class**: A data class that encapsulates sensor event information (sensor reference, event type, and event data).

**Design Pattern**: **Observer Pattern** - Sensors (subjects) notify observers (like `SmartHomeController`) when events occur, enabling reactive automation.

**Relationships**: 
- Sensors implement `SensorSubject` and notify `DeviceObserver` instances
- `SmartHomeController` observes sensors and controls `Device` instances
- `SensorEvent` objects are passed from subjects to observers

### 4. Command Package (`com.smarthome.command`) - Light Purple

**Purpose**: Implements the Command pattern for encapsulating device operations with undo functionality.

**Key Components**:
- **Command Interface**: Base interface for all commands with `execute()` and `undo()` methods.
- **CommandInvoker Class**: Manages command execution and maintains a history stack for undo operations. Provides `executeCommand()`, `undoLastCommand()`, and `clearHistory()` methods.
- **Concrete Commands**:
  - `TurnLightOnCommand`: Encapsulates turning a light on, storing previous state for undo
  - `TurnLightOffCommand`: Encapsulates turning a light off
  - `SetTempCommand`: Encapsulates setting thermostat temperature, storing previous temperature
  - `LockDoorCommand`: Encapsulates locking a door, storing previous lock state
  - `UnlockDoorCommand`: Encapsulates unlocking a door

**Design Pattern**: **Command Pattern** - Commands encapsulate requests as objects, allowing parameterization, queuing, logging, and undo operations.

**Relationships**: 
- Commands implement the `Command` interface
- `CommandInvoker` executes and manages commands
- Commands hold references to specific devices (`Light`, `Thermostat`, `DoorLock`) they operate on

### 5. Builder Package (`com.smarthome.builder`) - Light Yellow

**Purpose**: Implements the Builder pattern for constructing complex scene configurations.

**Key Components**:
- **Scene Class**: Represents a named configuration of device states (e.g., "Away Mode", "Night Mode"). Contains a map of devices to their target states and provides an `execute()` method to apply all states.
- **SceneBuilder Class**: Provides a fluent interface for building `Scene` objects step-by-step. Methods include `setName()`, `addDevice()`, and `build()`. Returns `SceneBuilder` instances for method chaining.

**Design Pattern**: **Builder Pattern** - Separates the construction of complex objects (scenes) from their representation, allowing step-by-step construction.

**Relationships**: 
- `SceneBuilder` creates `Scene` instances
- `Scene` uses `Device` instances to apply states

### 6. Adapter Package (`com.smarthome.adapter`) - Light Pink

**Purpose**: Implements the Adapter pattern for integrating third-party devices with incompatible interfaces.

**Key Components**:
- **ThirdPartyDevice Class**: Represents a legacy or third-party device with a different API (uses `activate()`, `deactivate()`, `getStatus()` instead of standard `Device` methods).
- **DeviceAdapter Class**: Adapts `ThirdPartyDevice` to the `Device` interface by mapping third-party methods to standard device methods:
  - `activate()` → `turnOn()`
  - `deactivate()` → `turnOff()`
  - `getStatus()` → `isOn()`

**Design Pattern**: **Adapter Pattern** - Allows incompatible interfaces to work together by wrapping an object with an adapter that translates between interfaces.

**Relationships**: 
- `DeviceAdapter` implements `Device` interface
- `DeviceAdapter` wraps and adapts `ThirdPartyDevice` instances

### 7. Main Package (`com.smarthome.main`) - Light Gray

**Purpose**: Contains the main application entry point.

**Key Components**:
- **SmartHomeSimulator Class**: Main application class that demonstrates all design patterns working together in an integrated scenario. Contains the `main(String[])` method as the entry point for the application.

**Relationships**: Uses classes from all other packages to demonstrate the complete system functionality. The `SmartHomeSimulator` class has dependency relationships with `DeviceFactory`, `Device`, `Sensor`, `Command`, `Scene`, and `DeviceAdapter` classes.
