package com.gdx.pickdem.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Constants;
import com.gdx.pickdem.util.Utils;

public class RedFirework extends Explosion {

    public RedFirework(Vector2 position) {
        super(position);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!isFinished() && !yetToStart()) {
            Utils.drawTextureRegion(
                    batch,
                    Assets.instance.redFireworkAssets.explosion.getKeyFrame(Utils.secondsSince(getStartTime()) - offset),
                    super.getPositionX() - Constants.EXPLOSION_CENTER.x,
                    super.getPositionY() - Constants.EXPLOSION_CENTER.y
            );
        }
    }
}
