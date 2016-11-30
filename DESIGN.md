# Framework Implementation
* Refactor to patterns
* Remove code smells
* Follow design principles e.g. SOLID

## Logging (Common)
    Normal Singleton -> Threadsafe -> Enum

## State Pattern (Client)
    Converted authentication to state pattern
    When `loginButton` is pressed, Authentication class wrapper delegates to its current state reference.
    Eliminated if/else case from UiWindow actionPerformed

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
