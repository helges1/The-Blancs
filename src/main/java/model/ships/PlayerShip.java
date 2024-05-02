package model.ships;


import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.Laser;
import model.PowerUps.PowerUpType;

public class PlayerShip extends Ship {
	
	private final TextureRegion playerLaserTexture;
	
	private static final float playerHealth = 100;
	private static final float playerSpeed = 200;
	private static final float playerWhidth = 40;
	private static final float playerHeight = 40;
	
	private static final float playerLaserSpeed = 400;
	private static final float playerLaserWidth = 20;
	private static final float playerLaserHeight = 30;
	private static final float playerFireRate = 0.3f;



	public PlayerShip(TextureRegion playerShipTexture, TextureRegion playerLaserTexture, float x, float y, FitViewport viewport) {
		
		super(playerShipTexture, x, y, playerWhidth, playerHeight,
				playerSpeed, playerHealth, viewport);
		
		this.playerLaserTexture = playerLaserTexture;
	}

	@Override
	public boolean fireLaser(List<Laser> playerLasers) {
		Laser laser = null;
		if (timeSinceLaserFired >= playerFireRate) {
			// ...
			timeSinceLaserFired = 0;

			if (this.getActivePowerUp() == PowerUpType.GUN) {
				//Shoot three lasers separated by 25 degrees.
				fireUpgradedLasers(playerLasers);
				return true;
	
			} else {
				// Shoot a single laser
				Vector2 position = getNosePositionOfShip();
				float angle = getRotation();
				laser = new Laser(playerLaserTexture, position, playerLaserSpeed, angle, playerLaserWidth, playerLaserHeight);
				laser.centreAtPoint(position);
				playerLasers.add(laser);
				return true;
			}
			
		}
		return false;
	}
	
	private void fireUpgradedLasers(List<Laser> playerLasers) {
		float angle1 = getRotation();
		float angle2 = getRotation() + 25;
		float angle3 = getRotation() - 25;
		Vector2 position = getNosePositionOfShip();
		Laser laser1 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle1, playerLaserWidth, playerLaserHeight);
		laser1.centreAtPoint(position);
		Laser laser2 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle2, playerLaserWidth, playerLaserHeight);
		laser2.centreAtPoint(position);
		Laser laser3 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle3, playerLaserWidth, playerLaserHeight);
		laser3.centreAtPoint(position);
		playerLasers.add(laser1);
		playerLasers.add(laser2);
		playerLasers.add(laser3);
	}
	

}