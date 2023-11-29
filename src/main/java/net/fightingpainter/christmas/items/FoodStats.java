package net.fightingpainter.christmas.items;

import net.minecraft.item.FoodComponent;

public class FoodStats {

    public static final FoodComponent SUGAR_INGOT = new FoodComponent.Builder()
        .hunger(1)
        .saturationModifier(0f)
    .build();

    public static final FoodComponent CANDY_CANE_COLOR_INGOT = new FoodComponent.Builder()
        .hunger(1)
        .saturationModifier(0.8f)
    .build();

    public static final FoodComponent CANDY_CANE_MIXED_INGOT = new FoodComponent.Builder()
        .hunger(1)
        .saturationModifier(1.6f)
    .build();

    public static final FoodComponent CANDY_CANE_INGOT = new FoodComponent.Builder()
        .hunger(2)
        .saturationModifier(2f)
    .build();

    public static final FoodComponent CANDY_CANE = new FoodComponent.Builder()
        .hunger(4)
        .saturationModifier(8f)
    .build();





    
}
