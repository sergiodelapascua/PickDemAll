package com.gdx.pickdem.util;

import com.badlogic.gdx.graphics.Camera;
import com.gdx.pickdem.Level;
import com.gdx.pickdem.entity.Robot;
public class ChaseCam {

    public Camera camera;
    private Robot target;
    private Level level;

    public ChaseCam(Camera camera, Robot target, Level level) {
        this.camera = camera;
        this.target = target;
        this.level = level;
    }

    public void update() {
        if(target.position.x < Constants.WORLD_SIZE/2)
            camera.position.x = Constants.WORLD_SIZE/2;
        else if (target.position.x > level.getMaxX()-Constants.WORLD_SIZE/2)
            camera.position.x = level.getMaxX()-Constants.WORLD_SIZE/2;
        else
            camera.position.x = target.position.x;

        if(target.position.y - Constants.ROBOT_EYE_HEIGHT < 0)
            camera.position.y = Constants.ROBOT_EYE_HEIGHT + 40;
        else
            camera.position.y = target.position.y+40;
    }
}