package com.galaxy.clickgui.item.items;

import com.galaxy.clickgui.item.Item;
import com.galaxy.settings.IntSetting;
import com.galaxy.utils.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

public class ItemInteger extends Item<IntSetting> {
    private boolean isSlide;

    public ItemInteger(IntSetting intSetting, int x, int y, int width, int height) {
        super(intSetting, x, y, width, height);
    }

    @Override
    public int drawScreen(DrawContext context, int mouseX, int mouseY, float delta, int offset) {
        if (!getObject().isVisible()) return 0;

        this.offset = offset;
        float y = this.y + offset;

        RenderUtil.rect(context, x, y, width, height, 0x80000000);
        float drawWidth = (float) (getObject().getValue() - getObject().getMin()) / (getObject().getMax() - getObject().getMin());
        RenderUtil.rect(context, x, y, width * drawWidth, height, 0xFF2B71F3);
        String text = getObject().getName() + " " + getObject().getValue();
        context.drawTextWithShadow(mc.textRenderer, text, x + 5, (int) (y + height / 2F - mc.textRenderer.fontHeight / 2F), -1);

        if (this.isSlide) {
            float value = ((float) mouseX - x) / width;
            value = MathHelper.clamp(value, 0.0F, 1.0F);
            int newValue = (int) (getObject().getMin() + (getObject().getMax() - getObject().getMin()) * value);
            getObject().setValue(newValue);
        }

        return height;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!getObject().isVisible()) return;
        if (bounding(mouseX, mouseY)) {
            this.isSlide = true;
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int state) {
        this.isSlide = false;
    }
}