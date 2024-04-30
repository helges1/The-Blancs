package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

import controller.EnemyShipController;
import controller.PlayerShipController;

import model.GameModel;
import model.Laser;
import model.PowerUps;
import model.ScoreManager;
import model.TheBlancsGame;
import model.PowerUps.PowerUpType;
import model.ships.Explosion;
import model.ships.Ship;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private GameModel gameModel;
	private PlayerShipController playerShipController;
	private EnemyShipController enemyShipController;
	private Texture background;
	private Texture shieldTexture;
	private FitViewport viewport; 
	private OrthographicCamera camera; 
	private TheBlancsGame game;
	private Music backgroundMusic;

	private float backgroundOffset; // The Y-offset of the background
	private final float backgroundScrollSpeed = 300;

	// Blast
	private TextureRegion[] blastFrames; // Array to hold the blast frames
	private float animationTime = 0; // Time since the animation started

	private int currentScore = 0;

	// HUD
	BitmapFont font;
	float hudVerticalMargin;
	float hudLeftX;
	float hudRightX;
	float hudCentreX;
	float hudRow1Y;
	float hudRow2Y;
	float hudRow3Y;
	float hudSectionWidth;

	// ShapeRenderer for drawing health bar
	private ShapeRenderer shapeRenderer;
    
	/**
     * Constructs the GameScreen with necessary dependencies.
     * 
     * @param gameModel The central model managing game logic and state.
     * @param playerShipController Controls player actions and interactions.
     * @param enemyShipController Controls enemy actions and interactions.
     * @param batch The SpriteBatch used to draw all textures.
     * @param camera The OrthographicCamera providing a view of the game world.
     * @param viewport Manages how the game world is scaled and shown on different screens.
     */
	public GameScreen(GameModel gameModel, PlayerShipController playerShipController,
			EnemyShipController enemyShipController,
			SpriteBatch batch, OrthographicCamera camera, FitViewport viewport) {

		this.batch = batch;
		this.camera = camera;
		this.gameModel = gameModel;
		this.game = new TheBlancsGame();

		this.playerShipController = playerShipController;
		this.enemyShipController = enemyShipController;
		
		// Load the blast frames
		createBlastFrames();
		
		// Load the background and shield textures
		background = new Texture("pictures/background.png");
		shieldTexture = new Texture("pictures/shield.png");

		// Load the background music
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/throughSpace.ogg"));
		backgroundMusic.setLooping(true);

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
		hudRow3Y = hudRow2Y - hudVerticalMargin - font.getCapHeight();
		hudSectionWidth = viewport.getWorldWidth() / 3;
		backgroundOffset = 0;
	}
    
	/**
     * Called when this screen becomes the current screen for the game.
     */
	@Override
	public void show() {
		backgroundMusic.play();
		ScoreManager.loadHighScore();
	}

    /**
     * Draws the game's HUD, showing scores, health, and power-up status.
     */
	private void drawHUD() {
		batch.begin();
	    
		// Draws high score in its orginal posistion
		int highScore = ScoreManager.getHighScore();
		String highScoreUser = ScoreManager.getHighScoreUser();
		font.draw(batch, "High Score: " + highScore + " by " + highScoreUser, hudLeftX, hudRow1Y);
	
		// Draw the current score in its original position
		String scoreText = "Score: " + currentScore;
		font.draw(batch, scoreText, hudLeftX, hudRow2Y);

		// Assuming health to be 100
		String healthText = "Health: " + (int) gameModel.getPlayerShip().getHealth();
		// Calculate the position for the health bar based on the text size
		float textWidth = font.getSpaceXadvance() * healthText.length();
		float healthBarX = hudLeftX + textWidth + 30; // 30 pixels padding from text
		float healthBarY = hudRow3Y - 11; // 11 pixels padding from text

		// Draw the health text
		font.draw(batch, healthText, hudLeftX, hudRow3Y);
		batch.end();

		// Draw the health bar
		float healthPercentage = gameModel.getPlayerShip().getHealth() / 100f; // Assuming health to always be 100 for now

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
		if (gameModel.getPlayerShip().getActivePowerUp() != null) {
			PowerUpType powerUpType = gameModel.getPlayerShip().getActivePowerUp();
			TextureRegion powerUpTexture = gameModel.getPowerUpTexture(powerUpType);
			String powerUpText = powerUpType.getPowerUpName();
		
			// x position for the powerUpText
			float powerUpTextX = hudRightX - font.getSpaceXadvance() * powerUpText.length() * 1.75f;
			
			// Draw the powerUpText at calculated position
			font.draw(batch, powerUpText, powerUpTextX, hudRow1Y);
			
			// x and y position for the powerUpTexture
			float powerUpTextureX = hudRightX - powerUpTexture.getRegionWidth();
			float powerUpTextureY = hudRow1Y - powerUpTexture.getRegionHeight() - 7.5f; // Slightly above the row
		
			// Draw the powerUpTexture at calculated position
			batch.draw(powerUpTexture, powerUpTextureX, powerUpTextureY);
		
			// Draw the powerUp timer text below the powerUpText
			String powerUpTimerText = String.valueOf((int) gameModel.getPlayerShip().getPowerUpTimer());
			float powerUpTimerTextX = hudRightX - font.getSpaceXadvance() * powerUpTimerText.length();
			font.draw(batch, powerUpTimerText, powerUpTimerTextX, hudRow2Y - 7.5f);
		}		

		batch.end();
	}

	/**
     * Renders the game world, including the HUD, player, enemies, and projectiles.
     * 
     * @param delta Time since last frame in seconds.
     */
	@Override
	public void render(float delta) {
		gameModel.updateModel(delta); // Update the game model
		currentScore = gameModel.getScore(); // Updates the score based on the destroyed enemy ships// Reset the destroyed enemy ships count
		if (currentScore > ScoreManager.getHighScore()){
			ScoreManager.setHighScore(currentScore, gameModel.getUserName());
		}
		if (gameModel.isGameOver()){
			game.setScreenType(ScreenType.GAME_OVER_SCREEN);
			return;
		}
		

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
			laser.draw(batch);
		}

		// Draw each Asteroide
		for (model.Asteroid Asteroid : gameModel.getAsteroids()) {
			Asteroid.draw(batch);
		}

		// Draw each laser fired by the enemies
		for (Laser laser : gameModel.getEnemyLasers()) {
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

		for (Explosion explosion : gameModel.getExplosions()) {
			explosion.draw(batch);
		}

		// Draw the shield if it is activated
		if (gameModel.getPlayerShip().getActivePowerUp() == PowerUpType.SHIELD) {
			batch.draw(shieldTexture, gameModel.getPlayerShip().getX() - (gameModel.getPlayerShip().getWidth() / 2),
					gameModel.getPlayerShip().getY() - (gameModel.getPlayerShip().getHeight() / 2),
					gameModel.getPlayerShip().getWidth() * 2f, gameModel.getPlayerShip().getHeight() * 2f);
		}

		// Draw the blast if the BLAST power-up is active
		if (gameModel.getPlayerShip().getActivePowerUp() == PowerUpType.BLAST) {
			// Update the animation time
			animationTime += delta;

			// Draw the blast
			drawBlast();
		} else {
			// Reset the animation time if the BLAST power-up is not active
			animationTime = 0;
		}

		// Draw the ship
		gameModel.getPlayerShip().draw(batch);

		// Update the game model
		gameModel.update();

		batch.end();

		// Draw the HUD
		drawHUD();
	}

    /**
     * Draws the blast effect when the player uses the blast power-up.
     */
	private void drawBlast() {
		// Calculate the frame index
		int frameRate = 10;
		int frameIndex = (int) (animationTime * frameRate) % blastFrames.length;

		// Calculate the position to draw the blast
		TextureRegion currentFrame = blastFrames[frameIndex];
		float scale = 2f; // 
		float scaledWidth = currentFrame.getRegionWidth() * scale;
		float scaledHeight = currentFrame.getRegionHeight() * scale;
		float blastX = gameModel.getPlayerShip().getX() + gameModel.getPlayerShip().getWidth() / 2 - scaledWidth / 2;
		float blastY = gameModel.getPlayerShip().getY() + gameModel.getPlayerShip().getHeight() / 2 - scaledHeight / 2;

		// Draw the current frame 
		batch.draw(currentFrame, blastX, blastY, scaledWidth, scaledHeight);
	}

    /**
     * Creates frames for the blast animation from a texture sheet.
     */
	private void createBlastFrames() {
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
	}

    /**
     * Called when the game screen size changes.
     * 
     * @param width New width of the screen.
     * @param height New height of the screen.
     */
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true); // Update the viewport on resize, centering the camera
	}
    
    /**
     * Called when the game is paused.
     */
	@Override
	public void pause() {
		// Implementation remains the same
	}
    
	/**
     * Called when the game resumes from a paused state.
     */
	@Override
	public void resume() {
		// Implementation remains the same
	}
	
    /**
     * Called when the game screen is no longer the current screen.
     */
	@Override
	public void hide() {
		backgroundMusic.pause(); // Pause the background music when the screen is hidden
	}
    
    /**
     * Frees up resources when this screen is destroyed.
     */
	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
		shapeRenderer.dispose();
		backgroundMusic.dispose();
	}
}