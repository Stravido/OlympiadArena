package main.com.olympiad.client.network;

import main.com.olympiad.shared.packets.DebugPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    private static final boolean DEBUG = true;
    private final Socket socket;
    private final PacketHandler packetHandler;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Creates a ClientHandler instance which is used to handle the communication
     * between client and server via Sockets
     *
     * @param socket The client Socket on which a ServerSocket is connected to
     */
    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Could not get input or output stream: " + e.getMessage());
        }
        this.packetHandler = new PacketHandler(this);
        new Thread(this::readLoop).start();
        DebugPacket dp = new DebugPacket("SYNC");
        packetHandler.sendPacket(dp);
    }

    /**
     * Begins to read from a Socket until the Socket is closed.
     */
    public void readLoop() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                packetHandler.handlePacket(line);
                if (DEBUG) System.err.println("DEBUG: "+line);
            }
        } catch (IOException e) {
            System.err.println("Could not read input stream: " + e.getMessage());
        }
    }

    /**
     * Sends out Text through the Socket's OutputStream in JSON format
     *
     * @param raw Text to be sent in JSON format
     */
    public void sendPacket(String raw) {
        out.println(raw);
    }
}
