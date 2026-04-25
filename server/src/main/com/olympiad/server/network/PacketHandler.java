package main.com.olympiad.server.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.com.olympiad.shared.packets.ChatPacket;
import main.com.olympiad.shared.packets.GameStatePacket;
import main.com.olympiad.shared.packets.MovePacket;
import main.com.olympiad.shared.packets.Packet;

public class PacketHandler {
    private final ClientHandler client;
    private final String uid;
    private static final Gson gson = new Gson();

    public PacketHandler(ClientHandler client, String uid) {
        this.client = client;
        this.uid = uid;
    }
    public void sendPacket(Packet packet) {
        client.sendPacket(uid,gson.toJson(packet));
    }

    public void sendPacket(String raw) {
        client.sendPacket(uid,raw);
    }

    public void handlePacket(String raw) {
        JsonObject json = gson.fromJson(raw, JsonObject.class);
        String type = json.get("type").getAsString();
        switch (type) {
            case "move" -> handleMovePacket(gson.fromJson(json, MovePacket.class));
            case "chat" -> handleChatPacket(gson.fromJson(json, ChatPacket.class));
            case "gamestate" -> handleGameStatePacket(gson.fromJson(json, GameStatePacket.class));
            default -> System.out.println("Unknown packet type: " + type);
        }
    }

    public void handleMovePacket(MovePacket packet) {

    }

    public void handleChatPacket(ChatPacket packet) {

    }

    public void handleGameStatePacket(GameStatePacket packet) {

    }
}
