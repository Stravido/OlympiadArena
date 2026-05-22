package main.com.olympiad.server.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.com.olympiad.shared.packets.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler {
    private static final Gson gson = new Gson();

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

                //System.out.println(gson.toJson("Hello-Packet", JsonObject.class));
                sendPacket(uid, "Hello-Packet");
                new Thread(() -> {
                    readLoop(uid);
                }).start();
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
            System.err.println("Couldnt read input stream of client " + uid + ": " + e.getMessage());
        }
    }

    public void sendPacket(String uid, String raw) {
        PrintWriter out = outs.get(uid);
        System.out.println(out!=null?"Output exists!":"No output found :(");
        out.println(raw);
        System.out.println("Packet sent: " + raw);
    }
}