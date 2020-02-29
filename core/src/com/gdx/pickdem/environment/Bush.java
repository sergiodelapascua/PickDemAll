package com.gdx.pickdem.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Utils;

public class Bush extends Environment{

    public Bush(Vector2 p) {
        position = p;
    }

    @Override
    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.bushAssets.arbusto;
        Utils.drawTextureRegion(batch, region, position.x,position.y);
    }
}
