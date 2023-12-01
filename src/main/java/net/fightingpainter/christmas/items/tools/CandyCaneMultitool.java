package net.fightingpainter.christmas.items.tools;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class CandyCaneMultitool extends PickaxeItem {

    ToolMaterial material;

    //Constructor
    public CandyCaneMultitool(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.material = material;
    }

    //This Method handles if the tool is suitable to break a block
    @Override
    public boolean isSuitableFor(BlockState state) {
        return true;
    }
    
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return this.material.getMiningSpeedMultiplier();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Right click to path"));
        tooltip.add(Text.of("Sneak + Right click to till soil"));
        tooltip.add(Text.of("Sneak + Right click to strip logs"));
    }

    //This Method handles the Ineraction with a Block
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        if (!world.isClient) {

            //if targeted block is "pathable" make it to path or farmland (hoe and shovel right click)
            if (world.getBlockState(blockPos).isIn(BlockTags.DIRT) && world.getBlockState(blockPos.up()).isAir()) {

                if (player.isSneaking()) {
                    world.setBlockState(blockPos, Blocks.FARMLAND.getDefaultState());
                    world.playSound(null, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1f, 1f);
                    return ActionResult.SUCCESS;
                }
                else {
                    world.setBlockState(blockPos, Blocks.DIRT_PATH.getDefaultState());
                    world.playSound(null, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1f, 1f);
                    return ActionResult.SUCCESS;
                }
            }
            //if targeted block is stripplable make it to stripped log (axe right click)
            else if (world.getBlockState(blockPos).isIn(BlockTags.LOGS) && player.isSneaking()) {
                String blockID = new ItemStack(world.getBlockState(blockPos).getBlock().asItem()).getRegistryEntry().getKey().get().getValue().getPath().toString(); //get block ID
                Block strippedBlock = Registries.BLOCK.get(new Identifier("stripped_" + blockID)); //get stripped block
                if (strippedBlock == null || strippedBlock == Blocks.AIR) return ActionResult.FAIL; //if stripped block doesn't exist, do nothing
                
                world.setBlockState(blockPos, strippedBlock.getDefaultState()); //set block to stripped block
                world.playSound(null, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1f, 1f); //play sound
            
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
