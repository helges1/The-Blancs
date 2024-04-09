package main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.badlogic.gdx.Game;
import view.HomeScreen;

import model.TheBlancsGame;

public class Main extends Game {

	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("TheBlancs");
		config.setWindowedMode(1280, 920);
		new Lwjgl3Application(new TheBlancsGame(), config);
	}

	@Override
	public void create() {
		//TODO
	}

}
