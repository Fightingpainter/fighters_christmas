package net.fightingpainter.christmas.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

public class Snowglobe_Block extends BlockWithEntity {
    public static final BooleanProperty SNOWING = BooleanProperty.of("snowing");

    public Snowglobe_Block(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(SNOWING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SNOWING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Snowglobe_BlockEntity(pos, state);

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (world != null && !world.isClient && world instanceof ServerWorld) { //check if correct world
            ServerWorld serverWorld = (ServerWorld) world; //cast to server world

            BlockEntity be = world.getBlockEntity(pos); //get the block entity

            if (be instanceof Snowglobe_BlockEntity) { //check if correct block entity

                Snowglobe_BlockEntity.onBlockInteracted((Snowglobe_BlockEntity) be, serverWorld, pos, state); //call the block entity method
            }
        }
        return ActionResult.SUCCESS; //return success
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient) {
            return checkType(type, ModCustom.GLOBE_BLOCK_ENTITY_TYPE, (w, pos, st, be) -> {
                if (be instanceof Snowglobe_BlockEntity) {
                    ((Snowglobe_BlockEntity) be).tick(w, pos, st);
                }
            });
        }
        return null;
    }

}
