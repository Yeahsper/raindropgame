package com.hackerman.game.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Raindrop {

    private Array<Rectangle> raindrops = new Array<Rectangle>();
    private long lastDropTime;

    public void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    public Array<Rectangle> getRaindrops() {
        return raindrops;
    }

    public long getLastDropTime() {
        return lastDropTime;
    }
}
