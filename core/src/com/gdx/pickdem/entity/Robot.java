package com.gdx.pickdem.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.pickdem.Level;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Constants;
import com.gdx.pickdem.util.Enums;
import com.gdx.pickdem.util.Enums.Facing;
import com.gdx.pickdem.util.Enums.JumpState;
import com.gdx.pickdem.util.Enums.WalkState;
import com.gdx.pickdem.util.Utils;

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

    private Level level;


    public Robot(Vector2 spawnLocation, Level level) {
        this.spawn = spawnLocation;
        this.level = level;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();

        init();
    }

    public void init(){
        position.set(spawn);
        lastFramePosition.set(spawn);
        velocity.setZero();

        facing = Facing.RIGHT;
        jumpState = JumpState.FALLING;
        walkState = WalkState.STANDING;
    }

    //==============================================================================================
    public void update(float delta, Array<Platform> platforms) {
        lastFramePosition.set(position);
        velocity.y -= delta * Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        if (jumpState != JumpState.JUMPING) {
            jumpState = JumpState.FALLING;

            /*if (position.y - Constants.ROBOT_EYE_HEIGHT < 0) {
                jumpState = JumpState.GROUNDED;
                position.y = Constants.ROBOT_EYE_HEIGHT;
                velocity.y = 0;
            }*/
            if (position.y - Constants.ROBOT_EYE_HEIGHT < -100)
                this.init();


            for (Platform platform : platforms) {
                if (landedOnPlatform(platform)) {
                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = platform.top + Constants.ROBOT_EYE_HEIGHT;
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            moveLeft(delta);
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            moveRight(delta);
        else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))&& jumpState != JumpState.FALLING){
            position.y = position.y-2;
        }else
            walkState = WalkState.STANDING;

        if (Gdx.input.isKeyPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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
        TextureRegion region = null;
        boolean mirror = false;

        if (facing == Facing.RIGHT && jumpState != JumpState.GROUNDED) {

            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            region = Assets.instance.robotAssets.jumpingRightAnimation.getKeyFrame(jumpDuration);
            //region = Assets.instance.pickDemAssets.jumpingRight;
            mirror = false;

        } else if (facing == Facing.RIGHT && walkState == WalkState.STANDING) {

            float standDuration = MathUtils.nanoToSec * TimeUtils.nanoTime();
            region = Assets.instance.robotAssets.standingRightAnimation.getKeyFrame(standDuration);
            mirror = false;

        } else if (facing == Facing.RIGHT && walkState == WalkState.WALKING) {

            float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
            region = Assets.instance.robotAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            mirror = false;

        } else if (facing == Enums.Facing.LEFT && jumpState != JumpState.GROUNDED) {

            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            region = Assets.instance.robotAssets.jumpingRightAnimation.getKeyFrame(jumpDuration);
            //region = Assets.instance.pickDemAssets.jumpingRight;
            mirror = true;

        } else if (facing == Facing.LEFT && walkState == WalkState.STANDING) {

            float standDuration = MathUtils.nanoToSec * TimeUtils.nanoTime();
            region = Assets.instance.robotAssets.standingRightAnimation.getKeyFrame(standDuration);
            mirror = true;

        } else if (facing == Facing.LEFT && walkState == WalkState.WALKING) {

            float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
            region = Assets.instance.robotAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            mirror = true;

        }

        Utils.drawRobotTextureRegion(batch, region, position, mirror);
    }
}
