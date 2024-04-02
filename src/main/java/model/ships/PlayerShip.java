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
	
	private static final float playerLaserSpeed = 600;
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
				float angle = getRotation() - 5f;
				float spacing = 50;

				for (int i = 0; i < 3; i++) {
					angle += 5f;
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
