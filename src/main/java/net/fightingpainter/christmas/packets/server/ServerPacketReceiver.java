package net.fightingpainter.christmas.packets.server;

import net.fightingpainter.christmas.Main;
import net.fightingpainter.christmas.CalenderDataHandler;
import net.fightingpainter.christmas.packets.client.*;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import java.util.ArrayList;
import java.util.List;

public class ServerPacketReceiver {
    
    // Packet registration
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(new Identifier(Main.MOD_ID, "client_action"), (server, player, handler, buf, responseSender) -> {
            ClientPacket packet = ClientPacket.decode(buf);
            server.execute(() -> handle(packet, player));
        });
    }

    
    //==========================================HANDLE==========================================

    // Method to handle the packet
    public static void handle(ClientPacket packet, ServerPlayerEntity player) {

        if (CalenderDataHandler.getDay() == 0) return; // If the day is 0, it's not December yet, we don't need it yet

        // the packet is a request for the data of a specific day
        if(packet.getAction().equals("get")) {

            int day = packet.getDay(); // Get the day from the packet
            if(day == 0) day = CalenderDataHandler.getDay(); // If the day is 0, get the current day
            
            if (day <= 24) { // If the day is between 1 and 24

                if (CalenderDataHandler.getDay() < day && !player.hasPermissionLevel(2)) {
                    player.sendMessageToClient(Text.of("Can't get future stuff"), false);
                    return;
                }; // If the day is in the future, we don't need it yet

                List<ItemStack> items = CalenderDataHandler.getItemsforDay(day); // Get the items for the day
                boolean collected = CalenderDataHandler.getPlayerDay(player.getUuidAsString(), day); // Get if the player has already collected the items for the day

                ServerPacket.send(player, day, collected, items); // Send the data to the player

            } else {
                ServerPacket.send(player, day, false, new ArrayList<>()); // Send an empty list if the day is not between 1 and 24 (this is to prevent errors because the client expects a list of items
            }
        }

        else if (packet.getAction().equals("collect")) {

            int day = packet.getDay();
            if(day == 0) day = CalenderDataHandler.getDay(); // If the day is 0, get the current day

            List<ItemStack> items = CalenderDataHandler.getItemsforDay(day);
            boolean collected = CalenderDataHandler.getPlayerDay(player.getUuidAsString(), day);

            if(!collected) {
                CalenderDataHandler.setPlayerDay(player.getUuidAsString(), day, true);

                World world = player.getServerWorld();

                for (ItemStack item : items) {

                    if (!player.getInventory().insertStack(item)) {
                        // If not enough space in Inventory, drop the item in the world
                        ItemEntity droppedItem = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), item);
                        world.spawnEntity(droppedItem);
                    }

                }
            }
        }
        else if (packet.getAction().split("_")[0].equals("list")) {

            MutableText message = Text.literal("");
            
            //list of days player didn't claim yet
            if (packet.getAction().equals("list_missed")) {

                List<Integer> days = CalenderDataHandler.getMissedDays(player.getUuidAsString());
    
                if (days.size() == 0) {
                    message = Text.literal("You didn't miss any days!").formatted(Formatting.BLUE);
                } else {
                    message = Text.literal("Missed days: ").formatted(Formatting.BLUE);
                
                    for (int day : days) {
                        // Create a clickable text for each day
                        MutableText dayText = Text.literal("[Day " + day+"]")
                            .formatted(Formatting.GOLD)
                            .styled(style -> style
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/calendar " + day))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to get items for Day " + day))));
                
                        message.append(dayText).append(" ");
                    }
                
                }
            
            }

            //list of days that are available (aka current day and before)
            else if (packet.getAction().equals("list_available")) {
    
                message = Text.literal("All days: ").formatted(Formatting.BLUE);

                //since get day can return 25 we need to make sure it doesn't go over 24
                int maxDay = CalenderDataHandler.getDay();
                if (maxDay > 24) maxDay = 24;

                //loop trough available days
                for (int day = 1; day <= maxDay; day++) {
                    
                    final int fixedDayVar = day;

                    // Create a clickable text for each day
                    MutableText dayText = Text.literal("[Day " + fixedDayVar+"]")
                        .formatted(Formatting.GOLD)
                        .styled(style -> style
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/calendar " + fixedDayVar))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to get items for Day " + fixedDayVar))));
            
                    message.append(dayText).append(" ");

                }
            
            }

            //list of all days (only with op because it allows oppening future days)
            else if (packet.getAction().equals("list_all") && player.hasPermissionLevel(2)) {
    
                message = Text.literal("All days: ").formatted(Formatting.BLUE);

                for (int day = 1; day <= 24; day++) {

                    final int fixedDayVar = day;
                    
                    // Create a clickable text for each day
                    MutableText dayText = Text.literal("[Day " + fixedDayVar+"]")
                        .formatted(Formatting.GOLD)
                        .styled(style -> style
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/calendar " + fixedDayVar))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to get items for Day " + fixedDayVar))));
            
                    message.append(dayText).append(" ");

                }
            
            }
            else { Main.LOGGER.error("Unknown list action: " + packet.getAction()); } // Error message if the action is unknown

            player.sendMessageToClient(message, false);

        }

        //unclaim a day
        else if (packet.getAction().equals("unclaim") && player.hasPermissionLevel(2)) {

            int day = packet.getDay();
            if(day == 0) day = CalenderDataHandler.getDay(); // If the day is 0, get the current day

            CalenderDataHandler.setPlayerDay(player.getUuidAsString(), day, false);

        }

        else { Main.LOGGER.error("Unknown action: " + packet.getAction()); } // Error message if the action is unknown

    }

}
