package main.com.olympiad.server;

import main.com.olympiad.server.network.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    private static final int PORT = 12345;
    public static void main(String[] args) {
        try {
            ClientHandler clientHandler = new ClientHandler(new ServerSocket(PORT));
            System.out.println("Server started on port: " + PORT);
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + PORT);
        }
    }
}