package main.com.olympiad.server.network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClientHandler {
    private static final boolean DEBUG = true;
    private static int clientCount = 0;

    private final ServerSocket sSocket;
    private HashMap<String, BufferedReader> ins = new HashMap<>();
    private HashMap<String, PrintWriter> outs = new HashMap<>();
    private HashMap<String, PacketHandler> packetHandlers = new HashMap<>();

    public ClientHandler(ServerSocket sSocket) {
        this.sSocket = sSocket;
        new Thread(this::listeningLoop).start();
    }

    public void listeningLoop() {
        try {
            Socket client;
            while (true) {
                client = sSocket.accept();
                System.out.println("Accepted connection from client: " + client.getInetAddress().getHostName());
                final String uid = "Client-" + clientCount++;
                ins.put(uid, new BufferedReader(new InputStreamReader(client.getInputStream())));
                outs.put(uid, new PrintWriter(client.getOutputStream(), true));
                packetHandlers.put(uid, new PacketHandler(this, uid));
                new Thread(() -> readLoop(uid)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readLoop(String uid) {
        BufferedReader in = ins.get(uid);
        String line;
        try {
            while ((line = in.readLine()) != null) {
                packetHandlers.get(uid).handlePacket(line);
                if (DEBUG) System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Couldnt read input stream: " + e.getMessage());
        }
    }

    public void sendPacket(String uid, String raw) {
        outs.get(uid).println(raw);
    }
}