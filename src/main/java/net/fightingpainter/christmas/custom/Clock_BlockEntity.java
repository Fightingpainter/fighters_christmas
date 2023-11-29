package net.fightingpainter.christmas.custom;

import net.fightingpainter.christmas.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Clock_BlockEntity extends BlockEntity{

    //Properties
    //====================================================================

    private boolean ignoreIngameTime = false; //this property is used to make sure that the block entity doesn't update the block state when the block is interacted with
    private boolean isNightTime; //this property is used to store the state of the block entity (it's just used to make sure no wierd stuff is happening)

    //Boring Stuff
    //====================================================================

    // Constructor
    public Clock_BlockEntity(BlockPos pos, BlockState state) {
        super(ModCustom.CLOCK_BLOCK_ENTITY_TYPE, pos, state);
    }

    
    //Cool Stuff
    //====================================================================
    
    // This method is called every tick
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world != null && world.getTime() % 100 == 0) {//Every 100 ticks / 5 seconds

            boolean nightTime = world.isNight(); //check if night time

            if (this.ignoreIngameTime()) { //check if ignore Ingame Time is true

                if (this.isNightTime() != nightTime) { //check if night time is different from the block entity property
                    //if it's different then that means that the time is still updating ? 
                    return; //return
                } else {
                    //if it's the same then that means that the time is done updating
                    this.setIgnoreIngameTime(false); //set ignoreingame time to false
                    return; //return
                }

            }else{
                world.setBlockState(pos, state.with(Clock_Block.NIGHT, nightTime)); //set the block state
            }
 
        }
    }
    
    // This method is called when the block is interacted with
    public static void onBlockInteracted(Clock_BlockEntity blockEntity, ServerWorld world, BlockPos pos, BlockState state) {
        
        boolean nightTime; //nightTime bool

        //update the block state
        if (blockEntity.ignoreIngameTime()) {
            // if ignore night time is true then we need to get the isNightTime property from the block entity
            nightTime = blockEntity.isNightTime(); //get the isNightTime property

        } else {
            //update state
            nightTime = world.isNight(); //check if night time
            world.setBlockState(pos, state.with(Clock_Block.NIGHT, nightTime)); //set the block state
            blockEntity.setNightTime(nightTime);

        }

        if (nightTime) {

            //if it's night time, make it day time
            Main.LOGGER.info("wakey wakey sleepy head!"); //log
            
            world.setTimeOfDay(0); //set time to 0

            world.setBlockState(pos, state.with(Clock_Block.NIGHT, false)); //set the block state
            blockEntity.setNightTime(false); //set Night Time property


        } else {

            //if it's day time, make it night time
            Main.LOGGER.info("good night sweet prince"); //log

            world.setTimeOfDay(13000); //set time to 0

            world.setBlockState(pos, state.with(Clock_Block.NIGHT, true)); //set block state
            blockEntity.setNightTime(true); //set Snowing property
        }

        blockEntity.setIgnoreIngameTime(true); //set ignoreingame time to true 

    }

    //Getters and Setters
    //====================================================================

    //ignoreWeather
    public boolean ignoreIngameTime() {
        return ignoreIngameTime;
    }

    public void setIgnoreIngameTime(boolean ignoreIngameTime) {
        this.ignoreIngameTime = ignoreIngameTime;
    }

    //isNightTime
    public boolean isNightTime() {
        return isNightTime;
    }

    public void setNightTime(boolean isNightTime) {
        this.isNightTime = isNightTime;
    }
}