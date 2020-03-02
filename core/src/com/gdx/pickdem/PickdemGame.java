package com.gdx.pickdem;

import com.badlogic.gdx.Game;
import com.gdx.pickdem.overlays.MenuScreen;

public class PickdemGame extends Game {
	@Override
	public void create () {
		showMenuScreen();
	}


	public void showMenuScreen() {
		setScreen(new MenuScreen(this));
	}

	public void showGameScreen() {
		setScreen(new GameplayScreen(this));
	}
}
