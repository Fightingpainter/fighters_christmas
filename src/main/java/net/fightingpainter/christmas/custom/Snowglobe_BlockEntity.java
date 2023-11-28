package net.fightingpainter.christmas.custom;

import net.fightingpainter.christmas.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Snowglobe_BlockEntity extends BlockEntity{

    //Properties
    //====================================================================

    private boolean ignoreWeather = false; //this property is used to store the snowing state of the block entity (it's just used to make sure no wierd stuff is happening)
    private boolean isSnowing; //this property is used to store the snowing state of the block entity (it's just used to make sure no wierd stuff is happening)

    //Boring Stuff
    //====================================================================

    // Constructor
    public Snowglobe_BlockEntity(BlockPos pos, BlockState state) {
        super(ModCustom.GLOBE_BLOCK_ENTITY_TYPE, pos, state);
    }

    
    //Cool Stuff
    //====================================================================
    
    // This method is called every tick
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world != null && world.getTime() % 100 == 0) {//Every 100 ticks / 5 seconds

            boolean isRaining = world.isRaining(); //check if raining

            if (this.isIgnoreWeather()) { //check if ignore weather is true

                if (this.isSnowing() != isRaining) { //check if the snowing property is different from the isRaining property
                    //if it's different that means that wheather has not been updated by the block entity yet. so we need to wait.
                    return; //return
                } else {
                    //if it's the same then that means that the weather finally updated to the correct state so we can set ignore weather to false
                    this.setIgnoreWeather(false); //set ignore weather to false
                    return; //return
                }

            }else{
                world.setBlockState(pos, state.with(Snowglobe_Block.SNOWING, isRaining)); //set the block state
            }
 
        }
    }
    
    // This method is called when the block is interacted with
    public static void onBlockInteracted(Snowglobe_BlockEntity blockEntity, ServerWorld world, BlockPos pos, BlockState state) {
        
        boolean isSnowing; //isSnowing bool

        //update the block state
        if (blockEntity.isIgnoreWeather()) {
            //if ignore weather is true then check the isSnowing property
            isSnowing = blockEntity.isSnowing(); //get the isSnowing property

        } else {
            //update state
            isSnowing = world.isRaining(); //check if raining
            world.setBlockState(pos, state.with(Snowglobe_Block.SNOWING, isSnowing)); //set the block state
            blockEntity.setSnowing(isSnowing);

        }

        if (isSnowing) {

            //if it is snowing, make the weather clear
            Main.LOGGER.info("no more snow :("); //log
            
            world.setWeather(0, 0, false, false);

            world.setBlockState(pos, state.with(Snowglobe_Block.SNOWING, false)); //set the block state
            blockEntity.setSnowing(false); //set Snowing property


        } else {

            //if it is not snowing, make the weather snow (rain)
            Main.LOGGER.info("Let it Snow!"); //log

            long currentTime = world.getTimeOfDay(); //get the current time
            long timeUntilNextDay = 24000 - (currentTime % 24000); //calculate time until next day
            world.setWeather(0, (int) timeUntilNextDay, true, false); //set weather
            
            world.setBlockState(pos, state.with(Snowglobe_Block.SNOWING, true)); //set block state
            blockEntity.setSnowing(true); //set Snowing property
        }

        blockEntity.setIgnoreWeather(true); //set ignore weather to true since we just updated the weather

    }

    //Getters and Setters
    //====================================================================

    //ignoreWeather
    public boolean isIgnoreWeather() {
        return ignoreWeather;
    }

    public void setIgnoreWeather(boolean ignoreWeather) {
        this.ignoreWeather = ignoreWeather;
    }

    //isSnowing
    public boolean isSnowing() {
        return isSnowing;
    }

    public void setSnowing(boolean isSnowing) {
        this.isSnowing = isSnowing;
    }
}