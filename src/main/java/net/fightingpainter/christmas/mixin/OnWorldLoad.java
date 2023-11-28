package net.fightingpainter.christmas.mixin;

import net.fightingpainter.christmas.CalenderDataHandler;
import net.minecraft.server.MinecraftServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class OnWorldLoad {
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {

		// This code is injected into the start of MinecraftServer.loadWorld()
		MinecraftServer server = (MinecraftServer) (Object) this;
		CalenderDataHandler.loadFiles(server); //load files
    }

}