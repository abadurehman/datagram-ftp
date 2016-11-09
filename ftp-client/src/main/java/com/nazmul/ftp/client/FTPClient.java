package com.nazmul.ftp.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This module contains the presentaton logic of an Echo Client.
 *
 * @author M. L. Liu
 */
public class FTPClient {
    static final String endMessage = ".";

    public static void main(String... args) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        try {

            System.out.println("Welcome to the Echo client.\n" +
                    "What is the name of the server host?");
            String hostName = br.readLine();

            if (hostName.isEmpty()) // if user did not enter a name
            {
                hostName = "localhost";  //   use the default host name
            }

            System.out.println("What is the port number of the server host?");
            String portNum = br.readLine();
            if (portNum.isEmpty()) {
                portNum = "3000";          // default port number
            }

            ClientHelper helper =
                    new ClientHelper(hostName, portNum);

            boolean done = false;
            String echo;

            while (!done) {
                System.out.println("Enter a line to receive an echo back from the server, "
                        + "or a single peroid to quit.");
                String message = br.readLine();
                if (message.trim().equals(endMessage)) {
                    done = true;
                    helper.done();
                } else {
                    echo = helper.getEcho(message);
                    System.out.println(echo);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
