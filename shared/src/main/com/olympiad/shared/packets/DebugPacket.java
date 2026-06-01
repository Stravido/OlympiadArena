package main.com.olympiad.shared.packets;

public class DebugPacket extends Packet {
    public final String msg;
    public DebugPacket(String msg) {
        super("debug");
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Debug[msg: "+msg+"]";
    }
}
