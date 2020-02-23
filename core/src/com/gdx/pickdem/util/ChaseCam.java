package com.gdx.pickdem.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
        camera.position.y = target.position.y+40;
    }
}