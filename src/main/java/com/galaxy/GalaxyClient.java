package com.galaxy;

import com.galaxy.clickgui.ClickGuiMain;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class GalaxyClient implements ModInitializer {
    public static final String MOD_ID = "galaxyclient";
    private static KeyBinding openClickGuiKey;

    @Override
    public void onInitialize() {
        openClickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.galaxyclient.open_clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.galaxyclient"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openClickGuiKey.wasPressed()) {
                client.setScreen(new ClickGuiMain());
            }
        });
    }
}