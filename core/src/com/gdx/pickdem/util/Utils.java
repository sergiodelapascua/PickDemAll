package com.gdx.pickdem.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Utils {

    public static void drawRobotTextureRegion(SpriteBatch batch, TextureRegion region,Vector2 position, boolean mirror) {
        batch.draw(
                region.getTexture(),
                position.x,
                position.y - Constants.ROBOT_EYE_HEIGHT,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                0.05f,
                0.05f,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                mirror,
                false);
    }

    public static void drawOwlTextureRegion(SpriteBatch batch, TextureRegion region,Vector2 position, boolean mirror) {
        batch.draw(
                region.getTexture(),
                position.x,
                position.y - Constants.ROBOT_EYE_POSITION.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                0.2f,
                0.2f,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                mirror,
                false);
    }

    public static float secondsSince(long timeNanos) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeNanos);
    }
}
