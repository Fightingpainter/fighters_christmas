package net.fightingpainter.christmas.packets.client;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fightingpainter.christmas.Main;

public class ClientPacket {

    private final String action;
    private final int day;

    public ClientPacket(String action, int day) {
        this.action = action;
        this.day = day;
    }


    //encode
    public static void encode(ClientPacket packet, PacketByteBuf buf) {
        buf.writeString(packet.action);
        buf.writeInt(packet.day);
    }

    //decode
    public static ClientPacket decode(PacketByteBuf buf) {
        return new ClientPacket(buf.readString(), buf.readInt());
    }


    // Send packet from client to server
    public static void send(String action, int day) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        encode(new ClientPacket(action, day), buf);
        ClientPlayNetworking.send(new Identifier(Main.MOD_ID, ClientPacketReceiver.CLIENT_PACKET_NAME), buf);
    }


    // Getters
    //===================================================================================================

    public String getAction() {
        return action;
    }

    public int getDay() {
        return day;
    }
}
