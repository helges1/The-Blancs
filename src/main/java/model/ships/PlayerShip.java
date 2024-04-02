package model.ships;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.Laser;

public class PlayerShip extends Ship {
	
	private final TextureRegion playerLaserTexture;
	
	private static final float playerHealth = 100;
	private static final float playerSpeed = 200;
	private static final float playerWhidth = 40;
	private static final float playerHeight = 40;
	
	private static final float playerLaserSpeed = 400;
	private static final float playerFireRate = 0.3f;

	public PlayerShip(TextureRegion playerShipTexture, TextureRegion playerLaserTexture, float x, float y, FitViewport viewport) {
		
		super(playerShipTexture, x, y, playerWhidth, playerHeight,
				playerSpeed, playerHealth, viewport);
		
		this.playerLaserTexture = playerLaserTexture;
	}

	@Override
	public Laser fireLaser(List<Laser> playerLasers) {
		Laser laser = null;
		if (timeSinceLaserFired >= playerFireRate) {
			if (isGunUpgraded()) { // Check if the gun is upgraded

				// Shoot three lasers in a burst with spacing
				Vector2 position = getNosePositionOfShip();
				float angle = getRotation();
				float spacing = 50;

				for (int i = 0; i < 3; i++) {
					// Calculate offset for each laser to spread out from the center
					float offsetDistance = (i - 1) * spacing; 
					
					// Calculate the offset vector based on the ship's angle
					// This rotates the offset to be perpendicular to the ship's forward direction
					float offsetAngleRadians = (float)Math.toRadians(getRotation() + 90); // +90 to make it perpendicular
					Vector2 offset = new Vector2((float)Math.cos(offsetAngleRadians) * offsetDistance,
												 (float)Math.sin(offsetAngleRadians) * offsetDistance);
				
					// Apply the offset to the ship's nose position to get the starting position for each laser
					position = new Vector2(getNosePositionOfShip()).add(offset);
					
					angle = getRotation(); 
					laser = new Laser(playerLaserTexture, position, playerLaserSpeed, angle);
					playerLasers.add(laser);
				}
				
	
			} else {
				// Shoot a single laser
				Vector2 position = getNosePositionOfShip();
				float angle = getRotation(); 
				laser = new Laser(playerLaserTexture, position, playerLaserSpeed, angle);
				playerLasers.add(laser);
			}
			timeSinceLaserFired = 0;
		}
		return laser;
	}
}
