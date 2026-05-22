package main.com.olympiad.client.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import main.com.olympiad.shared.packets.ChatPacket;
import main.com.olympiad.shared.packets.GameStatePacket;
import main.com.olympiad.shared.packets.MovePacket;
import main.com.olympiad.shared.packets.Packet;

public class PacketHandler {
    private final ServerHandler server;
    private final Gson gson = new Gson();

    public PacketHandler(ServerHandler server) {
        this.server = server;
    }

    public void sendPacket(Packet packet) {
        server.sendPacket(gson.toJson(packet));
    }

    public void sendPacket(String raw) {
        server.sendPacket(raw);
    }

    public void handlePacket(String raw) {
        if (raw.equals("Hello-Packet")) {
            System.out.println(raw);
            System.out.println("Sending ACK");
            sendPacket("ACK Hello-Packet");
            System.out.println("ACK sent");
        }
        /*JsonObject json = gson.fromJson(raw, JsonObject.class);
        String type = json.get("type").getAsString();
        switch (type) {
            case "move" -> handleMovePacket(gson.fromJson(json, MovePacket.class));
            case "chat" -> handleChatPacket(gson.fromJson(json, ChatPacket.class));
            case "gamestate" -> handleGameStatePacket(gson.fromJson(json, GameStatePacket.class));
            default -> System.out.println("Unknown packet type: " + type);
        }*/
    }

    public void handleMovePacket(MovePacket packet) {

    }

    public void handleChatPacket(ChatPacket packet) {

    }

    public void handleGameStatePacket(GameStatePacket packet) {

    }
}
