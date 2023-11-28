package net.fightingpainter.christmas.custom;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fightingpainter.christmas.Main;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
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


    public static void register() {
        Main.LOGGER.info("Registering Custom Stuff");

        //globe
        GLOBE_BLOCK = Registry.register(
            Registries.BLOCK, 
            new Identifier(Main.MOD_ID, "snowglobe"), 
            new Snowglobe_Block(FabricBlockSettings.copy(Blocks.STONE).nonOpaque())
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



    }
}