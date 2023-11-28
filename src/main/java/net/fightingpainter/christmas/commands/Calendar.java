package net.fightingpainter.christmas.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.fightingpainter.christmas.packets.client.ClientPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
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
        // Code to get a specific advent calendar day
        ClientPacket.send("get", day);
        return 1;
    }

    public static int runListAll(CommandContext<ServerCommandSource> ctx) {
        // Code to list all days
        ClientPacket.send("list_all", 0);
        return 1;
    }

    public static int runListMissed(CommandContext<ServerCommandSource> ctx) {
        // Code to list missed days
        ClientPacket.send("list_missed", 0);
        return 1;
    }

    public static int runListAvailable(CommandContext<ServerCommandSource> ctx) {
        // Code to list available days
        ClientPacket.send("list_available", 0);
        return 1;
    }

    public static int runUnclaimDay(CommandContext<ServerCommandSource> ctx, int day) {
        // Code to unclaim a specific day
        ClientPacket.send("unclaim", day);
        return 1;
    }
}

