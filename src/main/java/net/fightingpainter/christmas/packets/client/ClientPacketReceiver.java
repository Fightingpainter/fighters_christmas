package net.fightingpainter.christmas.packets.client;

import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fightingpainter.christmas.Main;
import net.fightingpainter.christmas.packets.server.ServerPacket;
import net.fightingpainter.christmas.screens.*;
import net.minecraft.client.MinecraftClient;

public class ClientPacketReceiver {

    // Packet registration
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(Main.MOD_ID, "server_response"), 
            (client, handler, buf, responseSender) -> {
                // Handle the received packet
                ServerPacket packet = ServerPacket.decode(buf);
                client.execute(() -> handle(packet));
            }
        );
    }


    //==========================================HANDLE==========================================

    // Method to handle the packet
    public static void handle(ServerPacket packet) {

        MinecraftClient.getInstance().setScreen(new CalendarScreen(packet.getDay(), packet.isCollected(), packet.getItems()));
    }



}
