package com.gdx.pickdem.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.pickdem.util.Assets;

public class Platform {
    public float top;
    float bottom;
    float left;
    float right;
    int height;
    int width;
    private String plataformName;


    public Platform(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
        //this.plataformName = name;
    }

    public void render(SpriteBatch batch) {
        final float width = right - left;
        final float height = top - bottom;
        Assets.instance.platformAssets.platformNinePatch.draw(batch, left-1, bottom-1, width+2, height+2);
    }

}
