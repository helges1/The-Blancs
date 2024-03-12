package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.viewport.*;

import controller.ShipController;
import model.GameModel;
import model.Laser;
import model.Ship;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private GameModel gameModel;
	private ShipController shipController;
	private Texture background;
	private FitViewport viewport; // Add the viewport
	private OrthographicCamera camera; // Camera for the viewport

	public GameScreen() {
		viewport = new FitViewport(800, 600);
		batch = new SpriteBatch();
		gameModel = new GameModel(); // Assuming GameModel doesn't need the viewport in its constructor
		shipController = new ShipController(gameModel.getShip());
		Gdx.input.setInputProcessor(shipController);
		background = new Texture("pictures/background.png");

		camera = new OrthographicCamera(); // Initialize the camera
		viewport = new FitViewport(800, 600, camera); // Initialize the viewport with desired world width and height

		// If Ship needs the viewport, set it here after creation
		gameModel.getShip().setViewport(viewport); // You would need to add a setViewport method to your Ship class
	}

	@Override
	public void show() {
		// Implementation remains the same
	}



	@Override
	public void render(float delta) {
		gameModel.updateModel(delta); // Update the game model
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shipController.update(delta);

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();

		camera.update(); // Update the camera
		batch.setProjectionMatrix(camera.combined); // Set the SpriteBatch to use the camera's view and projection matrices

		// Draw the background
		batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());


		// Draw each laser
		for (Laser laser : gameModel.getLasers()) {
			laser.update(delta); // Update the laser's position
			laser.draw(batch);
		}

		// Draw each enemy ship
		for (Ship enemyShip : gameModel.getEnemyShips()) {
			enemyShip.draw(batch);
		}

		// Draw the ship
		gameModel.getShip().draw(batch);

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true); // Update the viewport on resize, centering the camera
	}

	@Override
	public void pause() {
		// Implementation remains the same
	}

	@Override
	public void resume() {
		// Implementation remains the same
	}

	@Override
	public void hide() {
		// Implementation remains the same
	}

	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
	}
}