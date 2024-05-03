package model.ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.lasers.Cannons;

/**
 * A type of enemy ship.
 * It has 10 hp, and does 10 damage.
 * Extends <code>Ship</code>
 */
public class BasicEnemyShip extends Ship {

    private static final float basicEnemyHealth = 10;
    private static final float basicEnemySpeed = 50;

    private static final float basicEnemyWidth = 40;
    private static final float basicEnemyHeight = 40;

    private static final float basicEnemyLaserSpeed = 300;
    private static final float basicEnemyLaserDamage = 5;
    private static final float basicEnemyLaserWidth = 15;
    private static final float basicEnemyLaserHeight = 30;    
    private static final float basicEnemyFireRate = 1;

    /**
     * Constructor for a basic enemy ship.
     * Must be given its own and its laser's textures as arguments.
     * 
     * @param basicEnemyShipTexture
     * @param basicEnemyLaserTexture
     * @param x        float: the x position at which the ship should spawn.
     * @param y        float: the y position at which the ship should spawn.
     * @param viewport FitViewport: the viewport of the game.
     */
    BasicEnemyShip(TextureRegion basicEnemyShipTexture, TextureRegion basicEnemyLaserTexture,
    		float x, float y, FitViewport viewport) {

        super(basicEnemyShipTexture, basicEnemyLaserTexture, x, y, basicEnemyWidth, basicEnemyHeight,
        		basicEnemySpeed, basicEnemyHealth, basicEnemyFireRate, basicEnemyLaserSpeed,
        		basicEnemyLaserDamage, basicEnemyLaserWidth, basicEnemyLaserHeight, viewport);
        
        resetCannon(); // Set the cannon
    }


	@Override
	void resetCannon() {
		this.cannon = Cannons::basicEnemyCannon;
	}

	@Override
	void upgradeCannon() {
		//Pass - Has no upgrade capabilities
	}
		
}
