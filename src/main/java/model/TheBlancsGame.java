package model;

import com.badlogic.gdx.Game;

import view.GameScreen;

public class TheBlancsGame extends Game {
	
	private GameScreen gameScreen;

	@Override
	public void create() {
		gameScreen = new GameScreen();
		super.setScreen(gameScreen);
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
	
	@Override
	public void dispose() {
		gameScreen.dispose();
	}

}
