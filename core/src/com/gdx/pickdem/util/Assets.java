package com.gdx.pickdem.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    public PickDemAssets pickDemAssets;

    private AssetManager assetManager;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        pickDemAssets = new PickDemAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
//=======================================================================
    public class PickDemAssets {

        public final Animation walkingRightAnimation;
        public final Animation jumpingRightAnimation;
        public final Animation standingRightAnimation;

        public final Animation owlAnimation;

        public PickDemAssets(TextureAtlas atlas) {

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

            Array<TextureAtlas.AtlasRegion> owl = new Array<TextureAtlas.AtlasRegion>();
            owl.add(atlas.findRegion("buho(1)"));
            owl.add(atlas.findRegion("buho(2)"));
            owl.add(atlas.findRegion("buho(1)"));
            owlAnimation = new Animation(Constants.OWL_LOOP_DURATION, owl, Animation.PlayMode.LOOP);
        }
    }
}
