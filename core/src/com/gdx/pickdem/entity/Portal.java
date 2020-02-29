package com.gdx.pickdem.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Utils;

public class Portal {
    final public Vector2 position;

    public Portal(Vector2 position) {
        this.position = position;
    }

    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.portalAssets.portalAnimation.getKeyFrame(Utils.secondsSince(0));
        Utils.drawMirrorTextureRegion(batch, region, position, true);
    }
}