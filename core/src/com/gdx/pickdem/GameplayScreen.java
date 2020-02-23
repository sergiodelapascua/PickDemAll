package com.gdx.pickdem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.ChaseCam;
import com.gdx.pickdem.util.Constants;

public class GameplayScreen extends ScreenAdapter {

    public static final String TAG = GameplayScreen.class.getName();

    SpriteBatch batch;
    Level level;
    ExtendViewport viewport;
    ShapeRenderer renderer;
    private ChaseCam chaseCam;
    private Texture foto;

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        level = new Level();
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        chaseCam = new ChaseCam(viewport.getCamera(), level.robot);
        //foto = new Texture("/home/sergio/2DAM/JuegoAndroid/Nature Platformer Tileset [16x16][FREE] - RottingPixels/screenshot-1.png");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        batch.dispose();
    }

    @Override
    public void render(float delta) {
        level.update(delta);

        chaseCam.update();

        viewport.apply();
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        /*batch.begin();
        batch.draw(foto,0,0, Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        batch.end();*/
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        level.render(batch, renderer);
    }
}

