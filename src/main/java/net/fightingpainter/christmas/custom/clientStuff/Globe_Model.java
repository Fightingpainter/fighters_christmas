package net.fightingpainter.christmas.custom.clientStuff;

import net.fightingpainter.christmas.Main;
import net.fightingpainter.christmas.custom.Globe_BlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class Globe_Model extends GeoModel<Globe_BlockEntity> {
    @Override
    public Identifier getModelResource(Globe_BlockEntity animatable) {
        return new Identifier(Main.MOD_ID, "geo/globe.geo.json");
    }

    @Override
    public Identifier getTextureResource(Globe_BlockEntity animatable) {
        return new Identifier(Main.MOD_ID, "textures/block/globe.png");
    }

    @Override
    public Identifier getAnimationResource(Globe_BlockEntity animatable) {
        return new Identifier(Main.MOD_ID, "animations/globe.animation.json");
    }
}