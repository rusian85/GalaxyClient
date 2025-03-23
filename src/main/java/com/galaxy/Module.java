package com.galaxy;

import com.galaxy.settings.Setting;
import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    public final String name;
    public final Category category;
    public boolean toggle;
    public int keybind;
    public final List<Setting<?>> settings = new ArrayList<>();

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
        this.toggle = false;
        this.keybind = -1; // Без привязки по умолчанию
    }

    public void toggle() {
        this.toggle = !this.toggle;
        if (this.toggle) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onUpdate() {}
}