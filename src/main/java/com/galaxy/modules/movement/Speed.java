package com.galaxy.modules.movement;

import com.galaxy.Category;
import com.galaxy.Module;
import com.galaxy.settings.IntSetting;
import net.minecraft.client.MinecraftClient;

public class Speed extends Module {
    private final IntSetting speed;

    public Speed() {
        super("Speed", Category.MOVEMENT);
        this.speed = new IntSetting("Speed", 2, 1, 5);
        this.settings.add(speed);
    }

    @Override
    public void onUpdate() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        float speedFactor = speed.getValue() * 0.1f;
        mc.player.setMovementSpeed(mc.player.getMovementSpeed() + speedFactor);
    }
}