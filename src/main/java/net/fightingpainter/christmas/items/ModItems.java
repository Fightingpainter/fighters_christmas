package net.fightingpainter.christmas.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fightingpainter.christmas.Main;
import net.fightingpainter.christmas.custom.ModCustom;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {


    public static Item SUGAR_INGOT;
    public static Item CANDY_CANE_RED_INGOT;
    public static Item CANDY_CANE_WHITE_INGOT;
    public static Item CANDY_CANE_RED_WHITE_INGOT;
    public static Item CANDY_CANE_INGOT;
    public static Item CANDY_CANE;

    public static Item CHRISMAS_TREE;

    
    public static ItemGroup MOD_ITEM_GROUP;
    

    public static void register() {
        
        Main.LOGGER.info("Registering Items");
    
        SUGAR_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "sugar_ingot"), new Item(new FabricItemSettings().food(FoodStats.SUGAR_INGOT)));
        CANDY_CANE_RED_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_red_ingot"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_COLOR_INGOT)));
        CANDY_CANE_WHITE_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_white_ingot"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_COLOR_INGOT)));
        CANDY_CANE_RED_WHITE_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_red_white_ingot"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_MIXED_INGOT)));
        CANDY_CANE_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_ingot"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_INGOT)));
        CANDY_CANE = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE)));

        // Register Item Group
        Main.LOGGER.info("Registering Item Group");
        MOD_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, 
            new Identifier(Main.MOD_ID, "item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup."+Main.MOD_ID+".item_group"))
                .icon(() -> new ItemStack(ModCustom.TREE_ITEM)).entries((displayContext, entries) -> {

                    entries.add(ModCustom.GLOBE_ITEM);
                    entries.add(ModCustom.CLOCK_ITEM);
                    entries.add(ModCustom.TREE_ITEM);
                    
                    entries.add(CANDY_CANE);
                    entries.add(CANDY_CANE_INGOT);
                    entries.add(CANDY_CANE_RED_WHITE_INGOT);
                    entries.add(CANDY_CANE_RED_INGOT);
                    entries.add(CANDY_CANE_WHITE_INGOT);
                    entries.add(SUGAR_INGOT);
                }).build());

    }
}
