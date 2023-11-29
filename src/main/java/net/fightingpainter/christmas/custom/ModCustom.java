package net.fightingpainter.christmas.custom;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fightingpainter.christmas.Main;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModCustom {


    //globe
    public static Block GLOBE_BLOCK;
    public static Item GLOBE_ITEM;
    public static BlockEntityType<Snowglobe_BlockEntity> GLOBE_BLOCK_ENTITY_TYPE;

    //clock
    public static Block CLOCK_BLOCK;
    public static Item CLOCK_ITEM;
    public static BlockEntityType<Clock_BlockEntity> CLOCK_BLOCK_ENTITY_TYPE;

    //christmas Tree
    public static Block TREE_BLOCK;
    public static Item TREE_ITEM;



    public static void register() {
        Main.LOGGER.info("Registering Custom Stuff");

        //globe
        GLOBE_BLOCK = Registry.register(
            Registries.BLOCK,
            new Identifier(Main.MOD_ID, "snowglobe"), 
            new Snowglobe_Block(FabricBlockSettings.copy(Blocks.GLASS).strength(10000f, 10000f).dropsLike(Blocks.COBBLESTONE).requiresTool().pistonBehavior(PistonBehavior.BLOCK))
        );

        GLOBE_ITEM = Registry.register(
            Registries.ITEM, 
            new Identifier(Main.MOD_ID, "snowglobe"), 
            new BlockItem(GLOBE_BLOCK, new FabricItemSettings())
        );
        
        GLOBE_BLOCK_ENTITY_TYPE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Main.MOD_ID, "globe_entity"),
            FabricBlockEntityTypeBuilder.create(Snowglobe_BlockEntity::new, GLOBE_BLOCK).build()
        );


        //clock
        CLOCK_BLOCK = Registry.register(
            Registries.BLOCK, 
            new Identifier(Main.MOD_ID, "clock_block"), 
            new Clock_Block(FabricBlockSettings.copy(Blocks.COBBLESTONE).strength(10000f, 10000f).requiresTool().pistonBehavior(PistonBehavior.BLOCK))
        );

        CLOCK_ITEM = Registry.register(
            Registries.ITEM, 
            new Identifier(Main.MOD_ID, "clock_block"), 
            new BlockItem(CLOCK_BLOCK, new FabricItemSettings())
        );

        CLOCK_BLOCK_ENTITY_TYPE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Main.MOD_ID, "clock_entity"),
            FabricBlockEntityTypeBuilder.create(Clock_BlockEntity::new, CLOCK_BLOCK).build()
        );


        //Christmas Tree
        TREE_BLOCK = Registry.register(
            Registries.BLOCK, 
            new Identifier(Main.MOD_ID, "christmas_tree"), 
            new ChristmasTree(FabricBlockSettings.copy(Blocks.OAK_SAPLING).strength(10000f, 10000f).pistonBehavior(PistonBehavior.BLOCK).nonOpaque().luminance((state) -> {return 15;}))
        );

        TREE_ITEM = Registry.register(
            Registries.ITEM, 
            new Identifier(Main.MOD_ID, "christmas_tree"), 
            new BlockItem(TREE_BLOCK, new FabricItemSettings())
        );


    }
}