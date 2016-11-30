Framework Implementation
--------------------------
* Refactor to patterns
* Remove code smells
* Follow design principles e.g. SOLID

# Implemented
The design patterns listed below are the working patterns I have refactored so far. 
My aim is to achieve at least apply at least 8 design patterns. 

## 1. Logging (Common)
    Normal Singleton -> Threadsafe -> Enum

## 2. State Pattern (Client)
    Converted authentication to state pattern
    When `loginButton` is pressed, Authentication class wrapper delegates to its current state reference.
    Eliminated if/else case from UiWindow actionPerformed

# Possible Implementation
Here are the possible design patterns I could implement. This includes code smells and code principle violations.

## Switch Case Smell
    Command Pattern ??
    State Pattern

## onResponseCode (Client)
    Observer Pattern ??
    OR State Pattern

## Client-Server
    Proxy Pattern

## requestByCode (Server)
   Switch Case Smell -> Fix by State pattern
