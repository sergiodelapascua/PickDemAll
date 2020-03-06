package com.gdx.pickdem.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gdx.pickdem.PickdemGame;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Constants;
import com.gdx.pickdem.util.Utils;


public class MenuScreen extends InputAdapter implements Screen {

    public static final String TAG = MenuScreen.class.getName();

    PickdemGame game;

    private SpriteBatch batch;
    private ExtendViewport viewport;
    private BitmapFont font;
    private boolean musicOn;
    private Stage stage;
    public static Sound sound;

    public MenuScreen(PickdemGame game) {
        batch = new SpriteBatch();
        this.game = game;
        this.musicOn = true;
        stage = new Stage(new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE));
    }

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        AssetManager am1 = new AssetManager();
        AssetManager am2 = new AssetManager();
        AssetManager am3 = new AssetManager();
        AssetManager am4 = new AssetManager();
        Assets.instance.init(am, am1, am2, am3, am4);

        viewport = new ExtendViewport(Constants.MENU_SIZE, Constants.MENU_SIZE);
        Gdx.input.setInputProcessor(this);
        //==========================================================================================================
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/bebas-neue.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 124;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.getData().setScale(1);

        Array<Texture> textures = new Array<Texture>();
        for(int i = 0; i <=10;i++){
            textures.add(new Texture(Gdx.files.internal("background/img"+i+".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        Background background = new Background(textures);
        background.setSize(Constants.MENU_SIZE, Constants.MENU_SIZE);
        background.setSpeed(1);
        stage.addActor(background);
        Skin skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));

        Button button2 = new TextButton("Play",skin);
        button2.setSize(110,40);
        if(!Utils.onMobile())
            button2.setPosition(40, 35);
        else
            button2.setPosition(60, 35);
        stage.addActor(button2);
        sound = Gdx.audio.newSound(Gdx.files.internal("music/menu.ogg"));
        sound.loop(0.5f);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        stage.act();
        stage.draw();
        batch.begin();
        if (!Utils.onMobile()){
            font.draw(batch, "PICKDEM-ALL", Constants.MENU_SIZE/2+80, Constants.MENU_SIZE - Constants.MENU_SIZE/5, 0, Align.center, false);
        }else {
            font.draw(batch, "PICKDEM-ALL", Constants.MENU_SIZE - 80, Constants.MENU_SIZE - Constants.MENU_SIZE/5, 0, Align.center, false);
        }
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
        Vector3 tmp = new Vector3(screenX,screenY,0);
        viewport.unproject(tmp);
        Rectangle playBounds = null;
        if(!Utils.onMobile())
            playBounds = new Rectangle(Constants.MENU_SIZE/3, Constants.MENU_SIZE/3,Constants.MENU_SIZE/2,Constants.MENU_SIZE/8);
        else
            playBounds = new Rectangle(Constants.MENU_SIZE/3, Constants.MENU_SIZE/3,Constants.MENU_SIZE/2,Constants.MENU_SIZE/8);

        if(playBounds.contains(tmp.x,tmp.y)){
            sound.dispose();
            Sound sound2 = Gdx.audio.newSound(Gdx.files.internal("music/menu-navigate.ogg"));
            sound2.play(0.5f);
            Gdx.input.setInputProcessor(null);
            game.showGameScreen();
        }
        return true;
    }
}
