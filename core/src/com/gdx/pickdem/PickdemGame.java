package com.gdx.pickdem;

import com.badlogic.gdx.Game;
import com.gdx.pickdem.overlays.MenuScreen;

public class PickdemGame extends Game {
	@Override
	public void create () {
		showMenuScreen();
	}


	public void showMenuScreen() {
		// TODO: Show the difficulty screen
		setScreen(new MenuScreen(this));
	}

	public void showGameScreen() {
		// TODO: Show the Icicles screen with the appropriate difficulty
		setScreen(new GameplayScreen(this));
	}
}
