package com.galaxy.clickgui;

import com.galaxy.Module;
import com.galaxy.clickgui.item.Item;
import com.galaxy.clickgui.item.items.ItemBoolean;
import com.galaxy.clickgui.item.items.ItemInteger;
import com.galaxy.clickgui.item.items.ItemKeyBind;
import com.galaxy.settings.BoolSetting;
import com.galaxy.settings.IntSetting;
import com.galaxy.utils.MouseUtil;
import com.galaxy.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class ModuleButton {
    private final Module module;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final MinecraftClient mc;
    private int offset;
    private boolean open;
    private final List<Item<?>> items = new ArrayList<>();

    public ModuleButton(Module module, int x, int y, int width, int height, MinecraftClient mc) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = mc;

        module.settings.forEach(setting -> {
            if (setting instanceof BoolSetting boolSetting)
                items.add(new ItemBoolean(boolSetting, x, y, width, height));
            else if (setting instanceof IntSetting intSetting)
                items.add(new ItemInteger(intSetting, x, y, width, height));
        });

        items.add(new ItemKeyBind(module, x, y, width, height));
    }

    public int render(DrawContext context, int mouseX, int mouseY, float delta, int offset) {
        this.offset = offset;
        int y = this.y + offset;
        RenderUtil.rect(context, x, y, width, height, 0x80000000);
        context.drawTextWithShadow(mc.textRenderer, module.name,
                x + 3, (int) (y + (height / 2f - mc.textRenderer.fontHeight / 2f)),
                module.toggle ? 0xFF2B71F3 : -1);

        int offsets = height;
        if (open) {
            for (Item<?> item : items) {
                offsets += item.drawScreen(context, mouseX, mouseY, delta, offsets + offset);
            }
        }
        return offsets;
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (bounding(mouseX, mouseY)) {
            if (button == 0) module.toggle();
            else if (button == 1) this.open = !this.open;
        }
        if (open) items.forEach(item -> item.mouseClicked(mouseX, mouseY, button));
    }

    public void mouseReleased(double mouseX, double mouseY, int state) {
        if (open) items.forEach(item -> item.mouseReleased(mouseX, mouseY, state));
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (open) items.forEach(item -> item.keyPressed(keyCode, scanCode, modifiers));
    }

    public void charTyped(char chr, int modifiers) {
        if (open) items.forEach(item -> item.charTyped(chr, modifiers));
    }

    public boolean bounding(double mouseX, double mouseY) {
        return MouseUtil.bounding(mouseX, mouseY, x, y + offset, width, height);
    }
}