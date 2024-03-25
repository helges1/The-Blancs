package model.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.Laser;

public class PlayerShip extends Ship {
	
	private static final Texture playerShipTexture = new Texture("pictures/playerShip.png");
	private static final Texture playerLaserTexture = new Texture("pictures/playerLaser.png");
	
	private static final float playerHealth = 100;
	private static final float playerSpeed = 200;
	private static final float playerWhidth = 40;
	private static final float playerHeight = 40;
	
	private static final float playerLaserSpeed = 600;
	private static final float playerFireRate = 0.3f;

	public PlayerShip(float x, float y, FitViewport viewport) {
		
		super(playerShipTexture, x, y, playerWhidth, playerHeight,
				playerSpeed, playerHealth, viewport);
	}

	@Override
	public Laser fireLaser() {
		Laser laser = null;
		if (timeSinceLaserFired >= playerFireRate) {
				Vector2 position = getNosePositionOfShip();
				float angle = getRotation();
				laser = new Laser(playerLaserTexture, position, playerLaserSpeed, angle);
				timeSinceLaserFired = 0;
			}
		return laser;
	}

}
