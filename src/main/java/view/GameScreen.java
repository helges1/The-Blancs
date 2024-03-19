package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.viewport.*;

import controller.EnemyShipController;
import controller.ShipController;
import model.GameModel;
import model.Laser;
import model.Ship;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private GameModel gameModel;
	private ShipController shipController;
	private EnemyShipController enemyShipController;
	private Texture background;
	private FitViewport viewport; // Add the viewport
	private OrthographicCamera camera; // Camera for the viewport

	private int score = 0;

	// HUD 
	BitmapFont font;
	float hudVerticalMargin;
	float hudLeftX;
	float hudRightX;
	float hudCentreX;
	float hudRow1Y;
	float hudRow2Y;
	float hudSectionWidth;
    
	// ShapeRenderer for drawing health bar
	private ShapeRenderer shapeRenderer;


	public GameScreen() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(); // Initialize the camera
		gameModel = new GameModel(camera); // Assuming GameModel doesn't need the viewport in its constructor
		shipController = new ShipController(gameModel);
		enemyShipController = new EnemyShipController(gameModel.getEnemyShips(), gameModel.getShip());
		Gdx.input.setInputProcessor(shipController);
		background = new Texture("pictures/background.png");
        shapeRenderer = new ShapeRenderer();
		viewport = gameModel.getViewport(); // Initialize the viewport with desired world width and height

		// If Ship needs the viewport, set it here after creation
		gameModel.getShip().setViewport(viewport); // You would need to add a setViewport method to your Ship class

		// HUD Initialization
		font = new BitmapFont();
		hudVerticalMargin = 10;
		hudLeftX = 10;
		hudRightX = viewport.getWorldWidth() - hudLeftX;
		hudCentreX = viewport.getWorldWidth() / 2;
		hudRow1Y = viewport.getWorldHeight() - hudVerticalMargin;
		hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
		hudSectionWidth = viewport.getWorldWidth() / 3;
	}

	@Override
	public void show() {
		// Implementation remains the same
	}

	private void drawHUD() {
		batch.begin();
		// Draw the score on the left
		font.draw(batch, "Score: " + score, hudLeftX, hudRow1Y);
    
		// Assuming health to be 100
		String healthText = "Health: " + 100;
		// Calculate the position for the health bar based on the text size
		float textWidth = font.getSpaceXadvance() * healthText.length();
		float healthBarX = hudLeftX + textWidth + 30; // 20 pixels padding from text
		float healthBarY = hudRow2Y - 11; // 11 pixels padding from text
		
		// Draw the health text
		font.draw(batch, healthText, hudLeftX, hudRow2Y);
		batch.end();
		
		// Draw the health bar
		float healthPercentage = 100f / 100f; // Assuming health to always be 100 for now

        Color healthBarColor = Color.GREEN; // Default to green
        if (healthPercentage > 0.66f) {
            healthBarColor = Color.GREEN;
        } else if (healthPercentage > 0.33f) {
            healthBarColor = Color.ORANGE;
        } else {
            healthBarColor = Color.RED;
    }
        // Set the projection matrix for the shape renderer
        shapeRenderer.setProjectionMatrix(camera.combined);
		// Begin the shape renderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		// Set the color for the health bar
        shapeRenderer.setColor(healthBarColor);
		// Draws the health bar with the calculated percentage
        shapeRenderer.rect(healthBarX, healthBarY, 100 * healthPercentage, 10);
        shapeRenderer.end();
		

	}
	
	@Override
	public void render(float delta) {
		gameModel.updateModel(delta); // Update the game model
		score += gameModel.getDestroyedEnemyShipsCount() * 10; // Updates the score based on the destroyed enemy ships
		gameModel.resetDestroyedEnemyShipsCount(); // Reset the destroyed enemy ships count
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shipController.update(delta);
		enemyShipController.update(delta);


		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();

		camera.update(); // Update the camera
		batch.setProjectionMatrix(camera.combined); // Set the SpriteBatch to use the camera's view and projection matrices

		// Draw the background
		batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());


		// Draw each laser
		for (Laser laser : gameModel.getPlayerLasers()) {
			laser.update(delta); // Update the laser's position
			laser.draw(batch);
		}

		// Draw each enemy ship
		for (Ship enemyShip : gameModel.getEnemyShips()) {
			enemyShip.draw(batch);
		}

		// Draw the ship
		gameModel.getShip().draw(batch);
		gameModel.update();
		batch.end();

		// Draw the HUD
		drawHUD();
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
		shapeRenderer.dispose();
	}
}