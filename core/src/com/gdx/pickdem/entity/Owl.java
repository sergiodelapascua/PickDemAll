package com.gdx.pickdem.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Constants;
import com.gdx.pickdem.util.Enums.Facing;
import com.gdx.pickdem.util.Utils;

public class Owl {

    public Vector2 position;
    private Robot robot;
    private Facing facing;
    private float flyStartTime;

    public Owl(Robot r){
        facing = Facing.RIGHT;
        position = new Vector2(0,-20);
        robot = r;
    }

    public void update(float delta) {
        if(robotOnHit())
            robot.init();

        if(robot.position.x > position.x)
            moveRight(delta);
        else
            moveLeft(delta);

    }
    //==============================================================================================

    private boolean robotOnHit(){
        boolean leftHit = false;
        boolean rightHit = false;

        if(robot.position.x + Constants.ROBOT_STANCE_WIDTH / 2 < position.x + Constants.OWL_STANCE_WIDTH && robot.position.x > position.x
                && robot.position.y < position.y && position.y < robot.position.y + Constants.ROBOT_HEAD_HEIGHT)
            leftHit = true;
        else if(robot.position.x + Constants.ROBOT_STANCE_WIDTH/2 < position.x  && robot.position.x + Constants.ROBOT_STANCE_WIDTH > position.x
                && robot.position.y < position.y && position.y < robot.position.y + Constants.ROBOT_HEAD_HEIGHT)
            rightHit = true;

        return leftHit || rightHit;
    }

    //==============================================================================================
    private void moveLeft(float delta) {
        flyStartTime = TimeUtils.nanoTime();
        facing = Facing.LEFT;
        if(Utils.onMobile())
            position.x -= delta * Constants.OWL_MOVE_SPEED_X_MOBILE;
        else
        position.x -= delta * Constants.OWL_MOVE_SPEED_X;

        if(robot.position.y + Constants.ROBOT_HEAD_HEIGHT > position.y)
            position.y += delta * Constants.OWL_MOVE_SPEED_Y;
        else
            position.y -= delta * Constants.OWL_MOVE_SPEED_Y;
    }

    private void moveRight(float delta) {
        flyStartTime = TimeUtils.nanoTime();
        facing = Facing.RIGHT;

        if(Utils.onMobile())
            position.x += delta * Constants.OWL_MOVE_SPEED_X_MOBILE;
        else
            position.x += delta * Constants.OWL_MOVE_SPEED_X;

        if(robot.position.y + Constants.ROBOT_HEAD_HEIGHT > position.y)
            position.y += delta * Constants.OWL_MOVE_SPEED_Y;
        else
            position.y -= delta * Constants.OWL_MOVE_SPEED_Y;
    }

    //==============================================================================================
    public void render(SpriteBatch batch) {
        TextureRegion region = null;
        boolean mirror = false;

        if (facing == Facing.RIGHT ) {
            float flyDuration = MathUtils.nanoToSec* TimeUtils.nanoTime();
            region = Assets.instance.owlAssets.owlAnimation.getKeyFrame(flyDuration);
            mirror = true;

        } else if (facing == Facing.LEFT) {
            float flyDuration = MathUtils.nanoToSec* TimeUtils.nanoTime();
            region = Assets.instance.owlAssets.owlAnimation.getKeyFrame(flyDuration);
            mirror = false;

        }
        Utils.drawOwlTextureRegion(batch, region, position, mirror);
    }
}
