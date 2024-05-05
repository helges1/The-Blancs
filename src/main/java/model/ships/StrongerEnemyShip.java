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

    static final float strongerEnemyHealth = 20;
    static final float strongerEnemySpeed = 50;

    static final float strongerEnemyWidth = 40;
    static final float strongerEnemyHeight = 40;

    static final float strongerEnemyLaserSpeed = 250;
    static final float strongerEnemyLaserDamage = 20;
    static final float strongerEnemyLaserWidth = 20;
    static final float strongerEnemyLaserHeight = 35;    
    static final float strongerEnemyFireRate = 1;

    /**
     * Constructor for a stronger enemy ship.
     * Must be given its own and its laser's textures as arguments.
     * 
     * @param strongerEnemyShipTexture
     * @param strongerEnemyLaserTexture
     * @param x        float: the x position at which the ship should spawn.
     * @param y        float: the y position at which the ship should spawn.
     * @param viewport FitViewport: the viewport of the game.
     */
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
