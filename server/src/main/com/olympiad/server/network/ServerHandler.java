package main.com.olympiad.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class ServerHandler {
    private static final boolean DEBUG = true;
    private static int clientCount = 0;
    private boolean listening;
    private final ServerSocket sSocket;
    /// Saves the individual InputStreams(client -> server) for each client via the uid
    private HashMap<Integer, BufferedReader> ins = new HashMap<>();
    /// Saves the individual OutputStreams(server -> client) for each client via the uid
    private HashMap<Integer, PrintWriter> outs = new HashMap<>();
    /// Saves the individual PacketHandlers for each client via the uid
    private HashMap<Integer, PacketHandler> packetHandlers = new HashMap<>();

    /**
     * Creates a ServerHandler instance which is used to handle the
     * communication between clients and the server itself
     *
     * @param sSocket The ServerSocket on which the server started
     */
    public ServerHandler(ServerSocket sSocket) {
        this.sSocket = sSocket;
        listening = true;
        new Thread(this::listeningLoop).start();
    }

    /**
     * Begins to listen for clients on the port on which the server was
     * started until the ServerSocket gets closed or the listening attribute
     * is set to false
     */
    public void listeningLoop() {
        try {
            Socket client;
            while (listening) {
                client = sSocket.accept();
                System.out.println("Accepted connection from client: " + client.getInetAddress().getHostName());
                final int uid = clientCount++;
                ins.put(uid, new BufferedReader(new InputStreamReader(client.getInputStream())));
                outs.put(uid, new PrintWriter(client.getOutputStream(), true));
                packetHandlers.put(uid, new PacketHandler(this, uid));
                new Thread(() -> readLoop(uid)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Begins to read from a Socket until the Socket is closed.
     *
     * @param uid The individual uid of which Socket is to be read
     */
    public void readLoop(Integer uid) {
        BufferedReader in = ins.get(uid);
        String line;
        try {
            while ((line = in.readLine()) != null) {
                packetHandlers.get(uid).handlePacket(line);
                if (DEBUG){
                    System.err.println("DEBUG: "+line);
                }
            }
        } catch (IOException e) {
            System.err.println("Couldnt read input stream: " + e.getMessage());
        }
    }

    /**
     * Sends out Text through a Socket's OutputStream in JSON format
     *
     * @param uid The individual uid of which Socket is to be sent from
     * @param raw Text to be sent in JSON format
     */
    public void sendPacket(Integer uid, String raw) {
        outs.get(uid).println(raw);
    }
}