package com.galaxy.modules.combat;

import com.galaxy.Category;
import com.galaxy.Module;
import com.galaxy.settings.BoolSetting;
import com.galaxy.settings.IntSetting;

public class ExampleModule extends Module {
    public ExampleModule() {
        super("Example", Category.COMBAT);
        settings.add(new BoolSetting("Enabled", true));
        settings.add(new IntSetting("Range", 5, 1, 10));
    }

    @Override
    public void onEnable() {
        // Логика при включении модуля
    }

    @Override
    public void onDisable() {
        // Логика при выключении модуля
    }
}