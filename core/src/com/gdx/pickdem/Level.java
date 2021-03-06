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
import com.gdx.pickdem.util.Timer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Level {

    private Robot robot;
    private Owl owl;
    private Array<Platform> platforms;
    public Viewport viewport;
    private float maxX;
    private Wall walls;
    private Water water;
    private Array<Environment> environments;
    private Portal portal;
    private boolean added;
    private boolean complete;
    private Timer timer;

    public Level(Viewport viewport, Timer t) {
        this.viewport = viewport;
        platforms = new Array<Platform>();
        environments = null;
        maxX= Integer.MIN_VALUE;
        this.timer = t;
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
            environments = new Array<Environment>();
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
            environments.sort(new Comparator<Environment>() {
                @Override
                public int compare(Environment o1, Environment o2) {
                    if (o1.position.y < o2.position.y) {
                        return 1;
                    } else if (o1.position.y > o2.position.y) {
                        return -1;
                    }
                    return 0;
                }
            });
        }
    }

    public boolean isAdded() {
        return added;
    }

    public boolean isComplete() {
        return complete;
    }
}
