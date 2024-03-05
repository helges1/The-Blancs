package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.viewport.*;

import controller.ShipController;
import model.GameModel;

public class GameScreen implements Screen {
	
	private SpriteBatch batch;
	private GameModel gameModel;
	private ShipController shipController;
	private FitViewport viewport;

	
	//private Ship shipTexture;
	
	public GameScreen() {
		viewport = new FitViewport(800, 600);
		batch = new SpriteBatch();
		gameModel = new GameModel();
		shipController = new ShipController(gameModel.getShip());
		Gdx.input.setInputProcessor(shipController); // Set the shipController as the input processor
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

		shipController.update(Gdx.graphics.getDeltaTime());

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		
		gameModel.getShip().draw(batch);
		
		
		batch.end();
	}
	

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	

}
