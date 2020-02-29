package com.gdx.pickdem.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final float WORLD_SIZE = 140;
    public static final float COINS = 6;
    public static final float PORTAL_RADIUS = 12;
    public static final float PORTAL_HEIGHT = 50;
    public static final Vector2 COIN_CENTER = new Vector2(5, 6);
    public static final String TEXTURE_ATLAS = "images/pickdem.pack.atlas";
    public static final String STANDING_RIGHT = "Idle";
    public static final String JUMPING_RIGHT = "Jump(1)";
    public static final String WALKING_RIGHT = "Run(1)";

    public static final float WALK_LOOP_DURATION = 0.10f;
    public static final float JUMP_LOOP_DURATION = .05f;
    public static final float STAND_LOOP_DURTATION = 0.1f;
    public static final float OWL_LOOP_DURATION = 0.2f;
    public static final float PORTAL_LOOP_DURATION = 0.2f;

    public static final Vector2 ROBOT_EYE_POSITION = new Vector2(1, 16);
    public static final float ROBOT_EYE_HEIGHT = 16.0f;
    public static final float ROBOT_HEAD_HEIGHT = 4.0f;
    public static final float ROBOT_STANCE_WIDTH = 12.9f;
    public static final float OWL_STANCE_WIDTH = 15.6f;

    public static final float ROBOT_MOVE_SPEED = WORLD_SIZE/2;
    public static final float OWL_MOVE_SPEED_X = WORLD_SIZE/4;
    public static final float OWL_MOVE_SPEED_Y = WORLD_SIZE/8;

    public static final float JUMP_SPEED = WORLD_SIZE/2;
    public static final float MAX_JUMP_DURATION = .2f;
    public static final float GRAVITY = WORLD_SIZE;
}