package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.viewport.*;

import controller.EnemyShipController;
import controller.PlayerShipController;
import model.GameModel;
import model.Laser;
import model.PowerUps;
import model.PowerUps.PowerUpType;
import model.ships.Ship;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private GameModel gameModel;
	private PlayerShipController playerShipController;
	private EnemyShipController enemyShipController;
	private Texture background;
	private Texture shieldTexture;
	private FitViewport viewport; // Add the viewport
	private OrthographicCamera camera; // Camera for the viewport

	private float backgroundOffset; // The Y-offset of the background
	private final float backgroundScrollSpeed = 300;

	// Blast
	private TextureRegion[] blastFrames; // Array to hold the blast frames
	private float animationTime = 0; // Time since the animation started

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

	public GameScreen(GameModel model, PlayerShipController playerShipController,
			EnemyShipController enemyShipController,
			SpriteBatch batch, OrthographicCamera camera, FitViewport viewport) {

		this.batch = batch;
		this.camera = camera;
		gameModel = model;

		this.playerShipController = playerShipController;

		this.enemyShipController = enemyShipController;

		// Load the sprite sheet for the blast
		Texture blastTextureSheet = new Texture("pictures/air-blast.png");

		final int FRAME_SIZE = 200; // Size of one frame
		final int FRAMES_PER_ROW = 5; // Number of frames per row

		blastFrames = new TextureRegion[FRAMES_PER_ROW * 2]; // Total frames

		// Load frames from the first row
		for (int i = 0; i < FRAMES_PER_ROW; i++) {
			blastFrames[i] = new TextureRegion(blastTextureSheet, i * FRAME_SIZE, 0, FRAME_SIZE, FRAME_SIZE);
		}
		// Load frames from the second row
		for (int i = 0; i < FRAMES_PER_ROW; i++) {
			blastFrames[i + FRAMES_PER_ROW] = new TextureRegion(blastTextureSheet, i * FRAME_SIZE, FRAME_SIZE,
					FRAME_SIZE, FRAME_SIZE);
		}

		background = new Texture("pictures/background.png");
		shieldTexture = new Texture("pictures/shield.png");
		shapeRenderer = new ShapeRenderer();
		this.viewport = viewport;

		// If Ship needs the viewport, set it here after creation
		// gameModel.getShip().setViewport(viewport); // You would need to add a
		// setViewport method to your Ship class

		// HUD Initialization
		font = new BitmapFont();
		hudVerticalMargin = 10;
		hudLeftX = 10;
		hudRightX = viewport.getWorldWidth() - hudLeftX;
		hudCentreX = viewport.getWorldWidth() / 2;
		hudRow1Y = viewport.getWorldHeight() - hudVerticalMargin;
		hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
		hudSectionWidth = viewport.getWorldWidth() / 3;
		backgroundOffset = 0;
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
		String healthText = "Health: " + (int) gameModel.getShip().getHealth();
		// Calculate the position for the health bar based on the text size
		float textWidth = font.getSpaceXadvance() * healthText.length();
		float healthBarX = hudLeftX + textWidth + 30; // 20 pixels padding from text
		float healthBarY = hudRow2Y - 11; // 11 pixels padding from text

		// Draw the health text
		font.draw(batch, healthText, hudLeftX, hudRow2Y);
		batch.end();

		// Draw the health bar
		float healthPercentage = gameModel.getShip().getHealth() / 100f; // Assuming health to always be 100 for now

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

		batch.begin();
		// Draw the powerup timer on the right
		if (gameModel.getShip().getActivePowerUp() != null) {
			PowerUpType powerUpType = gameModel.getShip().getActivePowerUp();
			Texture powerUpTexture = powerUpType.getPowerUpTexture();
			batch.draw(powerUpTexture, hudRightX - powerUpTexture.getWidth(), hudRow1Y - powerUpTexture.getHeight());
			String powerUpTimerText = String.valueOf((int) gameModel.getShip().getPowerUpTimer());
			font.draw(batch, powerUpTimerText, hudRightX - font.getSpaceXadvance() * powerUpTimerText.length(),
					hudRow2Y);
		}

		batch.end();
	}

	@Override
	public void render(float delta) {
		gameModel.updateModel(delta); // Update the game model
		score += gameModel.getDestroyedEnemyShipsCount() * 10; // Updates the score based on the destroyed enemy ships
		gameModel.resetDestroyedEnemyShipsCount(); // Reset the destroyed enemy ships count

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		playerShipController.update(delta);
		enemyShipController.update(delta);

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();

		camera.update(); // Update the camera
		batch.setProjectionMatrix(camera.combined); // Set the SpriteBatch to use the camera's view and projection
													// matrices

		// Scroll the background downwards by decreasing the offset
		backgroundOffset -= backgroundScrollSpeed * delta;
		if (backgroundOffset <= -background.getHeight()) {
			backgroundOffset = 0;
		}

		// Draw the background twice to cover the entire screen and loop
		float backgroundScale = viewport.getWorldWidth() / background.getWidth();
		float backgroundScaledHeight = background.getHeight() * backgroundScale;

		float backgroundY = backgroundOffset * backgroundScale;
		batch.draw(background, 0, backgroundY, viewport.getWorldWidth(), backgroundScaledHeight);
		batch.draw(background, 0, backgroundY + backgroundScaledHeight, viewport.getWorldWidth(),
				backgroundScaledHeight);

		// Draw each laser fired by the player
		for (Laser laser : gameModel.getPlayerLasers()) {
			laser.update(delta); // Update the laser's position
			laser.draw(batch);
		}

		// Draw each laser fired by the enemies
		for (Laser laser : gameModel.getEnemyLasers()) {
			laser.update(delta); // Update the laser's position
			laser.draw(batch);
		}

		// Draw each enemy ship
		for (Ship enemyShip : gameModel.getEnemyShips()) {
			enemyShip.draw(batch);
		}

		// Draw PowerUps
		for (PowerUps powerUp : gameModel.getPowerUps()) {
			powerUp.draw(batch);
		}

		// Draw the shield if it is activated
		if (gameModel.getShip().isShieldActivated()) {
			batch.draw(shieldTexture, gameModel.getShip().getX() - (gameModel.getShip().getWidth() / 2),
					gameModel.getShip().getY() - (gameModel.getShip().getHeight() / 2),
					gameModel.getShip().getWidth() * 2f, gameModel.getShip().getHeight() * 2f);
		}

		// Draw the blast if the BLAST power-up is active
		if (gameModel.getShip().getActivePowerUp() == PowerUpType.BLAST) {
			// Update the animation time
			animationTime += delta;

			// Calculate the frame index
			int frameRate = 10;
			int frameIndex = (int) (animationTime * frameRate) % blastFrames.length;

			// Calculate the position to draw the blast
			TextureRegion currentFrame = blastFrames[frameIndex];
			float scale = 2f; // 
			float scaledWidth = currentFrame.getRegionWidth() * scale;
			float scaledHeight = currentFrame.getRegionHeight() * scale;
			float blastX = gameModel.getShip().getX() + gameModel.getShip().getWidth() / 2 - scaledWidth / 2;
			float blastY = gameModel.getShip().getY() + gameModel.getShip().getHeight() / 2 - scaledHeight / 2;

			// Draw the current frame 
			batch.draw(currentFrame, blastX, blastY, scaledWidth, scaledHeight);
		} else {
			// Reset the animation time if the BLAST power-up is not active
			animationTime = 0;
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