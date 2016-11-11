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

            System.out.println("Enter host address: ");
            String hostName = br.readLine();

            if (hostName.isEmpty()) // if user did not enter a name
            {
                hostName = "localhost";  //   use the default host name
            }

            System.out.println("Enter port number: ");
            String portNum = br.readLine();
            if (portNum.isEmpty()) {
                portNum = "3000";          // default port number
            }

            System.out.println("Enter operation code: ");
            String opcode = br.readLine();
            if (opcode.isEmpty()) {
                opcode = "600";
            }

            StringBuffer bufStr = new StringBuffer();
            bufStr.append("!");
            System.out.println("Enter username: ");
            String user = br.readLine();
            if (user.isEmpty()) {
                user = "demo";
            }
            bufStr.append(user);
            bufStr.append("@");
            String username = bufStr.toString();

            System.out.println
                    ("Enter password: ");
            String password = br.readLine();
            if (password.isEmpty()) {
                password = "demo";
            }

            ClientHelper helper = new ClientHelper(hostName, portNum, opcode, username, password);
            String auth = helper.authenticate(opcode, username, password);

            String echo = "";
            boolean done = false;
            while (!done) {
                System.out.println(auth);
                System.out.println("Enter message or period to stop: ");
                String message = br.readLine();
                if (message.trim().equals(endMessage)) {
                    done = true;
                    helper.done();
                } else {
                    echo = helper.getEcho(message);
                    System.out.println(echo);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
