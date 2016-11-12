package com.nazmul.ftp.client.console;

import com.nazmul.ftp.client.ClientHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FTPClient {
    static final String endMessage = ".";


    public static void main(String... args) {

        try {

            System.out.println("Enter host address: ");
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
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

            StringBuilder bufStr = new StringBuilder();
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
            String pass = password + "!";

            ClientHelper helper = new ClientHelper(hostName, portNum, opcode, username, pass);
            String auth = helper.authenticate(opcode, username, pass);

            boolean done = false;
            while (!done) {
                System.out.println(auth);
                System.out.println("Enter message or period to stop: ");
                String message = br.readLine();
                if (message.trim().equals(endMessage)) {
                    done = true;
                    helper.done();
                } else {
                    String echo = helper.sendRequest(message);
                    System.out.println(echo);
                }
            }

        } catch (Exception ex) {
            System.out.println("Status: " + ex.getMessage());
        }
    }
}
