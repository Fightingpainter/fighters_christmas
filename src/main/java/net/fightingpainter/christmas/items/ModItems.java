package net.fightingpainter.christmas.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fightingpainter.christmas.Main;
import net.fightingpainter.christmas.custom.ModCustom;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {


    public static final ItemGroup MOD_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
        new Identifier(Main.MOD_ID, "item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup."+Main.MOD_ID+".item_group"))
                .icon(() -> new ItemStack(ModCustom.GLOBE_ITEM)).build());


    public static void register() {
        Main.LOGGER.info("Registering Items");

    }
}
