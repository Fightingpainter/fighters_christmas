package net.fightingpainter.christmas.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChristmasTree extends Block{

    public ChristmasTree(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.125f, 0.0f, 0.125f, 0.875f, 0.8f, 0.875f);
    }


    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
    
        if (random.nextInt(10) == 0) { // 10% chance each tick to spawn a particle
            double x = pos.getX() + random.nextDouble(); // Random position within block bounds
            double y = pos.getY() + 0.1; // Slightly above the ground level
            double z = pos.getZ() + random.nextDouble(); 
    
            // Particle moving upwards
            double motionY = 0.05 + random.nextDouble() * 0.05; // Slightly random upward motion
    
            // Spawn particle
            world.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, 0, motionY, 0);
        }
    }
    
}
