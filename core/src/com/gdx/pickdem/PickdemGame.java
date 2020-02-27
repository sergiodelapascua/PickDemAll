package com.gdx.pickdem;

import com.badlogic.gdx.Game;

public class PickdemGame extends Game {
	@Override
	public void create () {
		setScreen(new GameplayScreen());
	}
}
