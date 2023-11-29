package net.fightingpainter.christmas;

import net.fightingpainter.christmas.blocks.ModBlocks;
import net.fightingpainter.christmas.commands.ModCommands;
import net.fightingpainter.christmas.custom.ModCustom;
import net.fightingpainter.christmas.items.ModItems;
import net.fightingpainter.christmas.packets.server.ServerPacketReceiver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {

	public static final String MOD_ID = "fighters_christmas"; //mod id
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID); //init the logger

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Fighters Christmas Mod | Merry Christmas!");

		ModCommands.register(); //register commands
		ModItems.register(); //register items
		ModBlocks.register(); //register blocks
		ModCustom.register(); //register custom stuff

		ServerPacketReceiver.register(); //register server packet receiver
	}
}