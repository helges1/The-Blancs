package model.lasers;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * This class contains static void methods that can be used to create <code>Cannon</code>
 * objects, as <code>Cannon</code> is a functional interface.
 */
public class Cannons {
	
	private static final Random random = new Random();

	/**
	 * Creates a basic player cannon that fires a single laser.
	 * @param laserTexture
	 * @param position
	 * @param laserSpeed
	 * @param laserDamage
	 * @param angle
	 * @param laserWidth
	 * @param laserHeight
	 * @return a Laser[] containing a single Laser object.
	 */
	public static Laser[] basicPlayerCannon(TextureRegion laserTexture, Vector2 position, float laserSpeed, float laserDamage,
			float angle, float laserWidth, float laserHeight) {
		
		Laser laser = new Laser(laserTexture, position, laserSpeed,
				angle, laserWidth, laserHeight, laserDamage);
		laser.centreAtPoint(position);
		return new Laser[]{laser};
	}

	/**
	 * Creates an upgraded player cannon that fires three lasers.
	 * @param laserTexture
	 * @param position
	 * @param laserSpeed
	 * @param laserDamage
	 * @param angle
	 * @param laserWidth
	 * @param laserHeight
	 * @return a Laser[] containing three Laser objects.
	 */
	public static Laser[] upgradedPlayerCannon(TextureRegion laserTexture, Vector2 position, float laserSpeed, float laserDamage,
			float angle, float laserWidth, float laserHeight) {
		
		Laser[] firedLasers = new Laser[3];
		float angle1 = angle;
		float angle2 = angle + 25;
		float angle3 = angle - 25;
		
		Laser laser1 = new Laser(laserTexture, position, laserSpeed, angle1,
				laserWidth, laserHeight, laserDamage);
		laser1.centreAtPoint(position);
		Laser laser2 = new Laser(laserTexture, position, laserSpeed, angle2,
				laserWidth, laserHeight, laserDamage);
		laser2.centreAtPoint(position);
		Laser laser3 = new Laser(laserTexture, position, laserSpeed, angle3,
				laserWidth, laserHeight, laserDamage);
		laser3.centreAtPoint(position);
		
		firedLasers[0] = laser1;
		firedLasers[1] = laser2;
		firedLasers[2] = laser3;
		return firedLasers;
	}

	/**
	 * Creates a basic enemy cannon that fires a single laser.
	 * @param laserTexture
	 * @param position
	 * @param laserSpeed
	 * @param laserDamage
	 * @param angle
	 * @param laserWidth
	 * @param laserHeight
	 * @return a Laser[] containing a single Laser object.
	 */
	public static Laser[] basicEnemyCannon(TextureRegion laserTexture, Vector2 position, float laserSpeed, float laserDamage,
			float angle, float laserWidth, float laserHeight) {
		
		if (random.nextInt(3) > 0) {
            Laser laser = new Laser(laserTexture, position, laserSpeed,
            		angle, laserWidth, laserHeight, laserDamage);
            laser.centreAtPoint(position);
            return new Laser[]{laser};
		}
        return null;
	}

}
