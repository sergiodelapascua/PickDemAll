package com.gdx.pickdem.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gdx.pickdem.PickdemGame;
import com.gdx.pickdem.entity.Platform;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Constants;


public class MenuScreen extends InputAdapter implements Screen {

    public static final String TAG = MenuScreen.class.getName();

    PickdemGame game;

    private SpriteBatch batch;
    private ExtendViewport viewport;
    private BitmapFont font;
    private Platform p;

    public MenuScreen(PickdemGame game) {
        batch = new SpriteBatch();
        this.game = game;
    }

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        AssetManager am1 = new AssetManager();
        AssetManager am2 = new AssetManager();
        AssetManager am3 = new AssetManager();
        AssetManager am4 = new AssetManager();
        Assets.instance.init(am, am1, am2, am3, am4);

        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        /*this.p = new Platform(viewport.getWorldWidth()/2 + 45,
                viewport.getWorldHeight()/2 + 100, 90, 50);*/
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font.getData().setScale(1.5f);
    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        //p.render(batch);
        font.draw(batch, "START", viewport.getWorldWidth() / 2,
                viewport.getWorldHeight() / 2+5, 0, Align.center, false);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("han pulsao");
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        /*if (p.left < screenX && screenX < p.right &&
                p.bottom < screenY && screenY < p.top) {*/
        if(worldTouch.dst(new Vector2(viewport.getWorldWidth() / 2,
                viewport.getWorldHeight() / 2+5)) < 45){
            System.out.println("dentro");
            game.showGameScreen();
        }

        return true;
    }
}
