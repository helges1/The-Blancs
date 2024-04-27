package model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Laser extends Sprite {
    private Vector2 velocity;
    private Vector2 windForce;
    // private static Sound laserSound; // Static to avoid reloading for each laser
    //
    // static {
    // laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3"));
    // }

    public Laser(TextureRegion texture, Vector2 position, float speed, float angle) {
        super(texture);
        setPosition(position.x, position.y);
        setRotation(angle);

        // Assuming the angle is correctly adjusted for your game's coordinate system,
        // and 'speed' represents the constant speed you want for the laser.
        float radians = (float) Math.toRadians(angle - 270);
        velocity = new Vector2((float) Math.cos(radians) * speed, (float) Math.sin(radians) * speed);
    }

    // Alternative constructor to allow setting custom width and height
    public Laser(TextureRegion texture, Vector2 position, float speed, float angle, float width, float height) {
        super(texture);
        setSize(width, height);
        setOriginCenter();
        setRotation(angle);
        centreAtPoint(position);

        // Assuming the angle is correctly adjusted for your game's coordinate system,
        // and 'speed' represents the constant speed you want for the laser.
        float radians = (float) Math.toRadians(angle - 270);
        velocity = new Vector2((float) Math.cos(radians) * speed, (float) Math.sin(radians) * speed);
    }
    
    private void centreAtPoint(Vector2 position/*, float angle, float width, float height*/) {
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
}
