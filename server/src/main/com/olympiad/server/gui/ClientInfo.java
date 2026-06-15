package main.com.olympiad.server.gui;

import javafx.beans.property.*;

/**
 * Observable model for a connected client — add fields here as the server grows.
 */
public class ClientInfo {
    private final IntegerProperty uid;
    private final IntegerProperty packets;

    public ClientInfo(int uid) {
        this.uid     = new SimpleIntegerProperty(uid);
        this.packets = new SimpleIntegerProperty(0);
    }

    // ── Getters / Setters ────────────────────────────────────
    public int getUid()                        { return uid.get(); }
    public void incrementPackets()             { packets.set(packets.get() + 1); }
}