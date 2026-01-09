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
- **Factory Method**: `DeviceFactory` interface with concrete factories (`LightFactory`, `ThermostatFactory`, `DoorLockFactory`). `LightFactory` allows the system to create Light objects without coupling the client code to concrete classes.
- **Abstract Factory**: `AbstractDeviceFactory` provides a way to create families of related device factories (Light, Thermostat, DoorLock).

### Structural Patterns
- **Facade**: `SmartHomeFacade` provides a high-level, simplified interface to control the entire complex system of devices and sensors.
- **Adapter**: `DeviceAdapter` adapts the `ThirdPartyDevice` API (using `activate()`) to our standard `Device` interface (`turnOn()`), allowing seamless legacy hardware integration.

### Behavioural Patterns
- **Command**: `Command` interface with concrete commands (`TurnLightOnCommand`, `TurnLightOffCommand`, `SetTempCommand`, `LockDoorCommand`, `UnlockDoorCommand`) and `CommandInvoker` for undo support. Encapsulates requests as objects. Alex implemented `TurnLightOnCommand` and `TurnLightOffCommand` with `CommandInvoker` support for a history stack, enabling full Undo functionality.
- **Observer**: `SensorSubject`/`DeviceObserver` pattern for sensor-driven automation. Sensors notify the SmartHomeController to trigger automatic device responses.

### Builder Pattern
- **Builder**: `SceneBuilder` provides a fluent interface for building complex `Scene` configurations step-by-step. It includes validation to ensure scenes cannot be built without a name.

## Comprehensive Testing Suite

To ensure high-quality software design and risk mitigation, the following test suites were implemented:

### Unit Testing (LightTest.java)

 - **Purpose:** Validates individual component logic.

    - Verifies Light initialization defaults (brightness at 100%, status OFF).

    - Tests state transitions and brightness edge-case boundaries.

    - Confirms the LightFactory returns the correct concrete object type.

### Integration Testing (PatternIntegrationTestAlex.java)

- **Purpose:** Verifies interaction between separate design patterns.

    - Command + Adapter: Proves that adapted third-party hardware can still be tracked in the undo history stack.

    - Builder Validation: Tests risk assessment by attempting to build a scene without a name, verifying that IllegalStateException is correctly thrown.

### Scale & Performance Testing (SystemScaleTest.java)

- **Purpose:** Stress tests the system for high-load environments.

    - Bulk Operations: Simulates a mansion environment by creating 1,000 virtual lights via the Factory pattern.

    - History Stress: Executes 2,500 operations and verifies the CommandInvoker stack integrity after 500 consecutive undo actions.

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

To run tests run:
### Unit Tests
mvn exec:java -Dexec.mainClass='com.smarthome.test.LightTest'

### Integration Tests
mvn exec:java -Dexec.mainClass='com.smarthome.test.PatternIntegrationTestAlex'

### Performance Tests
mvn exec:java -Dexec.mainClass='com.smarthome.test.SystemScaleTest'

### Door Lock Test
mvn exec:java -Dexec.mainClass='com.smarthome.test.DoorLockTest'

### Thermostat Test
mvn exec:java -Dexec.mainClass='com.smarthome.test.ThermostatTest'



# Smart Home Simulator - Class Diagram
<img width="3941" height="902" alt="SDE Report Home Simulator Class Diagram" src="https://github.com/user-attachments/assets/7faadd87-3a33-40c0-a321-7771c92e0312" />

# Smart Home Simulator - Flowchart
<img width="8192" height="3929" alt="SDE Report Home Simulator Flowchart" src="https://github.com/user-attachments/assets/b324e0af-1846-4f35-9aee-accc589c0b27" />


## Overview

The Smart Home Simulator class diagram illustrates a Java application that demonstrates multiple design patterns for managing smart home devices, sensors, and automation. The system is organized into seven main packages, each representing different design pattern implementations and functional areas.

## Package Structure

### 1. Devices Package (`com.smarthome.devices`) - Light Blue

**Purpose**: Defines the core `Device` interface and concrete implementations for `Light`, `Thermostat`, and `DoorLock`.

**Key Components**:
- **Device Interface**: The base interface that all smart home devices implement. It provides common operations: `getId()`, `getName()`, `turnOn()`, `turnOff()`, and `isOn()`.
- **Light Class**: Represents a smart light device with brightness control functionality. Implements the `Device` interface and adds specific methods like `setBrightness()` and `getBrightness()`.
- **Thermostat Class**: Represents a smart thermostat device with temperature control. Implements `Device` and provides temperature management methods (`getTemperature()`, `setTemperature()`).
- **DoorLock Class**: Represents a smart door lock device with lock/unlock functionality. Implements `Device` and adds security-specific methods (`lock()`, `unlock()`, `isLocked()`).

**Design Pattern**: Interface Segregation - All devices share a common interface while maintaining device-specific functionality.

### 2. Factory Package (`com.smarthome.factory`) - Light Orange

**Purpose**: Implements `DeviceFactory` for encapsulated creation. Each factory (e.g., `LightFactory`) handles specific initialization logic.

**Key Components**:
- **DeviceFactory Interface**: Defines the factory method pattern for creating devices. Provides a `createDevice()` method that returns a `Device` instance.
- **Concrete Factories**: 
  - `LightFactory`: The LightFactory provides a structured way to generate smart lighting devices while adhering to the DeviceFactory interface. createDevice(String id, String name): This method takes a unique identifier and a user-friendly name, returning a fully initialized Light object.
  Default State Initialization: When created through the factory, the light is automatically set to its default state: OFF with 100% brightness.
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

The Command pattern decouples the object that invokes the operation from the one that knows how to perform it. In your implementation, this allows the SmartHomeSimulator to issue requests without knowing the specific logic of each device.

**Key Components**:
- **Command Interface**: Base interface for all commands with `execute()` (Encapsulates the specific action (e.g., turning a light on).) and `undo()` (Reverses the action by restoring the device to its state prior to execution.) methods.
- **CommandInvoker Class**: Manages command execution and maintains a history stack for undo operations. Provides `executeCommand()`(Triggers the command's logic and pushes it onto the history stack.), `undoLastCommand()` (Pops the most recent command from the stack and calls its undo() method.), and `clearHistory()` (Resets the stack, typically used when changing scenes or restarting the simulation.) methods.
- **Concrete Commands**:
  - `TurnLightOnCommand` & `TurnLightOffCommand`: Encapsulates turning a light on, storing previous state for undo. Before changing the state, it saves the current isOn boolean to the previousState variable. This ensures that if undo() is called, the light returns to exactly how it was (e.g., if it was already on, it stays on).
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
- `SceneBuilder` - The SceneBuilder class serves as the director for the construction process, providing a "fluent interface" that makes the code readable and safe. The builder maintains a temporary Map<Device, String> to store the desired target states before the final object is ever created.
- `Scene` uses `Device` instances to apply states. The Scene object itself is the "Product" of the builder. It acts as a preset configuration that can be "played" at any time to change the state of the entire house. The Scene class contains an execute() method that iterates through its internal map of devices. It doesn't care if the device is a Light, a Thermostat, or an Adapter; it simply interacts with the common Device interface.

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
- `DeviceAdapter` wraps and adapts `ThirdPartyDevice` instances. It allows any `ThirdPartyDevice` to be treated as a standard object within the system. This means it can be added to the CommandInvoker's history stack or included in a Scene created by the SceneBuilder without the system knowing it is actually an external "Legacy" device. The adapter holds a private reference to a ThirdPartyDevice instance. This "wrapping" technique ensures that the original third-party code remains untouched (adhering to the Open/Closed Principle) while still being usable.

### 7. Main Package (`com.smarthome.main`) - Light Gray

**Purpose**: Contains the main application entry point.

**Key Components**:
- **SmartHomeSimulator Class**: Main application class that demonstrates all design patterns working together in an integrated scenario. Contains the `main(String[])` method as the entry point for the application.

**Relationships**: Uses classes from all other packages to demonstrate the complete system functionality. The `SmartHomeSimulator` class has dependency relationships with `DeviceFactory`, `Device`, `Sensor`, `Command`, `Scene`, and `DeviceAdapter` classes.

---

# Pull Request Summary - Jakub Holík

## Overview
This document provides a brief summary of three pull requests that implement core functionality for the Smart Home Simulator, demonstrating multiple design patterns (Factory Method, Command, and Observer).

---

## PR 1: Thermostat Functionality Implementation

**Purpose**: Complete thermostat device implementation with temperature control

**Key Components**:
- **Thermostat.java**: Core device with temperature control (10-30°C range validation)
- **ThermostatFactory.java**: Factory Method pattern for creating thermostat instances
- **SetTempCommand.java**: Command pattern with undo support for temperature changes
- **TemperatureSensor.java**: Observer pattern implementation that monitors temperature and notifies observers
- **SensorEvent.java**: Helper class for sensor event notifications
- **ThermostatTest.java**: Tests

**Design Patterns Demonstrated**:
- Factory Method: Device creation through factory
- Command: Encapsulated temperature operations with undo
- Observer: Temperature sensor notifies observers of significant changes (>2°C)

**Features**: Temperature validation, command undo, sensor-driven automation, error handling

---

## PR 2: DoorLock Functionality Implementation

**Purpose**: Complete door lock device implementation with lock/unlock control

**Key Components**:
- **DoorLock.java**: Core device with lock/unlock functionality and state management
- **DoorLockFactory.java**: Factory Method pattern for creating door lock instances
- **LockDoorCommand.java**: Command pattern with undo for lock operations
- **UnlockDoorCommand.java**: Command pattern with undo for unlock operations
- **DoorLockTest.java**: Tests

**Design Patterns Demonstrated**:
- Factory Method: Device creation through factory
- Command: Encapsulated lock/unlock operations with undo

**Features**: Lock/unlock control, state validation (prevents operations when device is off), command undo, factory flexibility

---

## PR 3: Fix Observer Pattern Implementation

**Purpose**: Fix linter errors and complete Observer pattern implementation

**Key Changes**:
- **AbstractDeviceFactory.java**: Removed unused import to fix linter warning
- **MotionSensor.java**: Completed implementation with motion detection logic (30% random chance), proper observer notification, and event publishing (MOTION_DETECTED, MOTION_STOPPED)
- **SmartHomeController.java**: Completed implementation with reactive automation:
  - MOTION_DETECTED → automatically turns on all registered lights
  - TEMPERATURE_CHANGED → adjusts thermostat (temp < 18°C → 20°C, temp > 23°C → 22°C)
  - Device registration/unregistration management

**Design Patterns Demonstrated**:
- Observer: Complete sensor-observer relationship with reactive device control

**Features**: Motion detection simulation, automatic device control based on sensor events, intelligent temperature management


# Pull Request Summary - Alexander Atanasov

## Overview
This document summarizes the contributions made to implement the core lighting foundation and four essential design patterns (Factory Method, Builder, Command, and Adapter), ensuring a scalable and reversible smart home environment.

---

## PR 4: Lighting and Creational Patterns Implementation

**Purpose**: Establish the lighting foundation and flexible instantiation logic.

**Key Components**:
- **Light.java**: Core device representing a smart light with brightness control.
- **LightFactory.java**: Factory Method pattern for encapsulated device creation.
- **LightTest.java**: Comprehensive unit tests for state transitions and factory accuracy.

**Design Patterns Demonstrated**:
- **Factory Method**: Decouples the client from concrete light instantiation.

**Features**: Default state management (OFF at 100% brightness), automated unit testing.

---

## PR 5: Command and Adapter Patterns Implementation

**Purpose**: Implement reversible device control and legacy hardware integration.

**Key Components**:
- **CommandInvoker.java**: Manages execution and a stack-based history for undo operations.
- **TurnLightOnCommand.java / TurnLightOffCommand.java**: Concrete commands for lighting control.
- **DeviceAdapter.java**: Adapter pattern to bridge `ThirdPartyDevice` with the standard `Device` interface.
- **ThirdPartyDevice.java**: Mock external API representing legacy hardware.

**Design Patterns Demonstrated**:
- **Command**: Encapsulates requests as objects to support multi-level undo.
- **Adapter**: Allows incompatible interfaces to work together via structural wrapping.

**Features**: Reversible state tracking, legacy API translation (activate -> turnOn), structural flexibility.

---

## PR 6: Builder Pattern and System Scale Testing

**Purpose**: Implement complex scene construction and verify system performance.

**Key Components**:
- **Scene.java**: Represents a product containing a map of devices and target states.
- **SceneBuilder.java**: Builder pattern providing a fluent interface for scene configuration.
- **PatternIntegrationTestAlex.java**: Tests interaction between Builder, Command, and Adapter patterns.
- **SystemScaleTest.java**: Stress test simulating 1,000 devices and 2,500 operations.

**Design Patterns Demonstrated**:
- **Builder**: Separates the construction of complex scenes from their representation.

**Features**: Fluent method chaining, build-time validation for scene names, performance metrics for high-load scenarios.