# Documenting protocol

## Introduction + objectives

## Use sequence  Diagram

### Define Messages and format of messages e.g.
1. Message: Login
2. Description: The user can log into the server with a username and password
3. Code/Number: 100
4. Message Parameters: username, password (both text)
5. Response Message
    - Code: 101
    - Text: login successful
6. Response Message
    - Code: 102
    - Text: login successful


### Describe how functionality of each message is implemented
e.g. Implementing the Connection message on the server as a function called login()
	// save the username and password
	// is a folder ! exists for username create it
	// return 101: login successful
 	// if cant create folder return 102

### Sequence diagram of Protocol Messages

See RFC’s online [RFC768](https://www.ietf.org/rfc/rfc768.txt)
