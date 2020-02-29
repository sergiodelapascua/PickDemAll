package com.gdx.pickdem.util;

import com.badlogic.gdx.graphics.Camera;
import com.gdx.pickdem.entity.Robot;
public class ChaseCam {

    private Camera camera;
    private Robot target;

    public ChaseCam(Camera camera, Robot target) {
        this.camera = camera;
        this.target = target;
    }

    public void update() {
        camera.position.x = target.position.x;
        if(target.position.y - Constants.ROBOT_EYE_HEIGHT < 0)
            camera.position.y = Constants.ROBOT_EYE_HEIGHT + 40;
        else
            camera.position.y = target.position.y+40;
    }
}