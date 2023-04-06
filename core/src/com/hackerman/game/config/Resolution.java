package com.hackerman.game.config;

public class Resolution {
    public static int width, height;
    private static Resolution INSTANCE;

    private Resolution() {
    }

    public static Resolution getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Resolution();
        }
        return INSTANCE;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Resolution.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Resolution.height = height;
    }
}
