package net.fightingpainter.christmas.custom.clientStuff;

import net.fightingpainter.christmas.Main;
import net.fightingpainter.christmas.custom.*;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class Globe_Item_Model extends GeoModel<Globe_Item> {
    @Override
    public Identifier getModelResource(Globe_Item animatable) {
        return new Identifier(Main.MOD_ID, "geo/globe.geo.json");
    }

    @Override
    public Identifier getTextureResource(Globe_Item animatable) {
        return new Identifier(Main.MOD_ID, "textures/block/globe.png");
    }

    @Override
    public Identifier getAnimationResource(Globe_Item animatable) {
        return new Identifier(Main.MOD_ID, "animations/globe.animation.json");
    }
}