package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import view.GameOverScreen;
import view.GameScreen;
import view.HelpScreen;
import view.HomeScreen;
import view.ScreenType;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;

import controller.EnemyShipController;
import controller.PlayerShipController;

/*
 * TheBlancsGame class is the main class of the game. It extends the Game class from the libGDX library.
 * It is responsible for creating the game model, screens, and controllers, and updating the active screen.
 */
public class TheBlancsGame extends Game {
	
	private GameModel gameModel;
	private GameScreen gameScreen;
    private HomeScreen homeScreen;
	private GameOverScreen gameOverScreen;
	private HelpScreen helpScreen;
	private String userName;
	public SpriteBatch batch;
	private ScreenType activeScreen = ScreenType.HOME_SCREEN;
	private PlayerShipController playerShipController;
	private int finalScore = 0;

	@Override
	public void create() {
		OrthographicCamera camera = new OrthographicCamera(); // Initialize the camera
		FitViewport viewport = new FitViewport(GameModel.WORLD_WIDTH, GameModel.WORLD_HEIGHT, camera);
		batch = new SpriteBatch();
		TextureAtlas atlas = new TextureAtlas("pictures/TheBlancsTextureAtlas.atlas");
		Sound laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3"));
		
        // Model
		gameModel = new GameModel(atlas, laserSound, viewport, userName);
		
        // Controllers
        this.playerShipController = new PlayerShipController(gameModel);

		EnemyShipController enemyShipController = new EnemyShipController(gameModel);
		Gdx.input.setInputProcessor(playerShipController);
        
        // Screens
		gameScreen = new GameScreen(gameModel, playerShipController, enemyShipController, batch, camera, viewport);
		homeScreen = new HomeScreen(this, atlas);
		gameOverScreen = new GameOverScreen(this, atlas);
		helpScreen = new HelpScreen(this, atlas);
		
		
        updateScreen();
	}

    private void updateScreen() {
        switch (activeScreen) {
            case HOME_SCREEN:
                setScreen(homeScreen);
                break;
			case HELP_SCREEN:
				setScreen(helpScreen);
				break;
            case GAME_SCREEN:
                setScreen(gameScreen);
                break;
            case GAME_OVER_SCREEN:
                setScreen(gameOverScreen);
                break;
            default:
                break;
        }
    }
	
	
	@Override
	public void render() {
		super.render();
		if (gameModel.isGameOver() && activeScreen != ScreenType.GAME_OVER_SCREEN) {
			finalScore = gameModel.getScore();  // Get the final score from the game model
			gameOverScreen.setFinalScore(finalScore);
			activeScreen = ScreenType.GAME_OVER_SCREEN;
			updateScreen();  // Update screen will handle setting the game over screen
		}

	}



	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	/*
	 * This method is used to set the active screen of the game.
	 * @param screenType The screen type to set as the active screen
	 */
	public void setScreenType(ScreenType screenType) {
		this.activeScreen = screenType;
		if (userName != null) {
			gameModel.setUserName(userName);
		}
        updateScreen();
	}

	/**
	 * This method is used to get the active screen of the game.
	 * @return The active screen of the game
	 */
	public ScreenType getScreenType() {
		return activeScreen;
	}

	/**
	 * This method is used to get the player controller of the game.
	 * @return The player controller of the game
	 */
	public PlayerShipController getPlayerController() {
		return playerShipController;
	}

	/**
	 * This method is used to set the user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * This method is used to get the game model of the game.
	 * @return The game model of the game
	 */
	public GameModel getGameModel() {
		return gameModel;
	}

	/**
	 * This method is used to get the user name
	 */
	public String getUserName() {
		return userName;
	}


}
