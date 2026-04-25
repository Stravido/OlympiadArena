package main.com.olympiad.shared.packets;

public class MovePacket extends Packet {
    public final String uid;
    public final int x,y;

    public MovePacket(String uid, int x, int y) {
        super("move");
        this.uid = uid;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Move[plr: " + uid + ", x: " + x + ", y: " + y + "]";
    }
}
