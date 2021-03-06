package com.gdx.pickdem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gdx.pickdem.overlays.GameOverOverlay;
import com.gdx.pickdem.overlays.OnscreenControls;
import com.gdx.pickdem.overlays.PickDemHud;
import com.gdx.pickdem.overlays.VictoryOverlay;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.ChaseCam;
import com.gdx.pickdem.util.Constants;
import com.gdx.pickdem.util.LevelLoader;
import com.gdx.pickdem.util.Timer;
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
    private GameOverOverlay gameOverOverlay;
    private long levelEndOverlayStartTime;
    private Timer timer;
    private boolean gameOver;
    private OnscreenControls onscreenControls;
    private Sound sound;

    private PickdemGame game;

    public GameplayScreen(PickdemGame g){
        this.game = g;
        sound = Gdx.audio.newSound(Gdx.files.internal("music/game.ogg"));
        sound.loop(0.5f);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        onscreenControls = new OnscreenControls();
        if (Utils.onMobile()) {
            Gdx.input.setInputProcessor(onscreenControls);
        }
        startNewLevel();

        victory = false;
        gameOver = false;
        victoryOverlay = new VictoryOverlay();
        gameOverOverlay = new GameOverOverlay();
        levelEndOverlayStartTime = 0;
    }

    @Override
    public void resize(int width, int height) {
        if(victory)
            victoryOverlay.viewport.update(width, height, true);
        else if(gameOver)
            gameOverOverlay.viewport.update(width, height, true);
        else {
            hud.viewport.update(width, height, true);
            viewport.update(width, height, true);
            if(Utils.onMobile()) {
                onscreenControls.viewport.update(width, height, true);
                onscreenControls.recalculateButtonPositions();
            }
        }
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        batch.dispose();
    }

    @Override
    public void render(float delta) {
        if (timer == null){
            timer = new Timer();
            timer.startTimer();
        }
        timer.updateCountDownText();
        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        if(timer.getCountdown() > 0){
            level.update(delta);
            chaseCam.update();
            level.render(batch);
        } else {
            gameOver = true;
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        level.render(batch);
        hud.render(batch, level.getRobot().getCollectedCoins(), timer.getCountdown());

        if(Utils.onMobile()) {
            onscreenControls.render(batch);
        }
        if(level.isComplete())
            startNewLevel();
        renderLevelEndOverlays(batch);
    }

    private void renderLevelEndOverlays(SpriteBatch batch) {
        if (victory) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                sound.dispose();
                victoryOverlay.init();
            }
            victoryOverlay.render(batch);

            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                game.showMenuScreen();
            }
        }
        if (gameOver) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                sound.dispose();
                gameOverOverlay.init();
            }
            gameOverOverlay.render(batch);

            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                game.showMenuScreen();
            }
        }
    }

    private void startNewLevel() {
        victory = false;
        timer = null;
        hud = new PickDemHud();
        String nextLevel = "";
        if (loadedLevel.equals("")) {
            nextLevel = "Level1";
            loadedLevel = nextLevel;
        }else if(loadedLevel.equals("Level1")) {
            Sound s = Gdx.audio.newSound(Gdx.files.internal("music/portal.ogg"));
            s.play(0.5f);
            nextLevel = "Level2";
            loadedLevel = nextLevel;
        }else if(loadedLevel.equals("Level2")) {
            Sound s = Gdx.audio.newSound(Gdx.files.internal("music/portal.ogg"));
            s.play(0.5f);
            nextLevel = "Level3";
            loadedLevel = nextLevel;
        }else if(loadedLevel.equals("Level3")) {
            victory = true;
        }
        if(!victory) {
            level = LevelLoader.load(nextLevel, viewport, timer);
            chaseCam = new ChaseCam(viewport.getCamera(), level.getRobot(), level);
            onscreenControls.setRobot(level.getRobot());
        }
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}

