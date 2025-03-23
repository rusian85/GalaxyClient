package com.galaxy.clickgui;

import com.galaxy.Category;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiMain extends Screen {
    private final List<CategoryPanel> panels = new ArrayList<>();

    public ClickGuiMain() {
        super(Text.of("GalaxyClient ClickGUI"));
    }

    @Override
    public void init() {
        if (this.panels.isEmpty()) {
            int x = 10;
            for (Category category : Category.values()) {
                panels.add(new CategoryPanel(category, x, 10, 100, 15, MinecraftClient.getInstance()));
                x += 110;
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.panels.forEach(panel -> panel.render(context, mouseX, mouseY, delta));
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.panels.forEach(panel -> panel.mouseClicked(mouseX, mouseY, button));
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        this.panels.forEach(panel -> panel.mouseReleased(mouseX, mouseY, state));
        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.panels.forEach(panel -> panel.keyPressed(keyCode, scanCode, modifiers));
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        this.panels.forEach(panel -> panel.charTyped(chr, modifiers));
        return super.charTyped(chr, modifiers);
    }
}