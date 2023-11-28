package net.fightingpainter.christmas.packets.server;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fightingpainter.christmas.Main;
import net.minecraft.item.ItemStack;

public class ServerPacket {
    
    private final int day;
    private final boolean collected;
    private final List<ItemStack> items;

    public ServerPacket(int day, boolean collected, List<ItemStack> items) {
        this.day = day;
        this.collected = collected;
        this.items = items;
    }

    
    //encode
    public static void encode(ServerPacket packet, PacketByteBuf buf) {
        buf.writeInt(packet.day);
        buf.writeBoolean(packet.collected);
        buf.writeVarInt(packet.items.size());
        for (ItemStack item : packet.items) {
            buf.writeItemStack(item);
        }
    }
    
    //decode
    public static ServerPacket decode(PacketByteBuf buf) {
        int day = buf.readInt();
        boolean collected = buf.readBoolean();
        List<ItemStack> items = new ArrayList<>();
        int size = buf.readVarInt();
        for (int i = 0; i < size; i++) {
            items.add(buf.readItemStack());
        }
        return new ServerPacket(day, collected, items);
    }
   
    
    // Send packet from server to a specific client
    public static void send(ServerPlayerEntity player, int day, boolean collected, List<ItemStack> items) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        encode(new ServerPacket(day, collected, items), buf);
        ServerPlayNetworking.send(player, new Identifier(Main.MOD_ID, ServerPacketReceiver.SERVER_PACKET_NAME), buf);
    }


    // Getters
    //===================================================================================================

    public int getDay() {
        return day;
    }

    public boolean isCollected() {
        return collected;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
