# Datagram FTP
A simple **File Transfer Protocol** client-server application using the **Java Socket API**.

## Build
Assuming you have the latest version of Java installed and the JAVA_HOME and PATH are set properly; please run these commands from the parent directory i.e. datagram-ftp

### Windows
`mvnw clean install`

### *nix
`./mvnw clean install`

## Run
First run the server: `java -jar ftp-server/target/ftp-server-1.0-SNAPSHOT-jar-with-dependencies.jar`
Then run the client: `java -jar ftp-client/target/ftp-client-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Objective(s):

To practice the: 

- design and implementation of a message passing protocol.
- design and implementation of a serial client-server application using the Java socket API.

## Requirement(s):

Each student will complete the design and implement a client-server file management system using Datagrams. The system should allow each client user to reliably upload and download a single file to and from the file server. Using the system, the client user should be able to:

1. Log–on
2. Upload a file to a folder (unique to each client) on the server.
3. Download a file to a folder
4. Log-off

The student will need to consider:

- Protocol Design
- Application Design and Implementation (Software Architecture)

## Format

                                    
                  0      7 8     15 16    23 24    31  
                 +--------+--------+--------+--------+ 
                 |     Source      |   Destination   | 
                 |      Port       |      Port       | 
                 +--------+--------+--------+--------+ 
                 |                 |                 | 
                 |     Length      |    Checksum     | 
                 +--------+--------+--------+--------+ 
                 |                                     
                 |          data octets ...            
                 +---------------- ...                 

                      User Datagram Header Format

