package net.fightingpainter.christmas;

import net.fightingpainter.christmas.other.KeyHandler;
import net.fightingpainter.christmas.packets.client.ClientPacketReceiver;
import net.fabricmc.api.ClientModInitializer;

public class Client implements ClientModInitializer{

    @Override
    public void onInitializeClient() {

        KeyHandler.register();

        ClientPacketReceiver.register();
    }
    
}
