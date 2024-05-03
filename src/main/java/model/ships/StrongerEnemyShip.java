package model.ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.lasers.Cannons;

/**
 * A Slightly stronger type of enemy ship.
 * It has 20 hp, and does 20 damage.
 * Extends <code>Ship</code>.
 */
public class StrongerEnemyShip extends Ship {

    private static final float strongerEnemyHealth = 20;
    private static final float strongerEnemySpeed = 50;

    private static final float strongerEnemyWidth = 40;
    private static final float strongerEnemyHeight = 40;

    private static final float strongerEnemyLaserSpeed = 250;
    private static final float strongerEnemyLaserDamage = 20;
    private static final float strongerEnemyLaserWidth = 20;
    private static final float strongerEnemyLaserHeight = 35;    
    private static final float strongerEnemyFireRate = 1;


	StrongerEnemyShip(TextureRegion strongerEnemyShipTexture, TextureRegion strongerEnemyLaserTexture,
			float x, float y, FitViewport viewport) {
		
		super(strongerEnemyShipTexture, strongerEnemyLaserTexture, x, y, strongerEnemyWidth, strongerEnemyHeight,
				strongerEnemySpeed, strongerEnemyHealth, strongerEnemyFireRate, strongerEnemyLaserSpeed,
				strongerEnemyLaserDamage, strongerEnemyLaserWidth, strongerEnemyLaserHeight, viewport);
		
		resetCannon();
	}
	

	@Override
	void resetCannon() {
		this.cannon = Cannons::basicEnemyCannon;
	}

	@Override
	void upgradeCannon() {
		// Pass: No upgrade capabilities
	}

}
