package model.ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.lasers.Cannons;

/**
 * This class represent a standard ship the player can control.
 */
public class PlayerShip extends Ship {
	
	static final float playerHealth = 100;
	static final float playerSpeed = 200;
	static final float playerWhidth = 40;
	static final float playerHeight = 40;
	
	static final float playerLaserSpeed = 400;
	static final float playerLaserDamage = 10;
	static final float playerLaserWidth = 20;
	static final float playerLaserHeight = 30;
	static final float playerFireRate = 0.3f;

	/**
	 * Creates Ship according to how its defined in the PlayerShip-class.
	 * Must be given its own and its laser's textures as arguments.
	 * 
	 * @param playerShipTexture
	 * @param playerLaserTexture
	 * @param x the ships x-coordinate.
	 * @param y the ships y-coordinate.
	 * @param viewport
	 */
	PlayerShip(TextureRegion playerShipTexture, TextureRegion playerLaserTexture, float x, float y, FitViewport viewport) {
		
		super(playerShipTexture, playerLaserTexture, x, y, playerWhidth, playerHeight,
				playerSpeed, playerHealth, playerFireRate, playerLaserSpeed,
				playerLaserDamage, playerLaserWidth, playerLaserHeight, viewport);
		
		resetCannon(); // Set cannon
	}
	

	@Override
	void resetCannon() {
		this.cannon = Cannons::basicPlayerCannon;
	}

	@Override
	void upgradeCannon() {
		this.cannon = Cannons::upgradedPlayerCannon;
	}

}