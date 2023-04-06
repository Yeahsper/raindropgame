package com.hackerman.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.hackerman.game.config.Resolution;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        Resolution.setWidth(800);
        Resolution.setHeight(480);

        config.setForegroundFPS(300);
        config.setWindowedMode(Resolution.width, Resolution.height);
        config.useVsync(true);
        config.setResizable(false);
        config.setTitle("Hackerman");
        new Lwjgl3Application(new Main(), config);
    }
}
