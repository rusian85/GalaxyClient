package com.galaxy.modules.misc;

import com.galaxy.Category;
import com.galaxy.Module;
import com.galaxy.settings.BoolSetting;

public class MiscExampleModule extends Module {
    public MiscExampleModule() {
        super("MiscTest", Category.MISC);
        settings.add(new BoolSetting("TestOption", false));
    }

    @Override
    public void onEnable() {}
    @Override
    public void onDisable() {}
}