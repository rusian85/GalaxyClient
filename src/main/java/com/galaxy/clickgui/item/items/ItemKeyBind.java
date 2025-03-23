package com.galaxy.clickgui.item.items;

import com.galaxy.Module;
import com.galaxy.clickgui.item.Item;
import com.galaxy.utils.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ItemKeyBind extends Item<Module> {
    private boolean pendingKey;

    public ItemKeyBind(Module module, int x, int y, int width, int height) {
        super(module, x, y, width, height);
    }

    @Override
    public int drawScreen(DrawContext context, int mouseX, int mouseY, float delta, int offset) {
        this.offset = offset;
        float y = this.y + offset;

        RenderUtil.rect(context, x, y, width, height, 0x80000000);
        String keyName = getObject().keybind == -1 ? "None" : InputUtil.fromKeyCode(getObject().keybind, -1).getLocalizedText().getString();
        context.drawTextWithShadow(mc.textRenderer,
                pendingKey ? "Press a key..." : "Bind [" + keyName + "]",
                x + 5, (int) (y + height / 2f - mc.textRenderer.fontHeight / 2f), -1);

        return height;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (bounding(mouseX, mouseY)) {
            if (button == 0) pendingKey = !pendingKey;
        } else {
            pendingKey = false;
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (pendingKey) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE) {
                getObject().keybind = -1;
            } else {
                getObject().keybind = keyCode;
            }
            pendingKey = false;
        }
    }
}