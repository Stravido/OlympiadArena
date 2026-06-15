package main.com.olympiad.client.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.com.olympiad.shared.packets.*;

public class PacketHandler {
    private final ClientHandler client;
    private final Gson gson = new Gson();

    public PacketHandler(ClientHandler client) {
        this.client = client;
    }

    public void sendPacket(Packet packet) {
        client.sendPacket(gson.toJson(packet));
        if (packet instanceof DebugPacket) System.out.println(packet);
    }

    public void sendPacket(String raw) {
        client.sendPacket(raw);
    }

    public void handlePacket(String raw) {
        JsonObject json = gson.fromJson(raw, JsonObject.class);
        String type = json.get("type").getAsString();
        switch (type) {
            case "move" -> handleMovePacket(gson.fromJson(json, MovePacket.class));
            case "chat" -> handleChatPacket(gson.fromJson(json, ChatPacket.class));
            case "gamestate" -> handleGameStatePacket(gson.fromJson(json, GameStatePacket.class));
            case "debug" -> handleDebugPacket(gson.fromJson(json, DebugPacket.class));
            default -> System.out.println("Unknown packet type: " + type);
        }
    }

    public void handleMovePacket(MovePacket packet) {

    }

    public void handleChatPacket(ChatPacket packet) {

    }

    public void handleGameStatePacket(GameStatePacket packet) {

    }

    public void handleDebugPacket(DebugPacket packet) {
        System.out.println("DebugPacket: "+packet.getMsg());
    }
}
