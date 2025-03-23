package com.galaxy.clickgui.item.items;

import com.galaxy.clickgui.item.Item;
import com.galaxy.settings.BoolSetting;
import com.galaxy.utils.RenderUtil;
import net.minecraft.client.gui.DrawContext;

public class ItemBoolean extends Item<BoolSetting> {
    public ItemBoolean(BoolSetting boolSetting, int x, int y, int width, int height) {
        super(boolSetting, x, y, width, height);
    }

    @Override
    public int drawScreen(DrawContext context, int mouseX, int mouseY, float delta, int offset) {
        if (!getObject().isVisible()) return 0;
        this.offset = offset;
        float y = this.y + offset;

        RenderUtil.rect(context, x, y, width, height, 0x80000000);
        context.drawTextWithShadow(mc.textRenderer, getObject().getName(),
                x + 5, (int) (y + height / 2f - mc.textRenderer.fontHeight / 2f),
                getObject().getValue() ? 0xFF2B71F3 : -1);
        return height;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (!getObject().isVisible()) return;
        if (bounding(mouseX, mouseY) && button == 0) {
            getObject().setValue(!getObject().getValue());
        }
    }
}