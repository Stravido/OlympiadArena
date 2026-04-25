package main.com.olympiad.shared.packets;

public class ChatPacket extends Packet {
    public final String uid;
    public final String msg;


    public ChatPacket(String uid, String msg) {
        super("chat");
        this.uid = uid;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Chat[plr: " + uid + ", msg: " + msg + "]";
    }
}
