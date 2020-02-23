package com.gdx.pickdem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.gdx.pickdem.entity.Platform;
import com.gdx.pickdem.entity.Robot;
import com.gdx.pickdem.util.Constants;

public class Level {

    Robot robot;
    DelayedRemovalArray<Platform> platforms;

    public Level() {
        robot = new Robot();
        platforms = new DelayedRemovalArray<Platform>();
        platforms.add(new Platform(10, 30, 20, 20));
        platforms.add(new Platform(60, 60, 20, 20));
    }

    public void update(float delta) {
        robot.update(delta, platforms);
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Platform platform : platforms)
            platform.render(renderer);

        renderer.line(0,0,200,0);

        /*renderer.line(10,0,10,80);
        renderer.line(30,0,30,80);

        float leftFoot = robot.position.x;
        float rightFoot = robot.position.x + Constants.ROBOT_STANCE_WIDTH;
        //leftFoot = robot.position.x - Constants.ROBOT_STANCE_WIDTH / 2;
        //rightFoot = robot.position.x + Constants.ROBOT_STANCE_WIDTH / 2;
        renderer.setColor(1, 0, 0, 1);
        renderer.line(leftFoot, 0, leftFoot, 100);
        renderer.setColor(0, 1, 0, 1);
        renderer.line(rightFoot, 0, rightFoot, 100);*/
        renderer.end();

        batch.begin();
        robot.render(batch);
        batch.end();
    }

}
