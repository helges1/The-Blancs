package model.ships;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import model.Laser;
import model.PowerUps.PowerUpType;

public abstract class Ship extends Sprite {

    public final float speed; // 200

    private float health;

    // Powerups
    private PowerUpType activePowerUp;

    // Laser
    float timeSinceLaserFired;

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
    public Ship(TextureRegion textureRegion, float x, float y, float width, float height, float speed, float health,
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
     * @param laserSpeed a float rep. the speed of the laser.
     * @param fireRate   a float rep. the rate at which the ship can fire lasers.
     * @param viewport   a FitViewport. The Viewport that should see the ship (? how
     *                   viewport works??)
     */
    Ship(TextureRegion texture, float x, float y, float width, float height, float speed,
            float health, FitViewport viewport) {

        super(texture);

        this.speed = speed;
        this.timeSinceLaserFired = 0;
        this.health = health;
        setSize(width, height);
        setPosition(x, y);
        this.setViewport(viewport);
        setOriginCenter();
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

    // verdiene kan endres om det ønskes bedre plassering av laser
    Vector2 getNosePositionOfShip() {
        float shipRotation = getRotation();

        float radians = (float) Math.toRadians(shipRotation);

//        float laserCenterOffsetX = getWidth() * 0f;
//        float laserCenterOffsetY = getHeight() * 1.2f;
//
//        float rotatedOffsetX = laserCenterOffsetX * MathUtils.cos(radians)
//                - laserCenterOffsetY * MathUtils.sin(radians);
//        float rotatedOffsetY = laserCenterOffsetX * MathUtils.sin(radians)
//                + laserCenterOffsetY * MathUtils.cos(radians);
//
//        float noseX = getX() + rotatedOffsetX + 2;
//        float noseY = getY() + rotatedOffsetY - 6;
//        
//        float noseX = getX() + (getWidth() / 2) * MathUtils.sin(radians) + getHeight() * MathUtils.cos(radians);
//        float noseY = getY() + (getWidth() / 2) * MathUtils.cos(radians) + getHeight() * MathUtils.sin(radians);
        
        float noseX = getX();
        float noseY = getY();
        if (shipRotation >= 0 && shipRotation <= 90) {
        	noseX += (getWidth() / 2)*MathUtils.cos(radians);
        	noseY += getHeight()*MathUtils.cos(radians) + (getWidth() / 2)*MathUtils.sin(radians);
        }
        else if (shipRotation > 90 && shipRotation <= 180) {
        	// cos(x) < 0 when 90<x<180
        	noseX -= (getWidth() / 2)*MathUtils.cos(radians);
        	noseY += (getWidth() / 2)*MathUtils.sin(radians);
        }
        else if (shipRotation > 180 && shipRotation <= 275) {
        	// sin(x) and cos(x) < 0 when 180<x<275
        	noseX += (getWidth() / 2)*(-MathUtils.cos(radians)) + getHeight()*(-MathUtils.sin(radians));
        	noseY -= (getWidth() / 2)*MathUtils.sin(radians);
        }
        else {
        	// angle (0, -90) | sin(x) < 0
        	noseX += getHeight()*(-MathUtils.sin(radians)) + (getWidth() / 2)*MathUtils.cos(radians);
        	noseY += (getWidth() / 2)*(-MathUtils.sin(radians)) + getHeight()*MathUtils.cos(radians);
        }

        return new Vector2(noseX, noseY);
    }

    abstract public boolean fireLaser(List<Laser> lasers);

    public void setViewport(FitViewport viewport) {
        this.viewport = viewport;

    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void applyBlastForce(Vector2 force) {
        this.velocity.add(force);
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
        activePowerUp = powerUp;
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

}
