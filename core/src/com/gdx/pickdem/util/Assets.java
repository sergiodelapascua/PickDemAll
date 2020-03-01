package com.gdx.pickdem.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    public RobotAssets robotAssets;
    public PlatformAssets platformAssets;
    public OwlAssets owlAssets;
    public CoinAssets coinAssets;
    public WallAssets wallAssets;
    public WaterAssets waterAssets;
    public FlowerAssets flowerAssets;
    public TreeAssets treeAssets;
    public BigRockAssets bigRock;
    public LittleRockAssets littleRockAssets;
    public BushAssets bushAssets;
    public PortalAssets portalAssets;
    public ExplosionAssets explosionAssets;
    public RedFireworkAssets redFireworkAssets;
    public BlueFireworkAssets blueFireworkAssets;
    public VioletFireworkAssets violetFireworkAssets;
    public YellowFireworkAssets yellowFireworkAssets;
    public SkullAssets skullAssets;

    private AssetManager assetManager;
    private AssetManager assetManager1;
    private AssetManager assetManager2;
    private AssetManager assetManager3;
    private AssetManager assetManager4;

    private Assets() {
    }

    public void init(AssetManager am, AssetManager am1, AssetManager am2, AssetManager am3, AssetManager am4) {
        this.assetManager = am;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        robotAssets = new RobotAssets(atlas);
        platformAssets = new PlatformAssets(atlas);
        owlAssets = new OwlAssets(atlas);
        coinAssets = new CoinAssets(atlas);
        wallAssets = new WallAssets(atlas);
        waterAssets = new WaterAssets(atlas);
        flowerAssets = new FlowerAssets(atlas);
        treeAssets = new TreeAssets(atlas);
        bigRock = new BigRockAssets(atlas);
        littleRockAssets = new LittleRockAssets(atlas);
        bushAssets = new BushAssets(atlas);
        portalAssets = new PortalAssets(atlas);
        explosionAssets = new ExplosionAssets(atlas);
        skullAssets = new SkullAssets(atlas);

        this.assetManager1 = am1;
        assetManager1.setErrorListener(this);
        assetManager1.load("images/redshot.atlas", TextureAtlas.class);
        assetManager1.finishLoading();
        TextureAtlas atlasRedFirework = assetManager1.get("images/redshot.atlas");
        redFireworkAssets = new RedFireworkAssets(atlasRedFirework);

        this.assetManager2 = am2;
        assetManager2.setErrorListener(this);
        assetManager2.load("images/blueshot.atlas", TextureAtlas.class);
        assetManager2.finishLoading();
        TextureAtlas atlasBlueFirework = assetManager2.get("images/blueshot.atlas");
        blueFireworkAssets = new BlueFireworkAssets(atlasBlueFirework);

        this.assetManager3 = am3;
        assetManager3.setErrorListener(this);
        assetManager3.load("images/violetshot.atlas", TextureAtlas.class);
        assetManager3.finishLoading();
        TextureAtlas atlasVioletFirework = assetManager3.get("images/violetshot.atlas");
        violetFireworkAssets = new VioletFireworkAssets(atlasVioletFirework);

        this.assetManager4 = am3;
        assetManager4.setErrorListener(this);
        assetManager4.load("images/yellowshot.atlas", TextureAtlas.class);
        assetManager4.finishLoading();
        TextureAtlas atlasYellowFirework = assetManager4.get("images/yellowshot.atlas");
        yellowFireworkAssets = new YellowFireworkAssets(atlasYellowFirework);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        assetManager1.dispose();
        assetManager2.dispose();
        assetManager3.dispose();
        assetManager4.dispose();
    }

    //=======================================================================
    public class RobotAssets {

        public final Animation walkingRightAnimation;
        public final Animation jumpingRightAnimation;
        public final Animation standingRightAnimation;

        public RobotAssets(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> walkingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT));
            walkingRightFrames.add(atlas.findRegion("Run(2)"));
            //walkingRightFrames.add(atlas.findRegion("Run(3)"));
            walkingRightFrames.add(atlas.findRegion("Run(4)"));
            walkingRightFrames.add(atlas.findRegion("Run(5)"));
            walkingRightFrames.add(atlas.findRegion("Run(6)"));
            walkingRightFrames.add(atlas.findRegion("Run(7)"));
            walkingRightFrames.add(atlas.findRegion("Run(8)"));
            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP);

            Array<TextureAtlas.AtlasRegion> jumpingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            jumpingRightFrames.add(atlas.findRegion(Constants.JUMPING_RIGHT));
            jumpingRightFrames.add(atlas.findRegion("Jump(2)"));
            jumpingRightFrames.add(atlas.findRegion("Jump(3)"));
            jumpingRightFrames.add(atlas.findRegion("Jump(4)"));
            jumpingRightFrames.add(atlas.findRegion("Jump(5)"));
            jumpingRightFrames.add(atlas.findRegion("Jump(6)"));
            jumpingRightFrames.add(atlas.findRegion("Jump(7)"));
            jumpingRightFrames.add(atlas.findRegion("Jump(8)"));
            jumpingRightFrames.add(atlas.findRegion("Jump(9)"));
            //jumpingRightFrames.add(atlas.findRegion("Jump(10)"));
            jumpingRightAnimation = new Animation(Constants.JUMP_LOOP_DURATION, jumpingRightFrames, Animation.PlayMode.NORMAL);

            Array<TextureAtlas.AtlasRegion> standingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            standingRightFrames.add(atlas.findRegion(Constants.STANDING_RIGHT));
            standingRightFrames.add(atlas.findRegion("Idle(2)"));
            standingRightFrames.add(atlas.findRegion("Idle(3)"));
            standingRightFrames.add(atlas.findRegion("Idle(4)"));
            standingRightFrames.add(atlas.findRegion("Idle(5)"));
            standingRightFrames.add(atlas.findRegion("Idle(6)"));
            standingRightFrames.add(atlas.findRegion("Idle(7)"));
            standingRightFrames.add(atlas.findRegion("Idle(8)"));
            standingRightFrames.add(atlas.findRegion("Idle(9)"));
            standingRightFrames.add(atlas.findRegion("Idle(10)"));
            standingRightAnimation = new Animation(Constants.STAND_LOOP_DURTATION, standingRightFrames, Animation.PlayMode.LOOP);


        }
    }

    public class OwlAssets {

        public final Animation owlAnimation;

        public OwlAssets(TextureAtlas atlas){
            Array<TextureAtlas.AtlasRegion> owl = new Array<TextureAtlas.AtlasRegion>();
            owl.add(atlas.findRegion("buho(1)"));
            owl.add(atlas.findRegion("buho(3)"));
            //owl.add(atlas.findRegion("buho(1)"));
            //owl.add(atlas.findRegion("buho(2)"));
            //owl.add(atlas.findRegion("buho(1)"));
            owlAnimation = new Animation(Constants.OWL_LOOP_DURATION, owl, Animation.PlayMode.LOOP);
        }
    }

    public class PlatformAssets {

        public final NinePatch platformNinePatch;

        public PlatformAssets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion("plat");
            int edge = 2;
            platformNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class CoinAssets {
        public final TextureAtlas.AtlasRegion coin;
        public CoinAssets(TextureAtlas atlas) {
            coin = atlas.findRegion("moneda");
        }
    }

    public class WallAssets {
        public final TextureAtlas.AtlasRegion wall;
        public WallAssets(TextureAtlas atlas) {
            wall = atlas.findRegion("muro");
        }
    }

    public class WaterAssets {
        public final TextureAtlas.AtlasRegion water;
        public WaterAssets(TextureAtlas atlas) {
            water = atlas.findRegion("agua");
        }
    }

    public class FlowerAssets {
        public final TextureAtlas.AtlasRegion flower;
        public FlowerAssets(TextureAtlas atlas) {
            flower = atlas.findRegion("flor");
        }
    }

    public class TreeAssets {
        public final TextureAtlas.AtlasRegion tree;
        public TreeAssets(TextureAtlas atlas) { tree = atlas.findRegion("arbol"); }
        //public TreeAssets(TextureAtlas atlas) { tree = atlas.findRegion("tronco"); }
    }

    public class LittleRockAssets {
        public final TextureAtlas.AtlasRegion rock;
        public LittleRockAssets(TextureAtlas atlas) {
            rock = atlas.findRegion("roca(1)");
        }
    }

    public class BigRockAssets {
        public final TextureAtlas.AtlasRegion rock;
        public BigRockAssets(TextureAtlas atlas) {
            rock = atlas.findRegion("roca(2)");
        }
    }

    public class BushAssets {
        public final TextureAtlas.AtlasRegion arbusto;
        public BushAssets(TextureAtlas atlas) {
            arbusto = atlas.findRegion("arbusto");
        }
    }

    public class PortalAssets {

        public final Animation portalAnimation;

        public PortalAssets(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> portalFrames = new Array<TextureAtlas.AtlasRegion>();
            portalFrames.add(atlas.findRegion("portal(1)"));
            portalFrames.add(atlas.findRegion("portal(2)"));
            portalFrames.add(atlas.findRegion("portal(3)"));
            portalFrames.add(atlas.findRegion("portal(4)"));
            portalAnimation = new Animation(Constants.PORTAL_LOOP_DURATION, portalFrames, Animation.PlayMode.LOOP);
        }
    }

    public class ExplosionAssets {

        public final Animation explosion;

        public ExplosionAssets(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> explosionRegions = new Array<TextureAtlas.AtlasRegion>();
            explosionRegions.add(atlas.findRegion("explosion-small"));
            explosionRegions.add(atlas.findRegion("explosion-medium"));
            explosionRegions.add(atlas.findRegion("explosion-large"));

            explosion = new Animation(Constants.OVERLAY_DURATION / explosionRegions.size, explosionRegions, Animation.PlayMode.NORMAL);
        }
    }

    public class RedFireworkAssets {

        public final Animation explosion;

        public RedFireworkAssets(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> explosionRegions = new Array<TextureAtlas.AtlasRegion>();
            explosionRegions.add(atlas.findRegion("firework_red0"));
            explosionRegions.add(atlas.findRegion("firework_red1"));
            explosionRegions.add(atlas.findRegion("firework_red2"));
            explosionRegions.add(atlas.findRegion("firework_red3"));
            explosionRegions.add(atlas.findRegion("firework_red4"));
            explosionRegions.add(atlas.findRegion("firework_red5"));
            explosionRegions.add(atlas.findRegion("firework_red6"));
            explosionRegions.add(atlas.findRegion("firework_red7"));

            explosion = new Animation(Constants.OVERLAY_DURATION / explosionRegions.size, explosionRegions, Animation.PlayMode.NORMAL);
        }
    }

    public class BlueFireworkAssets {

        public final Animation explosion;

        public BlueFireworkAssets(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> explosionRegions = new Array<TextureAtlas.AtlasRegion>();
            explosionRegions.add(atlas.findRegion("firework_blue0"));
            explosionRegions.add(atlas.findRegion("firework_blue1"));
            explosionRegions.add(atlas.findRegion("firework_blue2"));
            explosionRegions.add(atlas.findRegion("firework_blue3"));
            explosionRegions.add(atlas.findRegion("firework_blue4"));
            explosionRegions.add(atlas.findRegion("firework_blue5"));
            explosionRegions.add(atlas.findRegion("firework_blue6"));
            explosionRegions.add(atlas.findRegion("firework_blue7"));

            explosion = new Animation(Constants.OVERLAY_DURATION / explosionRegions.size, explosionRegions, Animation.PlayMode.NORMAL);
        }
    }

    public class VioletFireworkAssets {

        public final Animation explosion;

        public VioletFireworkAssets(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> explosionRegions = new Array<TextureAtlas.AtlasRegion>();
            explosionRegions.add(atlas.findRegion("firework_violet0"));
            explosionRegions.add(atlas.findRegion("firework_violet1"));
            explosionRegions.add(atlas.findRegion("firework_violet2"));
            explosionRegions.add(atlas.findRegion("firework_violet3"));
            explosionRegions.add(atlas.findRegion("firework_violet4"));
            explosionRegions.add(atlas.findRegion("firework_violet5"));
            explosionRegions.add(atlas.findRegion("firework_violet6"));
            explosionRegions.add(atlas.findRegion("firework_violet7"));

            explosion = new Animation(Constants.OVERLAY_DURATION / explosionRegions.size, explosionRegions, Animation.PlayMode.NORMAL);
        }
    }

    public class YellowFireworkAssets {

        public final Animation explosion;

        public YellowFireworkAssets(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> explosionRegions = new Array<TextureAtlas.AtlasRegion>();
            explosionRegions.add(atlas.findRegion("firework_yellow0"));
            explosionRegions.add(atlas.findRegion("firework_yellow1"));
            explosionRegions.add(atlas.findRegion("firework_yellow2"));
            explosionRegions.add(atlas.findRegion("firework_yellow3"));
            explosionRegions.add(atlas.findRegion("firework_yellow4"));
            explosionRegions.add(atlas.findRegion("firework_yellow5"));
            explosionRegions.add(atlas.findRegion("firework_yellow6"));
            explosionRegions.add(atlas.findRegion("firework_yellow7"));

            explosion = new Animation(Constants.OVERLAY_DURATION / explosionRegions.size, explosionRegions, Animation.PlayMode.NORMAL);
        }
    }

    public class SkullAssets {

        public final Animation skull;

        public SkullAssets(TextureAtlas atlas) {

            Array<TextureAtlas.AtlasRegion> regions = new Array<TextureAtlas.AtlasRegion>();
            regions.add(atlas.findRegion("calavera1"));
            regions.add(atlas.findRegion("calavera2"));

            skull = new Animation(Constants.OVERLAY_DURATION, regions, Animation.PlayMode.LOOP);
        }
    }
}
