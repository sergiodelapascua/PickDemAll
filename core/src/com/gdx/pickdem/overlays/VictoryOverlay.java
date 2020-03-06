package com.gdx.pickdem.overlays;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.pickdem.util.Constants;

public class VictoryOverlay {

    public final static String TAG = VictoryOverlay.class.getName();
    public final Viewport viewport;
    final BitmapFont font;
    Array<OverlayAnimation> explosions;

    public VictoryOverlay() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/bebas-neue.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 44;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter); // font size 34 pixels
        generator.dispose();
        font.getData().setScale(1);
    }

    public void init() {
        explosions = new Array<OverlayAnimation>(Constants.OVERLAY_COUNT);
        for (int i = 0; i < Constants.OVERLAY_COUNT; i++) {
            int random = (int) (Math.random()*4)+1;
            OverlayAnimation overlayAnimation = generateFireWork(random);
            overlayAnimation.offset = MathUtils.random(Constants.LEVEL_END_DURATION);

            explosions.add(overlayAnimation);
        }
        MenuScreen.sound.dispose();
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/win.ogg"));
        sound.play(0.5f);
    }

    private OverlayAnimation generateFireWork(int option){
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
        for (OverlayAnimation overlayAnimation : explosions){
            overlayAnimation.render(batch);
        }
        font.draw(batch, Constants.VICTORY_MESSAGE, viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 +20, 0, Align.center, false);

        batch.end();
    }
}
