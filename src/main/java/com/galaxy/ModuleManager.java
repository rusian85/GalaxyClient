package com.galaxy;

import com.galaxy.modules.combat.ExampleModule;
import com.galaxy.modules.combat.KillAura;
import com.galaxy.modules.misc.MiscExampleModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    private static ModuleManager instance;
    private final List<Module> modules = new ArrayList<>();

    private ModuleManager() {
        modules.add(new ExampleModule()); // Модуль для COMBAT
        modules.add(new KillAura()); // Модуль для COMBAT
        modules.add(new MiscExampleModule()); // Модуль для MISC
    }

    public static ModuleManager getInstance() {
        if (instance == null) {
            instance = new ModuleManager();
        }
        return instance;
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesByCategory(Category category) {
        return modules.stream()
                .filter(m -> m.category == category)
                .collect(Collectors.toList());
    }
}