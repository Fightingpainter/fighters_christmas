package net.fightingpainter.christmas.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fightingpainter.christmas.Main;
import net.fightingpainter.christmas.custom.ModCustom;
import net.fightingpainter.christmas.items.tools.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item SUGAR_NUGGET;
    public static Item SUGAR_INGOT;
    public static Item CANDY_CANE_RED_NUGGET;
    public static Item CANDY_CANE_RED_INGOT;
    public static Item CANDY_CANE_WHITE_NUGGET;
    public static Item CANDY_CANE_WHITE_INGOT;
    public static Item CANDY_CANE_RED_WHITE_NUGGET;
    public static Item CANDY_CANE_RED_WHITE_INGOT;
    public static Item CANDY_CANE_NUGGET;
    public static Item CANDY_CANE_INGOT;
    public static Item CANDY_CANE;
    public static Item CANDY_CANE_ROD;
    
    public static Item CANDY_CANE_SWORD;
    public static Item CANDY_CANE_PICKAXE;
    public static Item CANDY_CANE_AXE;
    public static Item CANDY_CANE_SHOVEL;
    public static Item CANDY_CANE_HOE;
    
    public static Item ENCHANTED_CANDY_CANE;
    public static Item CANDY_CANE_MULTITOOL;

    public static ItemGroup MOD_ITEM_GROUP;
    

    public static void register() {
        
        Main.LOGGER.info("Registering Items");
    
        SUGAR_NUGGET = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "sugar_nugget"), new Item(new FabricItemSettings().food(FoodStats.SUGAR_INGOT)));
        SUGAR_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "sugar_ingot"), new Item(new FabricItemSettings().food(FoodStats.SUGAR_INGOT)));
        CANDY_CANE_RED_NUGGET = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_red_nugget"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_COLOR_INGOT)));
        CANDY_CANE_RED_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_red_ingot"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_COLOR_INGOT)));
        CANDY_CANE_WHITE_NUGGET = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_white_nugget"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_COLOR_INGOT)));
        CANDY_CANE_WHITE_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_white_ingot"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_COLOR_INGOT)));
        CANDY_CANE_RED_WHITE_NUGGET = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_red_white_nugget"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_MIXED_INGOT)));
        CANDY_CANE_RED_WHITE_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_red_white_ingot"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_MIXED_INGOT)));
        CANDY_CANE_NUGGET = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_nugget"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_INGOT)));
        CANDY_CANE_INGOT = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_ingot"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE_INGOT)));
        CANDY_CANE = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE)));
        CANDY_CANE_ROD = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_rod"), new Item(new FabricItemSettings().food(FoodStats.CANDY_CANE)));
        
        CANDY_CANE_SWORD = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_sword"), new CandyCaneSword(CandyCaneMaterial.CANDYCANE, 12, -1f, new FabricItemSettings()));
        CANDY_CANE_PICKAXE = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_pickaxe"), new CandyCanePickaxe(CandyCaneMaterial.CANDYCANE, 6, -2.8f, new FabricItemSettings()));
        CANDY_CANE_AXE = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_axe"), new CandyCaneAxe(CandyCaneMaterial.CANDYCANE, 10, -3.0f, new FabricItemSettings()));
        CANDY_CANE_SHOVEL = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_shovel"), new CandyCaneShovel(CandyCaneMaterial.CANDYCANE, 6, -3.0f, new FabricItemSettings()));
        CANDY_CANE_HOE = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_hoe"), new CandyCaneHoe(CandyCaneMaterial.CANDYCANE, 7, -2.4f, new FabricItemSettings()));
        
        CANDY_CANE_MULTITOOL = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "candy_cane_multitool"), new CandyCaneMultitool(CandyCaneMaterial.CANDYCANE, 14, -3f, new FabricItemSettings()));
        
        // Register Item Group
        Main.LOGGER.info("Registering Item Group");
        MOD_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, 
            new Identifier(Main.MOD_ID, "item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup."+Main.MOD_ID+".item_group"))
                .icon(() -> new ItemStack(ModCustom.TREE_ITEM)).entries((displayContext, entries) -> {

                    entries.add(ModCustom.GLOBE_ITEM);
                    entries.add(ModCustom.CLOCK_ITEM);
                    entries.add(ModCustom.TREE_ITEM);

                    entries.add(CANDY_CANE_SWORD);
                    entries.add(CANDY_CANE_PICKAXE);
                    entries.add(CANDY_CANE_AXE);
                    entries.add(CANDY_CANE_SHOVEL);
                    entries.add(CANDY_CANE_HOE);
                    
                    entries.add(CANDY_CANE_ROD);
                    entries.add(CANDY_CANE);
                    entries.add(CANDY_CANE_INGOT);
                    entries.add(CANDY_CANE_NUGGET);
                    entries.add(CANDY_CANE_RED_WHITE_INGOT);
                    entries.add(CANDY_CANE_RED_WHITE_NUGGET);
                    entries.add(CANDY_CANE_RED_INGOT);
                    entries.add(CANDY_CANE_RED_NUGGET);
                    entries.add(CANDY_CANE_WHITE_INGOT);
                    entries.add(CANDY_CANE_WHITE_NUGGET);
                    entries.add(SUGAR_INGOT);
                    entries.add(SUGAR_NUGGET);

                    entries.add(CANDY_CANE_MULTITOOL);

                }).build());

    }
}
