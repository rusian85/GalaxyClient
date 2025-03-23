package com.galaxy.settings;

public class IntSetting extends Setting<Integer> {
    private final int min;
    private final int max;

    public IntSetting(String name, Integer defaultValue, int min, int max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    public int getMin() { return min; }
    public int getMax() { return max; }
}