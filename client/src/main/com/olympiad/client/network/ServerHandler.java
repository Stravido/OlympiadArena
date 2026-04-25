package main.com.olympiad.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler {
    private static final boolean DEBUG = true;

    private final Socket socket;
    private final PacketHandler packetHandler;
    private BufferedReader in;
    private PrintWriter out;

    public ServerHandler(Socket socket) {
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Could not get input stream: " + e.getMessage());
        }
        this.packetHandler = new PacketHandler(this);
        new Thread(this::readLoop).start();
    }

    public void readLoop() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                packetHandler.handlePacket(line);
                if (DEBUG) System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Could read input stream: " + e.getMessage());
        }
    }

    public void sendPacket(String raw) {
        out.println(raw);
    }
}
