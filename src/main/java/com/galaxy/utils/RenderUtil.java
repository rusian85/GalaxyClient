package com.galaxy.utils;

import net.minecraft.client.gui.DrawContext;

/**
 * Утилитный класс для отрисовки фигур в графическом интерфейсе.
 */
public class RenderUtil {
    /**
     * Отрисовывает прямоугольник на экране с использованием предоставленного DrawContext.
     *
     * @param context DrawContext, используемый для рендеринга.
     * @param x       Координата x верхнего левого угла прямоугольника.
     * @param y       Координата y верхнего левого угла прямоугольника.
     * @param width   Ширина прямоугольника.
     * @param height  Высота прямоугольника.
     * @param color   Цвет прямоугольника в формате ARGB (например, 0xFF2B71F3 для синего).
     */
    public static void rect(DrawContext context, float x, float y, float width, float height, int color) {
        context.fill((int) x, (int) y, (int) (x + width), (int) (y + height), color);
    }
}