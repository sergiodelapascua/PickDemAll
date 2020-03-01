package com.gdx.pickdem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.pickdem.entity.Owl;
import com.gdx.pickdem.entity.Platform;
import com.gdx.pickdem.entity.Portal;
import com.gdx.pickdem.entity.Robot;
import com.gdx.pickdem.environment.BigRock;
import com.gdx.pickdem.environment.Bush;
import com.gdx.pickdem.environment.Environment;
import com.gdx.pickdem.environment.Flower;
import com.gdx.pickdem.environment.LittleRock;
import com.gdx.pickdem.environment.Tree;
import com.gdx.pickdem.environment.Wall;
import com.gdx.pickdem.environment.Water;
import com.gdx.pickdem.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private Robot robot;
    private Owl owl;
    private Array<Platform> platforms;
    private Viewport viewport;
    private float maxX;
    private Wall walls;
    private Water water;
    private List<Environment> environments;
    private Portal portal;
    private boolean added;
    private boolean complete;

    public Level(Viewport viewport) {
        this.viewport = viewport;
        platforms = new Array<Platform>();
        environments = null;
        maxX= Integer.MIN_VALUE;
        walls = null;
        water = null;
        portal = null;
        added = false;
        complete = false;
        initLevel();
    }

    public void update(float delta) {
        if(walls == null){
            for (Platform p : platforms){
                if(p.right > maxX) {
                    maxX = p.right;
                }
            }
            walls = new Wall(maxX);
        }

        if(water == null)
            water = new Water(maxX);

        if (environments == null)
            generateEnvironment();

        if(robot.getCollectedCoins() != 6) {
            portal = null;
            added = false;
            complete = false;
        }

        if(portal != null) {
            if(robot.position.x > portal.position.x && robot.position.x + Constants.ROBOT_STANCE_WIDTH < portal.position.x + Constants.PORTAL_RADIUS*2
                    && robot.position.y > portal.position.y && robot.position.y < portal.position.y + Constants.PORTAL_HEIGHT){
                complete = true;
            }
        }

        //TODO: Atento a que este activo
        if(!complete) {
            robot.update(delta, platforms);
            //owl.update(delta);
        }
    }

    public void render(SpriteBatch batch) {

        /*renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(1, 0, 0, 1);
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
        renderer.line(-100, robot.position.y, 100, robot.positioRADIUSn.y);
        renderer.line(-100, robot.position.y+Constants.ROBOT_HEAD_HEIGHT, 100, robot.position.y+Constants.ROBOT_HEAD_HEIGHT);

        //=========
        renderer.end();*/

        batch.begin();
        water.render(batch);
        walls.render(batch);
        for (Platform platform : platforms)
            platform.render(batch);
        for(Environment e : environments)
            e.render(batch);
        if(portal != null)
            portal.render(batch);
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

    public float getMaxX() {
        return maxX;
    }

    public void addPortal(){
        if(robot.getCollectedCoins() == 6){
            int i = (int) (Math.random() * platforms.size - 1) + 1;
            Platform p = platforms.get(i);
            float x = (float) ((Math.random() * ((p.right - 10) - p.left)) + p.left);
            float y = p.top + 1;
            portal = new Portal(new Vector2(x,y));
            added = true;
        }
    }

    public void generateEnvironment(){
        if(platforms.size > 0) {
            environments = new ArrayList<Environment>();
            List<Integer> platformIndex = new ArrayList<Integer>();
            while (platformIndex.size() < 60) {
                int index = (int) (Math.random() * platforms.size - 1) + 1;
                /*if (platformIndex.contains(index))
                    continue;
                else*/
                    platformIndex.add(index);
            }

            for (Integer i : platformIndex) {
                Platform p = platforms.get(i);
                float x = (float) ((Math.random() * ((p.right - 10) - p.left)) + p.left);
                float y = p.top + 1;
                int random = (int) (Math.random()*5);
                switch (random){
                    case 0:
                        environments.add(new Tree(new Vector2(x, y)));
                        break;
                    case 1:
                        environments.add(new Flower(new Vector2(x, y)));
                        break;
                    case 2:
                        environments.add(new BigRock(new Vector2(x, y)));
                        break;
                    case 3:
                        environments.add(new LittleRock(new Vector2(x, y)));
                        break;
                    case 4:
                        environments.add(new Bush(new Vector2(x, y)));
                        break;
                }
            }
        }
    }

    public boolean isAdded() {
        return added;
    }

    public boolean isComplete() {
        return complete;
    }

}
