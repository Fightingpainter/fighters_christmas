package net.fightingpainter.christmas.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fightingpainter.christmas.Main;

public class ModCommands {
    


    public static void register() {
        Main.LOGGER.info("Registering Commands");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> Calendar.register(dispatcher));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ModifyCalendar.register(dispatcher));
    }

}
