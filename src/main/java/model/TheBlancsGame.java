package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import view.GameScreen;
import view.Homescreen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import controller.EnemyShipController;
import controller.PlayerShipController;

public class TheBlancsGame extends Game {
	
	private GameModel gameModel;
	private GameScreen gameScreen;
	public SpriteBatch batch;


	@Override
	public void create() {
		OrthographicCamera camera = new OrthographicCamera(); // Initialize the camera
		FitViewport viewport = new FitViewport(GameModel.WORLD_WIDTH, GameModel.WORLD_HEIGHT, camera);
		batch = new SpriteBatch();
		
		//Textures
		Texture playerShip = new Texture("pictures/playerShip.png"); 
		Texture playerLaser = new Texture("pictures/playerLaser.png"); 
		Texture enemyShip = new Texture("pictures/enemyShip.png"); 
		Texture enemyLaser = new Texture("pictures/enemyLaser.png");
		Sound laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3"));
		
		gameModel = new GameModel(playerShip, playerLaser, enemyShip, enemyLaser, laserSound, viewport, 1, 5);
		PlayerShipController playerShipController = new PlayerShipController(gameModel);
		EnemyShipController enemyShipController = new EnemyShipController(gameModel);
		Gdx.input.setInputProcessor(playerShipController);
		gameScreen = new GameScreen(gameModel, playerShipController, enemyShipController, batch, camera, viewport);
		
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
