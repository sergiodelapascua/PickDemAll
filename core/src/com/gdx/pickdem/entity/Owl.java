package com.gdx.pickdem.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Constants;

public class Owl {

    public Vector2 position;
    public Robot robot;
    public Facing facing;
    public float flyStartTime;

    public Owl(Robot r){
        facing = Facing.RIGHT;
        position = new Vector2(0,0);
        robot = r;
    }

    public void update(float delta) {
        if(robot.position.x > position.x)
            moveRight(delta);
        else
            moveLeft(delta);
    }

    //==============================================================================================
    private void moveLeft(float delta) {
        flyStartTime = TimeUtils.nanoTime();
        facing = Facing.LEFT;
        position.x -= delta * Constants.OWL_MOVE_SPEED_X;

        if(robot.position.y > position.y)
            position.y += delta * Constants.OWL_MOVE_SPEED_Y;
        else
            position.y -= delta * Constants.OWL_MOVE_SPEED_Y;
    }

    private void moveRight(float delta) {
        flyStartTime = TimeUtils.nanoTime();
        facing = Facing.RIGHT;

        position.x += delta * Constants.OWL_MOVE_SPEED_X;

        if(robot.position.y > position.y)
            position.y += delta * Constants.OWL_MOVE_SPEED_Y;
        else
            position.y -= delta * Constants.OWL_MOVE_SPEED_Y;
    }

    //==============================================================================================
    public void render(SpriteBatch batch) {
        TextureRegion region = null;
        boolean mirror = false;

        if (facing == Facing.RIGHT ) {
            float flyDuration = MathUtils.nanoToSec;// * (TimeUtils.nanoTime() - flyStartTime);
            region = Assets.instance.pickDemAssets.owlAnimation.getKeyFrame(flyDuration);
            mirror = true;

        } else if (facing == Facing.LEFT) {
            float flyDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - flyStartTime);
            region = Assets.instance.pickDemAssets.owlAnimation.getKeyFrame(flyDuration);
            mirror = false;

        }

        batch.draw(
                region.getTexture(),
                position.x,
                position.y - Constants.ROBOT_EYE_POSITION.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                0.2f,
                0.2f,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                mirror,
                false);


    }
    //==============================================================================================
    enum Facing {
        LEFT,
        RIGHT
    }
}
