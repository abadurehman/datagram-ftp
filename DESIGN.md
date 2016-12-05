Framework Implementation
--------------------------
* Refactor to patterns
* Remove code smells
* Follow design principles e.g. SOLID

# Implemented
The design patterns listed below are the working patterns I have refactored so far. 
My aim is to achieve at least 8 design patterns.

## 1. Logging (Common)
    Normal Singleton -> Threadsafe -> Enum

## 2. State Pattern (Client)
    Converted authentication to state pattern
    When `loginButton` is pressed, Authentication class wrapper delegates
    to its current state reference.
    Eliminated if/else case from UiWindow actionPerformed

## 3. Proxy Pattern (Common, Server, Client)
    Modified implementation to proxy pattern
    IDataSocket interface is the Subject
    DataSocket class is the Proxy on the server side
    Server class is the client
    ClientHelper is the proxy on the client side

    N.B.
    * All return types have to be primitive or serializable
    * A virtual proxy is a placeholder for "expensive to create" objects.
      The real object is only created when a client first requests/accesses the object.


# Possible Implementation
Here are the possible design patterns I could implement.
This includes code smells and code principle violations.

## Switch Case Smell
    Command Pattern ??
    State Pattern

## onResponseCode (Client)
    Observer Pattern ??
    OR State Pattern

## requestByCode (Server)
    Switch Case Smell -> Fix by State pattern

## Swing UI (Client)
    MVC pattern (Observer + Composite)

## Decorator (Common)
    File input and output (IO)
