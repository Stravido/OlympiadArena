package main.com.olympiad.client;

import main.com.olympiad.client.network.ClientHandler;

import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final int PORT = 12345;
    private static final String IP = "localhost";

    public static void main(String[] args) {
        try {
            System.out.println("Looking for server: " + IP + ":" + PORT);
            ClientHandler clientHandler = new ClientHandler(new Socket(IP, PORT));
        } catch (IOException e) {
            System.out.println("Error on connecting to server: " + e.getMessage());
        }
    }
}
