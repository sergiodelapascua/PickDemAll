package com.gdx.pickdem.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Utils;

public class Water {

    float endOfMap;

    public Water(float f){ endOfMap = f; }

    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.waterAssets.water;
        for (int i = -0; i <= endOfMap + 16; i=i +16) {
            Utils.drawTextureRegion(batch, region, i, -16);
        }
    }
}
