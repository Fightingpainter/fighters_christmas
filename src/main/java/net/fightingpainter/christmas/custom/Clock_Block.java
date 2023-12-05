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

public class Clock_Block extends BlockWithEntity {
    public static final BooleanProperty NIGHT = BooleanProperty.of("is_night");

    public Clock_Block(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(NIGHT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NIGHT);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Clock_BlockEntity(pos, state);

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (world != null && !world.isClient && world instanceof ServerWorld) { //check if correct world
            ServerWorld serverWorld = (ServerWorld) world; //cast to server world

            BlockEntity be = world.getBlockEntity(pos); //get the block entity

            if (be instanceof Clock_BlockEntity) { //check if correct block entity

                Clock_BlockEntity.onBlockInteracted((Clock_BlockEntity) be, serverWorld, pos, state); //call the block entity method
            }
        }
        return ActionResult.SUCCESS; //return success
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient) {
            return checkType(type, ModCustom.CLOCK_BLOCK_ENTITY_TYPE, (w, pos, st, be) -> {
                if (be instanceof Clock_BlockEntity) {
                    ((Clock_BlockEntity) be).tick(w, pos, st);
                }
            });
        }
        return null;
    }

}
