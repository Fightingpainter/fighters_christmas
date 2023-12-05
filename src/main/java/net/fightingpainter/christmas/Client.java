package net.fightingpainter.christmas;

import net.fightingpainter.christmas.custom.ModCustom;
import net.fightingpainter.christmas.other.KeyHandler;
import net.fightingpainter.christmas.packets.client.ClientPacketReceiver;
import net.minecraft.client.render.RenderLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

public class Client implements ClientModInitializer{

    @Override
    public void onInitializeClient() {

        KeyHandler.register();

        ClientPacketReceiver.register();

        BlockRenderLayerMap.INSTANCE.putBlock(ModCustom.GLOBE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModCustom.TREE_BLOCK, RenderLayer.getCutout());

        
    }
    
}
