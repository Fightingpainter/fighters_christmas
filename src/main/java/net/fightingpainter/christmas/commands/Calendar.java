package net.fightingpainter.christmas.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fightingpainter.christmas.packets.client.ClientPacket;
import net.fightingpainter.christmas.packets.server.ServerPacketReceiver;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;


public class Calendar {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> calendarCommand = CommandManager.literal("calendar")
                .executes(ctx -> runGetDay(ctx, 0)) // for "/calendar" with no arguments
                .then(CommandManager.literal("list")
                    .executes(ctx -> runListAvailable(ctx)) // for "/calendar list" or "/calendar list available"
                    .then(CommandManager.literal("all")
                        .requires(source -> source.hasPermissionLevel(2)) // permission check
                        .executes(ctx -> runListAll(ctx))) // for "/calendar list all"
                    .then(CommandManager.literal("missed")
                        .executes(ctx -> runListMissed(ctx))) // for "/calendar list missed"
                    .then(CommandManager.literal("available")
                        .executes(ctx -> runListAvailable(ctx)))) // for "/calendar list available"
                .then(CommandManager.argument("day", IntegerArgumentType.integer(1, 24))
                    .executes(ctx -> runGetDay(ctx, IntegerArgumentType.getInteger(ctx, "day"))) // for "/calendar <day>"
                    .then(CommandManager.literal("unclaim")
                        .requires(source -> source.hasPermissionLevel(2)) // permission check
                        .executes(ctx -> runUnclaimDay(ctx, IntegerArgumentType.getInteger(ctx, "day"))))); // for "/calendar <day> unclaim"

        dispatcher.register(calendarCommand);
    }

    public static int runGetDay(CommandContext<ServerCommandSource> ctx, int day) {

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) { //if on client side send packet
            ClientPacket.send("get", day);
        } 
        else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) { //if on server side handle packet directly
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            ClientPacket packet = new ClientPacket("get", day);
            ServerPacketReceiver.handle(packet, player);
        }
        else { //else just send error
            ctx.getSource().sendError(Text.of("This command can only be run by a player."));
        }

        return 1;
    }

    public static int runListAll(CommandContext<ServerCommandSource> ctx) {
        
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) { //if on client side send packet
            ClientPacket.send("list_all", 0);
        }
        else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) { //if on server side handle packet directly
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            ClientPacket packet = new ClientPacket("list_all", 0);
            ServerPacketReceiver.handle(packet, player);
        }
        else { //else just send error
            ctx.getSource().sendError(Text.of("This command can only be run by a player."));
        }

        return 1;
    }

    public static int runListMissed(CommandContext<ServerCommandSource> ctx) {

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) { //if on client side send packet
            ClientPacket.send("list_missed", 0);
        }
        else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) { //if on server side handle packet directly
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            ClientPacket packet = new ClientPacket("list_missed", 0);
            ServerPacketReceiver.handle(packet, player);
        }
        else { //else just send error
            ctx.getSource().sendError(Text.of("This command can only be run by a player."));
        }

        return 1;
    }

    public static int runListAvailable(CommandContext<ServerCommandSource> ctx) {

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) { //if on client side send packet
            ClientPacket.send("list_available", 0);
        }
        else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) { //if on server side handle packet directly
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            ClientPacket packet = new ClientPacket("list_available", 0);
            ServerPacketReceiver.handle(packet, player);
        }
        else { //else just send error
            ctx.getSource().sendError(Text.of("This command can only be run by a player."));
        }

        return 1;
    }

    public static int runUnclaimDay(CommandContext<ServerCommandSource> ctx, int day) {

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) { //if on client side send packet
            ClientPacket.send("unclaim", day);
        }
        else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) { //if on server side handle packet directly
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            ClientPacket packet = new ClientPacket("unclaim", day);
            ServerPacketReceiver.handle(packet, player);
        }
        else { //else just send error
            ctx.getSource().sendError(Text.of("This command can only be run by a player."));
        }
        
        return 1;
    }
}

