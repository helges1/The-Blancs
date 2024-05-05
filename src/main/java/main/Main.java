package main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.badlogic.gdx.Game;

import model.TheBlancsGame;




/**
 * The main entry point for the application. This class is responsible for setting up
 * the application configuration and launching the game.
 */
public class Main extends Game {

	/*
	 * This is the main method of the application.
	 * It creates a new Lwjgl3ApplicationConfiguration object and sets the configuration for the application.
	 *  It sets the title of the application, the window size, and the foreground FPS.
	 * It then creates a new Lwjgl3Application object with the TheBlancsGame class and the configuration object as parameters.
	 * @param args the command line arguments
	 */


	/**
	 * The main method of the application. It creates a new Lwjgl3ApplicationConfiguration object and sets the configuration for the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("TheBlancs");
		config.setWindowedMode(1200, 900);
		config.setWindowIcon("pictures/game-icon.png");
		new Lwjgl3Application(new TheBlancsGame(), config);
	}

	/**
	 * this method is called when the application is created. Currently, it does nothing.
	 */
	@Override
	public void create() {
		return;
	}

}
