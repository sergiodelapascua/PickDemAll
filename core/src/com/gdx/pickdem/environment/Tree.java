package com.gdx.pickdem.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gdx.pickdem.util.Assets;
import com.gdx.pickdem.util.Utils;

public class Tree extends Environment{

    public Tree(Vector2 position) {
        this.position = position;
    }

    @Override
    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.treeAssets.tree;
        Utils.drawCoinTextureRegion(batch, region, position.x,position.y);
    }
}