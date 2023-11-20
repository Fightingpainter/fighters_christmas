package net.fightingpainter.christmas.custom.clientStuff;

import net.fightingpainter.christmas.custom.*;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Globe_Item_Renderer extends GeoItemRenderer<Globe_Item> {
    public Globe_Item_Renderer() {
        super(new Globe_Item_Model());
    }
}