package model.ships;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import model.lasers.Laser;

public class StrongerEnemyShip extends Ship {
	
	public static final Random random = new Random();

    public final TextureRegion strongerEnemyLaserTexture;

    private static final float strongerEnemyHealth = 10;
    private static final float strongerEnemySpeed = 50;

    private static final float strongerEnemyWidth = 40;
    private static final float strongerEnemyHeight = 40;

    private static final float strongerEnemyLaserSpeed = 250;
    private static final float strongerEnemyLaserWidth = 20;
    private static final float strongerEnemyLaserHeight = 35;    
    private static final float strongerEnemyFireRate = 1;


	public StrongerEnemyShip(TextureRegion strongerEnemyShipTexture, TextureRegion strongerEnemyLaserTexture,
			float x, float y, FitViewport viewport) {
		
		super(strongerEnemyShipTexture, x, y, strongerEnemyWidth, strongerEnemyHeight,
				strongerEnemySpeed, strongerEnemyHealth, strongerEnemyFireRate, viewport);
		
		this.strongerEnemyLaserTexture = strongerEnemyLaserTexture;
		resetCannon();
	}
	

	@Override
	void resetCannon() {
		this.cannon = () -> {
			if (random.nextInt(2) >= 0) {
				Vector2 position = getNosePositionOfShip();
	            float angle = getRotation();
	            Laser laser = new Laser(strongerEnemyLaserTexture, position, strongerEnemyLaserSpeed,
	            		angle, strongerEnemyLaserWidth, strongerEnemyLaserHeight, 20);
	            laser.centreAtPoint(position);
	            return new Laser[]{laser};
			}
			return null;
		};
	}

	@Override
	void upgradeCannon() {
		// TODO Auto-generated method stub

	}

}
