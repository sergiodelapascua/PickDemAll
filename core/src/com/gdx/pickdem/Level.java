package com.gdx.pickdem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.pickdem.entity.Owl;
import com.gdx.pickdem.entity.Platform;
import com.gdx.pickdem.entity.Robot;
import com.gdx.pickdem.util.Constants;

public class Level {

    private Robot robot;
    private Owl owl;
    private DelayedRemovalArray<Platform> platforms;
    private Viewport viewport;

    public Level(Viewport viewport) {
        this.viewport = viewport;
        platforms = new DelayedRemovalArray<Platform>();
        initLevel();
    }

    public void update(float delta) {
        robot.update(delta, platforms);
        //TODO: Atento a que este activo
        //owl.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        /*renderer.setColor(1, 0, 0, 1);
        renderer.line(0,0,200,15);

        renderer.line(10,0,10,80);
        renderer.line(30,0,30,80);

        float leftFoot = robot.position.x;
        float rightFoot = robot.position.x + Constants.ROBOT_STANCE_WIDTH;
        float middle = robot.position.x + Constants.ROBOT_STANCE_WIDTH/2;
        //leftFoot = robot.position.x - Constants.ROBOT_STANCE_WIDTH / 2;
        //rightFoot = robot.position.x + Constants.ROBOT_STANCE_WIDTH / 2;
        renderer.setColor(1, 1, 1, 1);
        renderer.line(owl.position.x, 0, owl.position.x, 100);
        renderer.line(owl.position.x+Constants.OWL_STANCE_WIDTH, 0, owl.position.x + Constants.OWL_STANCE_WIDTH, 100);
        renderer.line(0, owl.position.y, 100, owl.position.y);
        //renderer.line(0, 0, 100, 100);
        renderer.setColor(1, 0, 0, 1);
        renderer.line(leftFoot, 0, leftFoot, 100);
        renderer.setColor(0, 1, 0, 1);
        renderer.line(rightFoot, 0, rightFoot, 100);
        renderer.setColor(0, 0, 1, 1);
        renderer.line(middle, 0, middle, 100);
        renderer.line(-100, robot.position.y, 100, robot.position.y);
        renderer.line(-100, robot.position.y+Constants.ROBOT_HEAD_HEIGHT, 100, robot.position.y+Constants.ROBOT_HEAD_HEIGHT);*/

        //=========
        renderer.end();

        batch.begin();
        for (Platform platform : platforms)
            platform.render(batch);
        robot.render(batch);
        owl.render(batch);
        batch.end();
    }

    private void initLevel(){
        robot = new Robot(new Vector2(20, Constants.ROBOT_EYE_HEIGHT), this);
        owl = new Owl(robot);
    }

    public Robot getRobot() {
        return robot;
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }
}
