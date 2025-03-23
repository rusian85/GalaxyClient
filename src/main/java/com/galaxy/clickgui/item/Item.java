package com.galaxy.clickgui.item;

import com.galaxy.utils.MouseUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public abstract class Item<T> {
    protected final T object;
    public final int x, y, width, height;
    public int offset;
    protected final MinecraftClient mc = MinecraftClient.getInstance();

    public Item(T object, int x, int y, int width, int height) {
        this.object = object;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int drawScreen(DrawContext context, int mouseX, int mouseY, float delta, int offset) { return 0; }
    public void mouseClicked(double mouseX, double mouseY, int button) {}
    public void mouseReleased(double mouseX, double mouseY, int state) {}
    public void keyPressed(int keyCode, int scanCode, int modifiers) {}
    public void charTyped(char chr, int modifiers) {}

    public boolean bounding(double mouseX, double mouseY) {
        return MouseUtil.bounding(mouseX, mouseY, x, y + offset, width, height);
    }

    protected T getObject() { return object; }
}