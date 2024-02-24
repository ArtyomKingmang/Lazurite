package com.kingmang.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleTCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = consoleReader.readLine()) != null) {
                writer.println(message);
            }

            consoleReader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}