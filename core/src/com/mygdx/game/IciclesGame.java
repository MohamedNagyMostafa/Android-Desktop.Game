package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.IciclesScreen;

public class IciclesGame extends Game implements com.mygdx.game.connections.ScreensConnection {

	@Override
	public void create() {
		openDifficultyScreen();
	}

	@Override
	public void openIciclesScreen(com.mygdx.game.helper.Difficulty difficulty) {
		setScreen(new IciclesScreen(this, difficulty));
	}

	@Override
	public void openDifficultyScreen() {
		setScreen(new com.mygdx.game.screens.DifficultyScreen(this));
	}


}
