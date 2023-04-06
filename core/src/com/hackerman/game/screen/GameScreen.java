package com.hackerman.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.hackerman.game.Main;
import com.hackerman.game.config.Resolution;
import com.hackerman.game.logic.Raindrop;

import java.util.Iterator;

public class GameScreen implements Screen {

    private final static Vector3 touchPos = new Vector3();
    final Main game;
    private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle bucket;
    private Raindrop raindrop;
    private int missCounter;

    public GameScreen(final Main game) {

        this.game = game;
        raindrop = new Raindrop();

        float bucketSize = 64;

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        bucket = new Rectangle();
        bucket.x = Resolution.getWidth() / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = bucketImage.getWidth();
        bucket.height = bucketImage.getHeight();

        raindrop.spawnRaindrop();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        game.font.draw(batch, "Misses: " + missCounter, 100, 400);
        batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop : raindrop.getRaindrops()) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        if (TimeUtils.nanoTime() - raindrop.getLastDropTime() > 1000000000) raindrop.spawnRaindrop();

        for (Iterator<Rectangle> iter = raindrop.getRaindrops().iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                iter.remove();
                missCounter++;
            }

            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
            }
        }

        if (missCounter >= 3) {
            game.setScreen(new FailureScreen(game));
            dispose();
        }

    }

    private void missCounter() {

    }

    @Override
    public void show() {
        rainMusic.setVolume(0.1f);
        rainMusic.play();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}
