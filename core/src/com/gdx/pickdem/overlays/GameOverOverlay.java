package com.gdx.pickdem.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.pickdem.util.Constants;


public class GameOverOverlay {

    public final Viewport viewport;
    final BitmapFont font;
    Array<OverlayAnimation> skulls;
    long startTime;

    public GameOverOverlay() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font.getData().setScale(1);
    }

    public void init() {
        startTime = TimeUtils.nanoTime();

        skulls = new Array<OverlayAnimation>(Constants.ENEMY_COUNT);

        for (int i = 0; i < Constants.ENEMY_COUNT; i++) {
            Vector2 position = new Vector2(MathUtils.random(viewport.getWorldWidth()), MathUtils.random(viewport.getWorldHeight()));
            Skull skull = new Skull(position);
            skull.offset = MathUtils.random(Constants.LEVEL_END_DURATION);
            skulls.add(skull);
        }
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        for (OverlayAnimation skull : skulls){
            skull.render(batch);
        }
        batch.setColor(132, 132, 132, 0.2f);
        font.draw(batch, Constants.GAME_OVER_MESSAGE, viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0, Align.center, false);

        batch.end();

    }
}
