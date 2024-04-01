package model.ships;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import model.GameModel;
import model.Laser;

public abstract class Ship extends Sprite {
	
    public final float speed; //200
    
    private float health;
    
    //private final Texture laserTexture;
    
    // Laser-stuff
    // final float laserFireRate;
    float timeSinceLaserFired;
    
    
    private Viewport viewport; // Reference to the viewport

    // Initialize parameters for powerups
    private boolean isShieldActivated = false;
    private float shieldDuration = 20;
    private boolean isGunUpgraded = false;
    private float gunUpgradeDuration = 20;

	

	
    // private GameModel gameModel; // Reference to the gameModel

    // Constructor for playerShip
//    public Ship(Texture texture) {
////        super(texture);
////        // this.gameModel = gameModel; // Store the gameModel reference
////        // Store the viewport reference
////        setSize(40, 40); // Set the size of the ship
////        setPosition(GameModel.WORLD_WIDTH/2, GameModel.WORLD_HEIGHT/2); // Set the initial position of the ship
////        setOriginCenter(); // Set origin to center for rotation
//    	
//    	this(texture, GameModel.WORLD_WIDTH/2, GameModel.WORLD_HEIGHT/2, 40, 40, 200, null, 600, 0.5f, 100, null);
//    }
//
//    // Constructor with position for enemyships
//    public Ship(Texture texture, GameModel gameModel, float x, float y, float health) {
////        super(texture);
////        // this.gameModel = gameModel; // Store the gameModel reference
////        // Store the viewport reference
////        setSize(40, 40); // Set the size of the ship
////        setPosition(x, y); // Set the initial position of the ship
//    	
//    	this(texture, x, y, 40, 40, 200, null,  600, 0, health, null);
//    }
    
    /**
     * A very general construcot for the Ship calss. EveryThing about the ship must be given as arguments.
     * 
     * @param texture the texture of the ship
     * @param x a float rep. x-position of the ship.
     * @param y a float rep. the y-position of the ship.
     * @param width a float rep. the width of the ship. 
     * @param height a float rep. the height of the ship.
     * @param speed a float rep. the speed of the ship.
     * @param laserSpeed a float rep. the speed of the laser.
     * @param fireRate a flaot rep. the rate at which the ship can fire lasers.
     * @param viewport a FitViewport. The Viewport that should see the ship (? how viewport works??)
     */
    Ship(Texture texture, float x, float y, float width, float height, float speed, 
    		float health, FitViewport viewport) {
    	
    	super(texture);

    	this.speed = speed;
//    	this.laserTexture = laserTexture;
//    	this.laserSpeed = laserSpeed;
//    	this.laserFireRate = fireRate;
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

    public void rotateShip(Vector2 rotateTowards) {
        // Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY()); // Burde dette heller bli gitt som argument fra Controller?
        //viewport.unproject(rotateTowards); // Only needed for playerShip movement

        Vector2 shipPosition = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        Vector2 direction = rotateTowards.sub(shipPosition);
        float angle = direction.angleDeg();

        setOriginCenter();
        setRotation(angle - 90);
    }

    // verdiene kan endres om det ønskes bedre plassering av laser
    Vector2 getNosePositionOfShip() {
        float shipRotation = getRotation();

        float radians = (float)Math.toRadians(shipRotation);

        float laserCenterOffsetX = getWidth() * 0f;
        float laserCenterOffsetY = getHeight() * 1.2f ;

        float rotatedOffsetX = laserCenterOffsetX * MathUtils.cos(radians) - laserCenterOffsetY * MathUtils.sin(radians);
        float rotatedOffsetY = laserCenterOffsetX * MathUtils.sin(radians) + laserCenterOffsetY * MathUtils.cos(radians);

        float noseX = getX() + rotatedOffsetX + 2;
        float noseY = getY() + rotatedOffsetY - 6;

        return new Vector2(noseX, noseY);
    }

    abstract public Laser fireLaser(List<Laser> playerLasers);
//    {
//    	Laser laser = null;
//    	if (timeSinceLaserFired >= laserFireRate) {
//    		Vector2 position = getNosePositionOfShip();
//            float angle = getRotation(); 
//            laser = new Laser(laserTexture, position, laserSpeed, angle);
//            timeSinceLaserFired = 0;
//    	}
//        return laser;
//    }


    public void setViewport(FitViewport viewport) {
        this.viewport = viewport;

    }

    public boolean isDestroyed() {
        return health <= 0;
    }


    public void update(float deltaTime) {
    	this.timeSinceLaserFired += deltaTime;
        
        if (isShieldActivated) {
            shieldDuration -= deltaTime;
            if (shieldDuration <= 0) {
                isShieldActivated = false;
            }
        }

        if (isGunUpgraded) {
            gunUpgradeDuration -= deltaTime;
            if (gunUpgradeDuration <= 0) {
                isGunUpgraded = false;
            }
        }
    }

    public float getHealth() {
        return health;
    }

    public void takeDamage(float damage) {
        if (!isShieldActivated) {
            health -= damage;
        }
    }


    public void addLife() {
        health += 20;
    }

    // PowerUp methods
    public void upgradeGun() {
        isGunUpgraded = true;
    }

    public boolean isGunUpgraded() {
        return isGunUpgraded;
    }

    public void activateShield() {
        isShieldActivated = true;
        shieldDuration = 5;
    }

    public boolean isShieldActivated() {
        return isShieldActivated;
    }

    public void activateBlast() {
        //TODO
    }

}
