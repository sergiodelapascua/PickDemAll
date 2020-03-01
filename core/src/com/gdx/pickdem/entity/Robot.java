package com.gdx.pickdem.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.pickdem.Level;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Constants;
import com.gdx.pickdem.util.Enums;
import com.gdx.pickdem.util.Enums.Facing;
import com.gdx.pickdem.util.Enums.JumpState;
import com.gdx.pickdem.util.Enums.WalkState;
import com.gdx.pickdem.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    public final static String TAG = Robot.class.getName();
    public Vector2 spawn;
    public Vector2 position;
    private Vector2 lastFramePosition;
    private Facing facing;
    private Vector2 velocity;
    private JumpState jumpState;
    private long jumpStartTime;
    private WalkState walkState;
    private long walkStartTime;
    private int collectedCoins;

    private Level level;
    private DelayedRemovalArray<Coin> coins;


    public Robot(Vector2 spawnLocation, Level level) {
        this.spawn = spawnLocation;
        this.level = level;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();
        coins = null;

        init();
    }

    public void init() {
        collectedCoins = 0;
        position.set(spawn);
        lastFramePosition.set(spawn);
        velocity.setZero();

        facing = Facing.RIGHT;
        jumpState = JumpState.FALLING;
        walkState = WalkState.STANDING;

        generateCoins();
        level.generateEnvironment();
    }

    private void generateCoins() {
        if (level.getPlatforms().size > 0) {
            collectedCoins = 0;
            coins = new DelayedRemovalArray<Coin>();
            List<Integer> platformIndex = new ArrayList<Integer>();
            while (platformIndex.size() != Constants.COINS) {
                int index = (int) (Math.random() * level.getPlatforms().size - 1) + 1;
                if (platformIndex.contains(index))
                    continue;
                else
                    platformIndex.add(index);
            }

            for (Integer i : platformIndex) {
                Platform p = level.getPlatforms().get(i);
                float x = (float) ((Math.random() * ((p.right - 10) - p.left)) + p.left);
                //float x = (p.right - p.left)/2 + p.left;
                float y = p.top + 1;
                coins.add(new Coin(new Vector2(x, y)));
            }
        }
    }

    //==============================================================================================
    public void update(float delta, Array<Platform> platforms) {
        if (coins == null)
            generateCoins();
        if (collectedCoins == 6 && !level.isAdded())
            level.addPortal();
        lastFramePosition.set(position);
        velocity.y -= delta * Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        if (jumpState != JumpState.JUMPING) {
            jumpState = JumpState.FALLING;

            if (position.y - Constants.ROBOT_EYE_HEIGHT < -16)
                this.init();


            for (Platform platform : platforms) {
                if (landedOnPlatform(platform)) {
                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = platform.top + Constants.ROBOT_EYE_HEIGHT;
                }
            }
        }

        /*if(position.x < 0)
            position.x = 0;
        else if(position.x + Constants.ROBOT_STANCE_WIDTH > level.getMaxX())
            position.x = level.getMaxX() - Constants.ROBOT_STANCE_WIDTH;*/

        Rectangle robotBounds = new Rectangle(
                position.x,
                position.y - Constants.ROBOT_EYE_HEIGHT,
                Constants.ROBOT_STANCE_WIDTH,
                Constants.ROBOT_EYE_HEIGHT);

        coins.begin();
        for (int i = 0; i < coins.size; i++) {
            Coin c = coins.get(i);
            Rectangle coinBounds = new Rectangle(
                    c.position.x - Constants.COIN_CENTER.x,
                    c.position.y - Constants.COIN_CENTER.y,
                    Assets.instance.coinAssets.coin.getRegionWidth(),
                    Assets.instance.coinAssets.coin.getRegionHeight()
            );
            if (robotBounds.overlaps(coinBounds)) {
                coins.removeIndex(i);
                collectedCoins++;
            }
        }
        coins.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            moveLeft(delta);
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            moveRight(delta);
        else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) && jumpState != JumpState.FALLING) {
            position.y = position.y - 2;
        } else
            walkState = WalkState.STANDING;

        if (Gdx.input.isKeyPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
            }
        } else {
            endJump();
        }
    }

    //==============================================================================================
    private boolean landedOnPlatform(Platform platform) {

        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.ROBOT_EYE_HEIGHT >= platform.top &&
                position.y - Constants.ROBOT_EYE_HEIGHT < platform.top) {

            /*float leftFoot = position.x - Constants.ROBOT_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.ROBOT_STANCE_WIDTH / 2;*/
            float leftFoot = position.x;
            float rightFoot = position.x + Constants.ROBOT_STANCE_WIDTH;

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot);
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot);

            straddle = (platform.left > leftFoot && platform.right < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }

    //==============================================================================================
    private void startJump() {
        jumpState = JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            if (jumpDuration < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == JumpState.JUMPING)
            jumpState = JumpState.FALLING;
    }

    //==============================================================================================
    private void moveLeft(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING)
            walkStartTime = TimeUtils.nanoTime();

        walkState = WalkState.WALKING;
        facing = Facing.LEFT;
        position.x -= delta * Constants.ROBOT_MOVE_SPEED;
    }

    private void moveRight(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING)
            walkStartTime = TimeUtils.nanoTime();

        walkState = WalkState.WALKING;
        facing = Facing.RIGHT;
        position.x += delta * Constants.ROBOT_MOVE_SPEED;
    }

    //==============================================================================================
    public void render(SpriteBatch batch) {
        for (Coin c : coins)
            c.render(batch);
        TextureRegion region = null;
        boolean mirror = false;

        if (facing == Facing.RIGHT && jumpState != JumpState.GROUNDED) {

            float jumpDuration = Utils.secondsSince(jumpStartTime);
            region = Assets.instance.robotAssets.jumpingRightAnimation.getKeyFrame(jumpDuration);
            //region = Assets.instance.pickDemAssets.jumpingRight;
            mirror = false;

        } else if (facing == Facing.RIGHT && walkState == WalkState.STANDING) {

            float standDuration = MathUtils.nanoToSec * TimeUtils.nanoTime();
            region = Assets.instance.robotAssets.standingRightAnimation.getKeyFrame(standDuration);
            mirror = false;

        } else if (facing == Facing.RIGHT && walkState == WalkState.WALKING) {

            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = Assets.instance.robotAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            mirror = false;

        } else if (facing == Enums.Facing.LEFT && jumpState != JumpState.GROUNDED) {

            float jumpDuration = Utils.secondsSince(jumpStartTime);
            region = Assets.instance.robotAssets.jumpingRightAnimation.getKeyFrame(jumpDuration);
            //region = Assets.instance.pickDemAssets.jumpingRight;
            mirror = true;

        } else if (facing == Facing.LEFT && walkState == WalkState.STANDING) {

            float standDuration = MathUtils.nanoToSec * TimeUtils.nanoTime();
            region = Assets.instance.robotAssets.standingRightAnimation.getKeyFrame(standDuration);
            mirror = true;

        } else if (facing == Facing.LEFT && walkState == WalkState.WALKING) {

            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = Assets.instance.robotAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            mirror = true;

        }

        Utils.drawRobotTextureRegion(batch, region, position, mirror);
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }
}
