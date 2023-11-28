package net.fightingpainter.christmas.other;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fightingpainter.christmas.packets.client.ClientPacket;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;


import org.lwjgl.glfw.GLFW;

public class KeyHandler {

    public static final String KEY_CATEGORY = "key.category.fighters_christmas.christmas";
    public static final String KEY_CALENDAR = "key.fighters_christmas.calendar";

    public static KeyBinding calendar_key;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(calendar_key.wasPressed()) {
                ClientPacket.send("get", 0);
            }
        });
    }

    public static void register() {
        calendar_key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_CALENDAR,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}