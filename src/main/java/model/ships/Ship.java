package model.ships;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import model.lasers.Cannon;
import model.lasers.Laser;
import model.powerUps.PowerUps.PowerUpType;

public abstract class Ship extends Sprite {

    public final float speed; // 200

    private float health;

    // PowerUps
    private PowerUpType activePowerUp;

    // Laser
    private TextureRegion laserTexture;
    private float laserSpeed;
	private float laserDamage;
	private float laserWidth;
	private float laserHeight;
    private float fireRate;
    float timeSinceLaserFired;
    Cannon cannon;

    private Viewport viewport; // Reference to the viewport
    

    // Initialize parameters for powerups
    private boolean isShieldActivated = false;
    private boolean isGunUpgraded = false;
    private float powerUpDuration = 20;

    private Vector2 velocity = new Vector2(0, 0);

    // private GameModel gameModel; // Reference to the gameModel

    /**
     * Constructor to create a ship instance, suitable for both production and
     * testing.
     * Accepts a TextureRegion which allows for easier mocking and testing.
     *
     * @param textureRegion the texture region of the ship
     * @param x             the x-position of the ship
     * @param y             the y-position of the ship
     * @param width         the width of the ship
     * @param height        the height of the ship
     * @param speed         the movement speed of the ship
     * @param health        the initial health of the ship
     * @param viewport      the viewport in which the ship exists (for bounding
     *                      checks in movements)
     */
    Ship(TextureRegion textureRegion, float x, float y, float width, float height, float speed, float health,
            Viewport viewport) {
        super(textureRegion); // Use the provided TextureRegion

        this.speed = speed;
        this.health = health;
        this.timeSinceLaserFired = 0;
        this.viewport = viewport; // Use the provided viewport, more flexible

        setSize(width, height);
        setPosition(x, y);
        setOriginCenter(); // Set origin to center for rotation
    }

    /**
     * A very general constructor for the Ship class. EveryThing about the ship must
     * be given as arguments.
     * 
     * @param texture    the texture of the ship
     * @param x          a float rep. x-position of the ship.
     * @param y          a float rep. the y-position of the ship.
     * @param width      a float rep. the width of the ship.
     * @param height     a float rep. the height of the ship.
     * @param speed      a float rep. the speed of the ship.
     * @param health     a float rep. the ship's health
     * @param fireRate   a float rep. the rate at which the ship can fire lasers.
     * @param viewport   a FitViewport. The Viewport that should see the ship (? how
     *                   viewport works??)
     */
    Ship(TextureRegion texture, float x, float y, float width, float height, float speed,
            float health, float fireRate, FitViewport viewport) {

        super(texture);

        this.speed = speed;
        this.timeSinceLaserFired = 0;
        this.health = health;
        this.fireRate = fireRate;
        setSize(width, height);
        setPosition(x, y);
        this.setViewport(viewport);
        setOriginCenter();
    }
    
    Ship(TextureRegion shipTexture, TextureRegion laserTexture, float x, float y, float width,
			float height, float speed, float health, float fireRate,
			float laserSpeed, float laserDamage, float laserWidth, float laserHeight,
			FitViewport viewport) {
    	
		this(shipTexture, x, y, width, height, speed, health, fireRate, viewport);
		this.laserTexture = laserTexture;
		this.laserSpeed = laserSpeed;
		this.laserDamage = laserDamage;
		this.laserWidth = laserWidth;
		this.laserHeight = laserHeight;
	}

	public void update(float deltaTime) {
        this.timeSinceLaserFired += deltaTime;

        // PowerUp timer
        if (activePowerUp != null) {
            powerUpDuration -= deltaTime;
            if (powerUpDuration <= 0) {
                if (activePowerUp.equals(PowerUpType.SHIELD)) {
                	isShieldActivated = false;
                } else if (activePowerUp.equals(PowerUpType.GUN)) {
                	resetCannon();
                	isGunUpgraded = false;
                }
                activePowerUp = null;
                powerUpDuration = 20;
            }
        }

        // This is for enemy ships when they get blasted
        if (!velocity.isZero()) {
            float newX = getX() + velocity.x * deltaTime;
            float newY = getY() + velocity.y * deltaTime;
            setPosition(newX, newY);

            // Slow down the ship
            velocity.scl(0.98f); // Slow down the ship by 2% each frame

            // If velocity is very small, set it to zero
            if (velocity.len2() < 0.01f) {
                velocity.setZero();
            }
        }
    }

    public void moveUp(float deltaTime) {
        float newY = getY() + speed * deltaTime;
        if (newY + getHeight() > viewport.getWorldHeight()) { // Check upper bound against viewport's world height
            newY = viewport.getWorldHeight() - getHeight();
        }
        setPosition(getX(), newY);
    }

    public void moveDown(float deltaTime) {
        float newY = getY() - speed * deltaTime;
        if (newY < 0) { // Check lower bound
            newY = 0;
        }
        setPosition(getX(), newY);
    }

    public void moveLeft(float deltaTime) {
        float newX = getX() - speed * deltaTime;
        if (newX < 0) { // Check left bound
            newX = 0;
        }
        setPosition(newX, getY());
    }

    public void moveRight(float deltaTime) {
        float newX = getX() + speed * deltaTime;
        if (newX + getWidth() > viewport.getWorldWidth()) { // Check right bound against viewport's world width
            newX = viewport.getWorldWidth() - getWidth();
        }
        setPosition(newX, getY());
    }

    public void moveShip(Vector2 movementPosition) {
        float newX = getX() + speed * movementPosition.x * Gdx.graphics.getDeltaTime();
        float newY = getY() + speed * movementPosition.y * Gdx.graphics.getDeltaTime();

        if (newX < 0) {
            newX = 0;
        } else if (newX + getWidth() > viewport.getWorldWidth()) {
            newX = viewport.getWorldWidth() - getWidth();
        }

        if (newY < 0) {
            newY = 0;
        } else if (newY + getHeight() > viewport.getWorldHeight()) {
            newY = viewport.getWorldHeight() - getHeight();
        }

        setPosition(newX, newY);
    }

    public void rotateShip(Vector2 rotateTowards) {
        Vector2 shipPosition = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        Vector2 direction = rotateTowards.sub(shipPosition);
        float angle = direction.angleDeg();

        setOriginCenter();
        setRotation(angle - 90);
    }

    public void rotateShip(float angle) {
        setRotation(angle);
    }
    
    
    Vector2 getNosePositionOfShip() {
        float shipRotation = getRotation();

        float radians = (float) Math.toRadians(shipRotation);
        float width = getWidth() / 2;
        float height = getHeight();
        float noseX = getX();
        float noseY = getY();
        
        if (shipRotation >= 0 && shipRotation <= 90) {
        	noseX += width*MathUtils.cos(radians);
        	noseY += height*MathUtils.cos(radians) + width*MathUtils.sin(radians);
        }
        else if (shipRotation > 90 && shipRotation <= 180) {
        	//90<x<180 -> sin(x) > 0 & cos(x) < 0
        	noseX -= width*MathUtils.cos(radians);
        	noseY += width*MathUtils.sin(radians);
        }
        else if (shipRotation > 180 && shipRotation <= 270) {
        	//180<x<270 -> sin(x) < 0 & cos(x) < 0
        	noseX += width*(-MathUtils.cos(radians)) + height*(-MathUtils.sin(radians));
        	noseY -= width*MathUtils.sin(radians);
        }
        else {
        	//270<x<360 -> sin(x) < 0 & cos(x) > 0
        	noseX += height*(-MathUtils.sin(radians)) + width*MathUtils.cos(radians);
        	noseY += width*(-MathUtils.sin(radians)) + height*MathUtils.cos(radians);
        }

        return new Vector2(noseX, noseY);
    }

    public boolean fireLaser(List<Laser> lasers) {
    	if (timeSinceLaserFired >= fireRate) {
    		timeSinceLaserFired = 0;
    		Vector2 position = getNosePositionOfShip();
    		float angle = getRotation();
	    	Laser[] firedLasers = cannon.fireCannon(laserTexture, position, laserSpeed,
	    			laserDamage, angle, laserWidth, laserHeight);
	    	if (firedLasers != null) {
		    	lasers.addAll(Arrays.asList(firedLasers));
		    	return true;
	    	}
    	}
    	return false;
    }

    public void setViewport(FitViewport viewport) {
        this.viewport = viewport;

    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void applyBlastForce(Vector2 force) {
        this.velocity.add(force);
    }

   
    
    /**
     * Reset the standard cannon the ship uses.
     */
    abstract void resetCannon();
    
    /**
     * Upgrade the cannon to a more powerful version.
     */
    abstract void upgradeCannon();

    public float getHealth() {
        return health;
    }

    public void takeDamage(float damage) {
        if (activePowerUp != PowerUpType.SHIELD) {
            health -= damage;
        }
    }

    public void addHealth() {
        health += 20;
    }

    public void setActivePowerUp(PowerUpType powerUp) {
    	// To make sure the upgraded gun returns to normal when the game resets or a new powerUp is picked up.
    	if (powerUp == null || !powerUp.equals(PowerUpType.GUN))
    		resetCannon();
    	
        activePowerUp = powerUp;
        if (PowerUpType.GUN.equals(activePowerUp))
        	upgradeCannon();
    }

    public PowerUpType getActivePowerUp() {
        return activePowerUp;
    }

    public float getPowerUpTimer() {
        return powerUpDuration;
    }

    public void resetPowerUpTimer() {
        powerUpDuration = 20;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Method to let laser wait v time before firing laser after restarting the game.
     *
     * @param v
     */
    public void cooldownLaser(float v) {
        this.timeSinceLaserFired = v;
    }
}
