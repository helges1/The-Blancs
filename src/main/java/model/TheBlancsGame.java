package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import view.GameOverScreen;
import view.GameScreen;
import view.HomeScreen;
import view.ScreenType;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;

import controller.EnemyShipController;
import controller.PlayerShipController;

public class TheBlancsGame extends Game {
	
	private GameModel gameModel;
	private GameScreen gameScreen;
    private HomeScreen homeScreen;
	private GameOverScreen gameOverScreen;
	private String userName;
	public SpriteBatch batch;
	private ScreenType activeScreen = ScreenType.HOME_SCREEN;
	private PlayerShipController playerShipController;


	@Override
	public void create() {
		OrthographicCamera camera = new OrthographicCamera(); // Initialize the camera
		FitViewport viewport = new FitViewport(GameModel.WORLD_WIDTH, GameModel.WORLD_HEIGHT, camera);
		batch = new SpriteBatch();
		//Textures
//		Texture playerShip = new Texture("pictures/playerShip.png"); 
//		Texture playerLaser = new Texture("pictures/playerLaser.png"); 
//		Texture enemyShip = new Texture("pictures/basicEnemyShip.png"); 
//		Texture enemyLaser = new Texture("pictures/enemyLaser.png");
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
		homeScreen = new HomeScreen(this);
		gameOverScreen = new GameOverScreen(this);
		
        updateScreen();
	}

    private void updateScreen() {
        switch (activeScreen) {
            case HOME_SCREEN:
                setScreen(homeScreen);
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
	}
	
	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
	
	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	public void setScreenType(ScreenType screenType) {
		this.activeScreen = screenType;
		if (userName != null) {
			gameModel.setUserName(userName);
		}
        updateScreen();
	}
    

	public ScreenType getScreenType() {
		return activeScreen;
	}

	public PlayerShipController getPlayerController() {
		return playerShipController;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
