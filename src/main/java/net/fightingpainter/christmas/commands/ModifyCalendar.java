package net.fightingpainter.christmas.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import java.util.List;
import java.util.ArrayList;

import net.fightingpainter.christmas.CalenderDataHandler;
import net.fightingpainter.christmas.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ModifyCalendar {
    
    // "/modify-calendar <day>"

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){ 
        dispatcher.register(CommandManager.literal("modify-calendar").requires(source -> source.hasPermissionLevel(2))
        .then(CommandManager.argument("day", IntegerArgumentType.integer(1, 24))
        .executes(context -> run(IntegerArgumentType.getInteger(context, "day"), context.getSource()))));

    }
 
    public static int run(int day, ServerCommandSource source) {

        ServerPlayerEntity player = source.getPlayer(); //get player

        List<ItemStack> items = new ArrayList<>(); //create array for items
        
        //get items from hotbar
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = inventory.getStack(i); // Get the item in the slot

            //if not empty (air) add to array
            if(!itemStack.isEmpty()){
                items.add(itemStack);
            }
        }

        if(items.size() > 0){
            //if recieved items add them to the calendar
            Main.LOGGER.info("setting items for day "+day+" to "+items.toString());
            CalenderDataHandler.setItemsforDay(day, items);
            return 1;
        }
        else {
            //send a private Message to player
            player.sendMessageToClient(Text.of("No Items in Hotbar Found Please add the Items to your Hotbar you want to set the rewards of Day "+day+" to"), false);
            return 1;
        }

    }
}