package main.com.olympiad.shared.packets;

public abstract class Packet {
    private final String type;
    private final long timestamp;

    public Packet(String type) {
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }
}
