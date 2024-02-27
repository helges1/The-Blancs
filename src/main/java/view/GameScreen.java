package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import controller.ShipController;
import model.Ship;

public class GameScreen implements Screen {
	
	private SpriteBatch batch;
	private Ship ship;
	private ShipController shipController;
	
	//private Ship shipTexture;
	
	public GameScreen() {
		batch = new SpriteBatch();
		ship = new Ship(new Texture("pictures/playerShip.png"));
		shipController = new ShipController(ship);
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
		batch.begin();
		
		ship.draw(batch);
		
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
		// TODO Auto-generated method stub
		
	}

}
