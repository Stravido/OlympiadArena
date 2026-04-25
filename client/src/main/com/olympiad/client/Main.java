package main.com.olympiad.client;

import main.com.olympiad.client.network.ServerHandler;

import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final int PORT = 12345;
    private static final String IP = "localhost";

    public static void main(String[] args) {
        try {
            ServerHandler serverHandler = new ServerHandler(new Socket(IP, PORT));
            System.out.println("Looking for server on port: " + PORT);
        } catch (IOException e) {
            System.out.println("Error on connecting to server: " + e.getMessage());
        }
    }
}
