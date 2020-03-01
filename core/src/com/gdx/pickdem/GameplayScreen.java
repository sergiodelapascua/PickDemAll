package com.gdx.pickdem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gdx.pickdem.overlays.PickDemHud;
import com.gdx.pickdem.overlays.VictoryOverlay;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.ChaseCam;
import com.gdx.pickdem.util.Constants;
import com.gdx.pickdem.util.LevelLoader;
import com.gdx.pickdem.util.Utils;

public class GameplayScreen extends ScreenAdapter {

    public static final String TAG = GameplayScreen.class.getName();

    SpriteBatch batch;
    Level level;
    ExtendViewport viewport;
    ShapeRenderer renderer;
    private ChaseCam chaseCam;
    private String loadedLevel = "";
    private PickDemHud hud;
    private boolean victory;
    private VictoryOverlay victoryOverlay;
    private long levelEndOverlayStartTime;

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        AssetManager am1 = new AssetManager();
        AssetManager am2 = new AssetManager();
        AssetManager am3 = new AssetManager();
        AssetManager am4 = new AssetManager();
        Assets.instance.init(am, am1, am2, am3, am4);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        startNewLevel();
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        chaseCam = new ChaseCam(viewport.getCamera(), level.getRobot(), level);
        victory = false;
        victoryOverlay = new VictoryOverlay();
        levelEndOverlayStartTime = 0;
    }

    @Override
    public void resize(int width, int height) {
        hud.viewport.update(width, height, true);
        if(victory)
            victoryOverlay.viewport.update(width, height, true);
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
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        level.render(batch);
        hud.render(batch, level.getRobot().getCollectedCoins());
        if(level.isComplete()) {
            startNewLevel();
        }
        renderLevelEndOverlays(batch);
    }

    private void renderLevelEndOverlays(SpriteBatch batch) {
        if (victory) {
            if (levelEndOverlayStartTime == 0) {
                System.out.println("E");
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                victoryOverlay.init();
            }
            victoryOverlay.render(batch);

            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                startNewLevel();
            }
        }
    }

    private void startNewLevel() {
        victory = false;
        hud = new PickDemHud();
        String nextLevel = "";
        if (loadedLevel.equals("")) {
            nextLevel = "Level1";
            loadedLevel = "Level1";
        }else if(loadedLevel.equals("Level1")) {
            nextLevel = "Level2";
            loadedLevel = "Level2";
        }else if(loadedLevel.equals("Level2")) {
            victory = true;
        }
        if(!victory) {
            level = LevelLoader.load(nextLevel, viewport);
            chaseCam = new ChaseCam(viewport.getCamera(), level.getRobot(), level);
        }
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}

