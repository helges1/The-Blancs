package model.lasers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * This interface is used to define a cannon, which works as a factory for
 * <code>Laser</code> objects.
 */
@FunctionalInterface
public interface Cannon {
	
	/**
	 * Fire the cannon.
	 * @param laserDamage 
	 * @param laserHeight 
	 * @param laserWidth 
	 * @param angle 
	 * @param laserSpeed 
	 * @param position 
	 * @param laserTexture 
	 * 
	 * @return a Laser[]. Can include multiple, one, or even zero Laser-objects. 
	 */
	Laser[] fireCannon(TextureRegion laserTexture, Vector2 position, float laserSpeed, float laserDamage,
			float angle, float laserWidth, float laserHeight);

}
