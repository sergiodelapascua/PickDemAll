package com.gdx.pickdem.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final int TIME = 100;
    //public

    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final float WORLD_SIZE = 140;
    public static final float MENU_SIZE = 480;
    public static final int COINS = 6;
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
    public static final float OWL_MOVE_SPEED_X_MOBILE = WORLD_SIZE/6;
    public static final float OWL_MOVE_SPEED_Y = WORLD_SIZE/8;

    public static final float JUMP_SPEED = WORLD_SIZE/2;
    public static final float MAX_JUMP_DURATION = .2f;
    public static final float GRAVITY = WORLD_SIZE;
    public static final float HUD_VIEWPORT_SIZE = 480;
    public static final float HUD_MARGIN = 20;

    public static final Vector2 EXPLOSION_CENTER = new Vector2(8, 8);
    public static final float OVERLAY_DURATION = 0.5f;
    public static final float LEVEL_END_DURATION = 5;
    public static final String VICTORY_MESSAGE = "VICTORY!";
    public static final String GAME_OVER_MESSAGE = "GAME OVER";
    public static final int OVERLAY_COUNT =500;
    public static final int ENEMY_COUNT =1000;
    public static final String FONT_FILE = "font/header.fnt";

    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 200;
    public static final String MOVE_LEFT_BUTTON = "left";
    public static final String MOVE_RIGHT_BUTTON = "right";
    public static final String SHOOT_BUTTON = "down";
    public static final String JUMP_BUTTON = "up";
    public static final Vector2 BUTTON_CENTER = new Vector2(16, 16);
    public static final float BUTTON_RADIUS = 32;
}