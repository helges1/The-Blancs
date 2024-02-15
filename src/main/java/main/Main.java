package main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import model.TheBlancsGame;

public class Main {

	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("TheBlancs");
		config.setWindowedMode(1280, 920);
		new Lwjgl3Application(new TheBlancsGame(), config);
	}

}
