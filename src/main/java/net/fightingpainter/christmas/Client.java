package net.fightingpainter.christmas;

import net.fightingpainter.christmas.custom.ModCustom;
import net.fightingpainter.christmas.custom.clientStuff.*;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class Client implements ClientModInitializer{

    @Override
    public void onInitializeClient() {

        BlockEntityRendererFactories.register(ModCustom.GLOBE_BLOCK_ENTITY_TYPE, Globe_Renderer::new);
    }
    
}
