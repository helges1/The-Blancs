package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Ship extends Sprite {
	
    public final float speed; //200
    
    private float laserSpeed;
    private final float laserFireRate;
    private float timeSinceLaserFired;
    
    private Viewport viewport; // Reference to the viewport

	

	
    // private GameModel gameModel; // Reference to the gameModel

    public Ship(Texture texture) {
//        super(texture);
//        // this.gameModel = gameModel; // Store the gameModel reference
//        // Store the viewport reference
//        setSize(40, 40); // Set the size of the ship
//        setPosition(GameModel.WORLD_WIDTH/2, GameModel.WORLD_HEIGHT/2); // Set the initial position of the ship
//        setOriginCenter(); // Set origin to center for rotation
    	
    	this(texture, GameModel.WORLD_WIDTH/2, GameModel.WORLD_HEIGHT/2, 40, 40, 200, 600, 0.5f, null);
    }

    // Constructor with position for enemyships
    public Ship(Texture texture, GameModel gameModel, float x, float y) {
//        super(texture);
//        // this.gameModel = gameModel; // Store the gameModel reference
//        // Store the viewport reference
//        setSize(40, 40); // Set the size of the ship
//        setPosition(x, y); // Set the initial position of the ship
    	
    	this(texture, x, y, 40, 40, 200, 600, 0, null);
    }
    
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
    public Ship(Texture texture, float x, float y, float width, float height,
    		float speed, float laserSpeed, float fireRate,
    		FitViewport viewport) {
    	
    	super(texture);
    	this.speed = speed;
    	this.laserSpeed = laserSpeed;
    	this.laserFireRate = fireRate;
    	this.timeSinceLaserFired = fireRate;
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
    private Vector2 getNosePositionOfShip() {
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

    public Laser fireLaser(Texture laserTexture, float laserSpeed) {
    	Laser laser = null;
    	if (timeSinceLaserFired >= laserFireRate) {
    		Vector2 position = getNosePositionOfShip();
            float angle = getRotation(); 
            laser = new Laser(laserTexture, position, this.laserSpeed, angle);
            timeSinceLaserFired = 0;
    	}
        return laser;
    }


    public void setViewport(FitViewport viewport) {
        this.viewport = viewport;

    }


    public void update(float deltaTime) {
    	this.timeSinceLaserFired += deltaTime;
    }
}
