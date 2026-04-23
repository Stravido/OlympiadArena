package main.com.olympiad.client.network;

import com.google.gson.Gson;
import main.com.olympiad.shared.packets.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler {
    private final Gson gson = new Gson();
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
            System.err.println("Could not get input stream: "+e.getMessage());
        }
        this.packetHandler = new PacketHandler(this);
        new Thread(this::readLoop).start();
    }

    public void readLoop(){
        String line;
        try{
            while ((line = in.readLine()) != null) {
                packetHandler.handlePacket(line);
            }
        } catch (IOException e) {
            System.err.println("Could read input stream: "+e.getMessage());
        }
    }

    public void sendPacket(String raw) {
        out.println(raw);
    }
    public void sendPacket(Packet packet) {
        out.println(gson.toJson(packet));
    }
}
