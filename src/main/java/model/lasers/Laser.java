package model.lasers;

import java.util.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * This class is used to represent a laser, fired from a laser cannon.
 */
public class Laser extends Sprite {
	
    private Vector2 velocity;
    private Vector2 windForce;
    private float damage;
    
    /**
     * A complete constructor for a <code>Laser</code> where you specify all its
     * properties.
     * 
     * @param texture a <code>TextureRegion</code> corresponding to the texture of the laser.
     * @param position a <code>Vector2</code>: the position the laser is to be constructed at.
     * @param speed a <code>float</code>: the speed at which the laser can travel.
     * @param angle a <code>float</code>: the angle of the laser in degrees.
     * The laser faces north (up) at 0 degrees.
     * @param width a <code>float</code>: the width of the laser.
     * @param height a <code>float</code>: the height of the laser. 
     * @param damage a <code>float</code>: the amount of damage the laser can deal.
     */
    Laser(TextureRegion texture, Vector2 position, float speed, float angle,
    		float width, float height, float damage) {
        this(texture, position, speed, angle, width, height);
        this.damage = damage;
    }

    /**
     * Constructor for Laser, which allows you to specify texture, position,
     * speed, angle, width and height of the laser.
     * 
     * @param texture a <code>TextureRegion</code> corresponding to the texture of the laser.
     * @param position a <code>Vector2</code>: the position the laser is to be constructed at.
     * @param speed a <code>float</code>: the speed at which the laser can travel.
     * @param angle a <code>float</code>: the angle of the laser in degrees.
     * The laser faces north (up) at 0 degrees.
     * @param width a <code>float</code>: the width of the laser.
     * @param height a <code>float</code>: the height of the laser.
     */
    private Laser(TextureRegion texture, Vector2 position, float speed, float angle,
    		float width, float height) {
        super(texture);
        setSize(width, height);
        setOriginCenter();
        setRotation(angle);

        // Assuming the angle is correctly adjusted for your game's coordinate system,
        // and 'speed' represents the constant speed you want for the laser.
        float radians = (float) Math.toRadians(angle - 270);
        velocity = new Vector2((float) Math.cos(radians) * speed, (float) Math.sin(radians) * speed);
    }
    
    /**
     * This constructor is used exclusively for testing.
     */
    Laser(TextureRegion texture, Vector2 position, float speed, float angle) {
        super(texture);
        setPosition(position.x, position.y);
        setRotation(angle);

        // Assuming the angle is correctly adjusted for your game's coordinate system,
        // and 'speed' represents the constant speed you want for the laser.
        float radians = (float) Math.toRadians(angle - 270);
        velocity = new Vector2((float) Math.cos(radians) * speed, (float) Math.sin(radians) * speed);
    }
    
    /**
     * Position this <code>Laser</code> with its base centred at the given
     * <strong>position</strong>, corresponding the laser's angle.
     *  
     * @param position a <code>Vector2</code>: the position at which to centre the laser.
     */
    public void centreAtPoint(Vector2 position) {
    	float angle = getRotation();
    	float radians = (float) Math.toRadians(angle);
    	float width = getWidth() / 2;
    	float height = getHeight();
    	float xPos = position.x;
    	float yPos = position.y;
    	
    	if (angle >= 0 && angle <= 90) {
    		xPos += -(width*MathUtils.cos(radians)) - height*MathUtils.sin(radians);
    		yPos += -(width*MathUtils.sin(radians));
    	}
    	else if (angle > 90 && angle <= 180) {
    		//90<x<180 -> sin(x) > 0 & cos(x) < 0
    		xPos += -(height*MathUtils.sin(radians)) - width*(-MathUtils.cos(radians));
    		yPos += -(width*MathUtils.sin(radians)) - height*(-MathUtils.cos(radians));
    	}
    	else if (angle > 180 && angle <= 270) {
    		//180<x<270 -> sin(x) < 0 & cos(x) < 0
    		xPos -= width*(-MathUtils.cos(radians));
    		yPos += -(height*(-MathUtils.cos(radians))) - width*(-MathUtils.sin(radians));
    	}
    	else {
    		//270<x<360 -> sin(x) < 0 & cos(x) > 0
    		xPos -= width*MathUtils.cos(radians);
    		yPos -= width*(-MathUtils.sin(radians));
    	}
    	setPosition(xPos, yPos);
    }

    /**
     * Apply a force to this <code>Laser</code>.
     * (Even though there's no wind in space)
     * 
     * @param windForce a <code>Vector2</code>: the force with which to affect the laser.
     */
    public void setWindForce(Vector2 windForce) {
        this.windForce = windForce;
    }

    /**
     * This method will move the laser according the amount of time that has passed,
     * based on its velocity.
     * 
     * @param deltaTime a <code>float</code>: the amount of time that has passed in seconds.
     */
    public void update(float deltaTime) {
        // Apply wind force to the velocity
        if (windForce != null) velocity.add(windForce.x * deltaTime, windForce.y * deltaTime);

        setPosition(getX() + velocity.x * deltaTime, getY() + velocity.y * deltaTime);
    }

	/**
	 * This method will check if the laser is off the screen.
	 *
	 * @param worldHeight a <code>float</code>: the height of the world.
	 * @return a <code>boolean</code>: true if the laser is off the screen, false otherwise.
	 */
    public boolean isOffScreen(float worldHeight) {
        return getY() > worldHeight;
    }


    public Vector2 getVelocity() {
        return velocity;
    }

	/**
	 * @return The damage of the laser as a <code>float</code>.
	 * Can be <code>null</code> if not initialized in the constructor.
	 */
	public float getDamage() {
		return damage;
	}


	@Override
	public int hashCode() {
		return Objects.hash(damage, velocity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Laser other = (Laser) obj;
		return Float.floatToIntBits(damage) == Float.floatToIntBits(other.damage)
				&& Objects.equals(velocity, other.velocity)
				&& Objects.equals(getX(), other.getX()) && Objects.equals(getY(), other.getY());
	}
	
}
