package model;

import com.badlogic.gdx.Game;
import view.GameScreen;
import view.Homescreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TheBlancsGame extends Game {
	
	private GameScreen gameScreen;
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
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
