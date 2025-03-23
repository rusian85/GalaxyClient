package com.galaxy.clickgui;

import com.galaxy.Category;
import com.galaxy.Module;
import com.galaxy.ModuleManager;
import com.galaxy.utils.MouseUtil;
import com.galaxy.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class CategoryPanel {
    private final Category category;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final MinecraftClient mc;
    private final List<ModuleButton> moduleButtons = new ArrayList<>();
    private boolean open = true;

    public CategoryPanel(Category category, int x, int y, int width, int height, MinecraftClient mc) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = mc;

        for (Module module : ModuleManager.getInstance().getModules()) {
            if (module.category == this.category) {
                moduleButtons.add(new ModuleButton(module, x, y, width, height, mc));
            }
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Отрисовка заголовка категории
        RenderUtil.rect(context, x, y, width, height, 0xFF2B71F3);
        context.drawTextWithShadow(mc.textRenderer, category.name(),
                (int) (x + width / 2f - mc.textRenderer.getWidth(category.name()) / 2f),
                (int) (y + height / 2f - mc.textRenderer.fontHeight / 2f), -1);

        // Отрисовка модулей, если панель открыта
        if (this.open) {
            int offset = height;
            for (ModuleButton moduleButton : this.moduleButtons) {
                offset += moduleButton.render(context, mouseX, mouseY, delta, offset);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (bounding(mouseX, mouseY) && button == 1) this.open = !this.open;
        if (this.open) moduleButtons.forEach(mb -> mb.mouseClicked(mouseX, mouseY, button));
    }

    public void mouseReleased(double mouseX, double mouseY, int state) {
        if (this.open) moduleButtons.forEach(mb -> mb.mouseReleased(mouseX, mouseY, state));
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.open) moduleButtons.forEach(mb -> mb.keyPressed(keyCode, scanCode, modifiers));
    }

    public void charTyped(char chr, int modifiers) {
        if (this.open) moduleButtons.forEach(mb -> mb.charTyped(chr, modifiers));
    }

    public boolean bounding(double mouseX, double mouseY) {
        return MouseUtil.bounding(mouseX, mouseY, this.x, this.y, this.width, this.height);
    }
}