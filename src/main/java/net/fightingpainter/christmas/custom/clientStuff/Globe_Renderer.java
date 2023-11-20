package net.fightingpainter.christmas.custom.clientStuff;

import net.fightingpainter.christmas.custom.*;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class Globe_Renderer extends GeoBlockRenderer<Globe_BlockEntity> {
    public Globe_Renderer(BlockEntityRendererFactory.Context context) {
        super(new Globe_Model());
    }
}