package model.ships;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import model.Laser;

public class BasicEnemyShip extends Ship {
	
	public static final Random random = new Random();
	
	public static final Texture basicEnemyShipTexture = new Texture("pictures/enemyShip.png");
	public static final Texture basicEnemyLaserTexture = new Texture("pictures/enemyLaser.png");
	
	private static final float basicEnemyHealth = 10;
	private static final float basicEnemySpeed = 50;
	
	private static final float basicEnemyWidth = 40;
	private static final float basicEnemyHeight = 40;
	
	private static final float basicEnemyLaserSpeed = 450;
	private static final float basicEnemyFireRate = 1;
	

	
	/**
	 * Constructor for a basic enemy ship.
	 * 
	 * @param x float: the x position at which the ship should spawn.
	 * @param y float: the y position at which the ship should spawn.
	 * @param viewport FitViewport: the viewport of the game.
	 */
	public BasicEnemyShip(float x, float y, FitViewport viewport) {
		
		super(basicEnemyShipTexture, x, y, basicEnemyWidth, basicEnemyHeight,
				basicEnemySpeed, basicEnemyHealth, viewport);
	}


	@Override
	public Laser fireLaser() {
		Laser laser = null;
		if (timeSinceLaserFired >= basicEnemyFireRate) {
			if (random.nextInt(3) > 0) { // 66% chance that the ship chooses to fire laser
				Vector2 position = getNosePositionOfShip();
				float angle = getRotation();
				laser = new Laser(basicEnemyLaserTexture, position, basicEnemyLaserSpeed, angle);
				timeSinceLaserFired = 0;
			}
		}
		return laser;
	}
	

}
