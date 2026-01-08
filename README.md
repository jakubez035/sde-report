# Smart Home Simulator (CLI)

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
- **Command**: `Command` interface with concrete commands (`TurnLightOnCommand`, `SetTempCommand`, etc.) and `CommandInvoker` for undo support
- **Observer**: `SensorSubject`/`DeviceObserver` pattern for sensor event handling

## Building

This project uses Maven. To build:

```bash
mvn compile
```

## Status

Project structure created. Implementation in progress.