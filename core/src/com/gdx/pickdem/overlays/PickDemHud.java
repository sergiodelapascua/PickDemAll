package com.gdx.pickdem.overlays;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Constants;
import com.gdx.pickdem.util.Utils;

public class PickDemHud {

    public final Viewport viewport;
    final BitmapFont font;
    float levelStart;

    public PickDemHud() {
        levelStart = TimeUtils.nanoTime();
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        font = new BitmapFont();
        font.getData().setScale(1);
    }

    public void render(SpriteBatch batch, int coins, int segundos) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        final String hudString ="Time remaining: "+ segundos;

        font.draw(batch, hudString, Constants.HUD_MARGIN, viewport.getWorldHeight() - Constants.HUD_MARGIN);

        final TextureRegion coin = Assets.instance.coinAssets.coin;
        int cont = Constants.COINS - coins;
        for (int i = 1; i <= cont; i++) {
            final Vector2 drawPosition = new Vector2(
                    viewport.getWorldWidth() - i * (Constants.HUD_MARGIN / 2 + (coin.getRegionWidth()*0.2f)),
                    viewport.getWorldHeight() - Constants.HUD_MARGIN - (coin.getRegionHeight()*0.2f)
            );
            Utils.drawTextureRegion(
                    batch,
                    coin,
                    drawPosition.x,
                    drawPosition.y
            );
        }

        batch.end();
    }
}
