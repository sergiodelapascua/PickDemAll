package com.gdx.pickdem.overlays;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.pickdem.environment.BlueFirework;
import com.gdx.pickdem.environment.Explosion;
import com.gdx.pickdem.environment.RedFirework;
import com.gdx.pickdem.environment.VioletFirework;
import com.gdx.pickdem.environment.YellowFirework;
import com.gdx.pickdem.util.Constants;

public class VictoryOverlay {

    public final static String TAG = VictoryOverlay.class.getName();
    public final Viewport viewport;
    final BitmapFont font;
    Array<Explosion> explosions;

    public VictoryOverlay() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        //font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font = new BitmapFont();
        //font.getData().setScale(1);
    }

    public void init() {
        explosions = new Array<Explosion>(Constants.EXPLOSION_COUNT);
        for (int i = 0; i < Constants.EXPLOSION_COUNT; i++) {
            int random = (int) (Math.random()*4)+1;
            Explosion explosion = generateFireWork(random);
            explosion.offset = MathUtils.random(Constants.LEVEL_END_DURATION);

            explosions.add(explosion);
        }
    }

    private Explosion generateFireWork(int option){
        switch (option){
            case 1:
                return new RedFirework(new Vector2(MathUtils.random(viewport.getWorldWidth()), MathUtils.random(viewport.getWorldHeight())));
            case 2:
                return new BlueFirework(new Vector2(MathUtils.random(viewport.getWorldWidth()), MathUtils.random(viewport.getWorldHeight())));
            case 3:
                return new VioletFirework(new Vector2(MathUtils.random(viewport.getWorldWidth()), MathUtils.random(viewport.getWorldHeight())));
            case 4:
                return new YellowFirework(new Vector2(MathUtils.random(viewport.getWorldWidth()), MathUtils.random(viewport.getWorldHeight())));
        }
        return null;
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        for (Explosion explosion : explosions){
            explosion.render(batch);
        }
        font.draw(batch, Constants.VICTORY_MESSAGE, viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2.f, 0, Align.center, false);

        batch.end();
    }
}
