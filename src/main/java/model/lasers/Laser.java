package model.lasers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Laser extends Sprite {
    private Vector2 velocity;
    private Vector2 windForce;
    
    private float damage;

    public Laser(TextureRegion texture, Vector2 position, float speed, float angle) {
        super(texture);
        setPosition(position.x, position.y);
        setRotation(angle);

        // Assuming the angle is correctly adjusted for your game's coordinate system,
        // and 'speed' represents the constant speed you want for the laser.
        float radians = (float) Math.toRadians(angle - 270);
        velocity = new Vector2((float) Math.cos(radians) * speed, (float) Math.sin(radians) * speed);
    }

    /**
     * Constructor for Laser, which allows you to specify texture, position,
     * speed, angle, width and height of the laser.
     * 
     * @param texture a <code>TextureRegion</code> corresponding to the texture of the laser.
     * @param position a <code>Vector2</code> representing the position the laser is to be constructed at.
     * @param speed a <code>float</code> representing the speed at which the laser can travel.
     * @param angle a <code>float</code> representing the angle of the laser in degrees.
     * The laser faces north (up) at 0 degrees.
     * @param width a <code>float</code> representing the width of the laser.
     * @param height a <code>float</code> representing the height of the laser.
     */
    public Laser(TextureRegion texture, Vector2 position, float speed, float angle,
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
    
    public Laser(TextureRegion texture, Vector2 position, float speed, float angle,
    		float width, float height, float damage) {
        super(texture);
        setSize(width, height);
        setOriginCenter();
        setRotation(angle);

        // Assuming the angle is correctly adjusted for your game's coordinate system,
        // and 'speed' represents the constant speed you want for the laser.
        float radians = (float) Math.toRadians(angle - 270);
        velocity = new Vector2((float) Math.cos(radians) * speed, (float) Math.sin(radians) * speed);
        this.damage = damage;
    }
    
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

    public void setWindForce(Vector2 windForce) {
        this.windForce = windForce;
    }

    public void update(float deltaTime) {
        // Apply wind force to the velocity
        if (windForce != null) velocity.add(windForce.x * deltaTime, windForce.y * deltaTime);

        setPosition(getX() + velocity.x * deltaTime, getY() + velocity.y * deltaTime);
    }

    public boolean isOffScreen(float worldHeight) {
        return getY() > worldHeight;
    }

    public static void disposeSound() {
        // Static method to dispose the sound resource when the game is exiting
        // laserSound.dispose();
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
	
}
