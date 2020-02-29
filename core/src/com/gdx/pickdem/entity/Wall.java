package com.gdx.pickdem.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Utils;

public class Wall {

    float endOfMap;

    public Wall(float f){ endOfMap = f; }

    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.wallAssets.wall;
        for (int i = -48; i <= 480; i=i +48) {
            Utils.drawWallTextureRegion(batch, region, -48, i);
        }
        for (int i = -48; i <= 480; i=i +48) {
            Utils.drawWallTextureRegion(batch, region, endOfMap, i);
        }
    }
}
