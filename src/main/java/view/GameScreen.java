package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import controller.ShipController;
import model.GameModel;

public class GameScreen implements Screen {
	
	private SpriteBatch batch;
	private GameModel gameModel;
	private ShipController shipController;
	private Texture backGround;
	//private Ship shipTexture;
	
	public GameScreen() {
		batch = new SpriteBatch();
		gameModel = new GameModel();
		shipController = new ShipController(gameModel.getShip());
		Gdx.input.setInputProcessor(shipController); // Set the shipController as the input processor
	    backGround = new Texture("pictures/background.png");
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
		batch.begin();
		
		//Draws the background and the ship
		batch.draw(backGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameModel.getShip().draw(batch);
		
		batch.end();
	}
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	    backGround.dispose();
		batch.dispose();
		
	}

}
