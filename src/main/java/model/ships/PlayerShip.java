package model.ships;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.lasers.Cannon;
import model.lasers.Laser;
import model.powerUps.PowerUps.PowerUpType;

public class PlayerShip extends Ship {
	
	private final TextureRegion playerLaserTexture;
	
	private static final float playerHealth = 100;
	private static final float playerSpeed = 200;
	private static final float playerWhidth = 40;
	private static final float playerHeight = 40;
	
	private static final float playerLaserSpeed = 400;
	private static final float playerLaserDamage = 10;
	private static final float playerLaserWidth = 20;
	private static final float playerLaserHeight = 30;
	private static final float playerFireRate = 0.3f;

	public PlayerShip(TextureRegion playerShipTexture, TextureRegion playerLaserTexture, float x, float y, FitViewport viewport) {
		
		super(playerShipTexture, x, y, playerWhidth, playerHeight,
				playerSpeed, playerHealth, playerFireRate, viewport);
		
		this.playerLaserTexture = playerLaserTexture;
		resetCannon(); // Set cannon
	}
	
//	private void fireUpgradedLasers(List<Laser> playerLasers) {
//		float angle1 = getRotation();
//		float angle2 = getRotation() + 25;
//		float angle3 = getRotation() - 25;
//		Vector2 position = getNosePositionOfShip();
//		Laser laser1 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle1, playerLaserWidth, playerLaserHeight);
//		laser1.centreAtPoint(position);
//		Laser laser2 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle2, playerLaserWidth, playerLaserHeight);
//		laser2.centreAtPoint(position);
//		Laser laser3 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle3, playerLaserWidth, playerLaserHeight);
//		laser3.centreAtPoint(position);
//		playerLasers.add(laser1);
//		playerLasers.add(laser2);
//		playerLasers.add(laser3);
//	}

	@Override
	void resetCannon() {
		this.cannon = () -> {
			Vector2 position = getNosePositionOfShip();
			float angle = getRotation();
			Laser laser = new Laser(playerLaserTexture, position, playerLaserSpeed,
					angle, playerLaserWidth, playerLaserHeight, playerLaserDamage);
			laser.centreAtPoint(position);
			return new Laser[]{laser};
		};
	}

	@Override
	void upgradeCannon() {
		this.cannon = () -> {
			Laser[] firedLasers = new Laser[3];
			float angle1 = getRotation();
			float angle2 = getRotation() + 25;
			float angle3 = getRotation() - 25;
			Vector2 position = getNosePositionOfShip();
			
			Laser laser1 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle1,
					playerLaserWidth, playerLaserHeight, playerLaserDamage);
			laser1.centreAtPoint(position);
			Laser laser2 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle2,
					playerLaserWidth, playerLaserHeight, playerLaserDamage);
			laser2.centreAtPoint(position);
			Laser laser3 = new Laser(playerLaserTexture, position, playerLaserSpeed, angle3,
					playerLaserWidth, playerLaserHeight, playerLaserDamage);
			laser3.centreAtPoint(position);
			
			firedLasers[0] = laser1;
			firedLasers[1] = laser2;
			firedLasers[2] = laser3;
			return firedLasers;
		};
	}

}