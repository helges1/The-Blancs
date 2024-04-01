package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer.Random;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.ships.BasicEnemyShip;
import model.ships.PlayerShip;
import model.ships.Ship;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameModel {

    // Player
    private Ship playerShip;
    private List<Laser> playerLasers; // Player lasers
//    private float playerLaserSpeed;
    private final static Sound laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3")); // Static to avoid reloading for each laser

    // Enemies
    private LinkedList<Ship> enemyShips;
    private List<Laser> enemyLasers;
    private LinkedList<PowerUps> powerUps;
//    private float enemyLaserSpeed;

    // World values for boundaries or other purposes
    public final static float WORLD_WIDTH = 800;
    public final static float WORLD_HEIGHT = 600;

    private float timeSincePowerUpSpawned;
    // Enemy spawn values
    private final float timeBetweenEnemiesSpawn;
    private float timeSinceEnemySpawned;
    private final int maxEnemiesOnScreen;

    // Textures for game entities
//    private Texture playerShipTexture;
//    private Texture playerLaserTexture; // Added missing laserTexture declaration
//    private Texture enemyShipTexture;
//    private Texture enemyLaserTexture;

    // Viewport
    private FitViewport viewport;
    
    // Keep track of destroyed enemy ships
    private int destroyedEnemyShipsCount = 0;

//    public GameModel(OrthographicCamera camera){
//    }
//        // Load textures
//        playerShipTexture = new Texture("pictures/playerShip.png");
//        playerLaserTexture = new Texture("pictures/playerLaser.png");
//        enemyShipTexture = new Texture("pictures/enemyShip.png");
//        enemyLaserTexture = new Texture("pictures/enemyLaser.png");
//
//        // Initialize player
//        playerShip = new Ship(playerShipTexture); // Updated to pass this GameModel instance
//        playerLasers = new LinkedList<>(); // Bør være LinkedList, for da kan man fjerne elementer fra midten av listen uten større kost
//        playerLaserSpeed = 600;
//        // Initialize enemies
//        enemyShips = new LinkedList<>();
//        enemyLasers = new LinkedList<>();
//        enemyLaserSpeed = 450; // Eksempelspeed
//        timeSinceEnemySpawned = 0;
	    
//	    this(new Texture("pictures/playerShip.png"), new Texture("pictures/playerLaser.png"),
//	    		new Texture("pictures/enemyShip.png"), new Texture("pictures/enemyLaser.png"),
//	    		Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3")),
//	    		600, 450, 5, 5, camera);
//    }
    
    /**
     * A general constructor for the GameModel where everything is provided as arguments
     * (except world sizes, for some reason)
     * 
     * @param playerShipTexture
     * @param playerLaserTexture
     * @param enemyShipTexture
     * @param enemyLaserTexture
     * @param playerLaserSpeed
     * @param enemyLaserSpeed
     * @param timeBetweenEnemiesSpawn
     * @param maxEnemiesOnScreen
     */
    public GameModel(FitViewport viewport, float timeBetweenEnemiesSpawn, int maxEnemiesOnScreen) {
    	
    	// Initialize textures
//    	this.playerShipTexture = playerShipTexture;
//    	this.playerLaserTexture = playerLaserTexture;
//    	this.enemyShipTexture = enemyShipTexture;
//    	this.enemyLaserTexture = enemyLaserTexture;
    	
//    	this.laserSound = laserSound;
    	
    	// Initialize player
    	playerShip = new PlayerShip(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, viewport);
    	playerLasers = new LinkedList<>();
//    	this.playerLaserSpeed = playerLaserSpeed;
    	
    	// Initialize enemies
    	enemyShips = new LinkedList<>();
    	enemyLasers = new LinkedList<>();
        powerUps = new LinkedList<>();
//    	this.enemyLaserSpeed = enemyLaserSpeed;
    	this.timeBetweenEnemiesSpawn = timeBetweenEnemiesSpawn;
    	this.timeSinceEnemySpawned = 0;
    	this.maxEnemiesOnScreen = maxEnemiesOnScreen;
        this.timeSincePowerUpSpawned = 0;


        // Set the viewport
    	this.viewport = viewport;
        // viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
    }

    public void updateModel(float deltaTime) {
        // Update timers
        timeSincePowerUpSpawned += deltaTime;
        timeSinceEnemySpawned += deltaTime;

        // Update playerShip
        playerShip.update(deltaTime);

        // Update enemyShips
        for (Ship ship : enemyShips)
        	ship.update(deltaTime);

        // Spawn new power ups
        if (timeSincePowerUpSpawned >= 10) {
            spawnPowerUp();
            timeSincePowerUpSpawned = 0;
        }

        // Update power ups
        Iterator<PowerUps> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUps powerUp = powerUpIterator.next();
            powerUp.update(deltaTime);
            if (powerUp.isExpired()) {
                powerUpIterator.remove(); // Remove expired power ups
            } else {
                // Check for collisions with player ship
                if (playerShip.getBoundingRectangle().overlaps(powerUp.getBoundingRectangle())) {
                    powerUpIterator.remove(); // Remove the power up after collecting
                    switch (powerUp.getPowerUpType()) {
                        case "life":
                            playerShip.addLife();
                            break;
                        case "gun":
                            playerShip.upgradeGun();
                            break;
                        case "shield":
                            playerShip.activateShield();
                            break;
                        case "blast":
                            playerShip.activateBlast();
                            break;
                    }
                }
            }
        }

        // Spawn new enemy ships
        if (timeSinceEnemySpawned >= timeBetweenEnemiesSpawn &&
                enemyShips.size() < maxEnemiesOnScreen) {
            spawnEnemyShip();
            timeSinceEnemySpawned = 0;
        }

        // Update enemies lasers
        Iterator<Laser> enemyLaserIterator = enemyLasers.iterator();
        while(enemyLaserIterator.hasNext()) {
            Laser laser = enemyLaserIterator.next();
            laser.update(deltaTime);
            if (laser.isOffScreen(WORLD_HEIGHT)) {
                enemyLaserIterator.remove(); // Remove off-screen lasers
            } else {
                // Check for collisions with player ship
                if (checkCollision(laser, playerShip)) {
                    enemyLaserIterator.remove(); // Remove the laser after hitting the ship
                    playerShip.takeDamage(5); // Reduce player health
                    if (playerShip.isDestroyed()) {
                        // Game over
                    }
                }
            }
        }


        // Update player lasers
        Iterator<Laser> laserIterator = playerLasers.iterator();
        while(laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update(deltaTime);
            if (laser.isOffScreen(WORLD_HEIGHT)) {
                laserIterator.remove(); // Remove off-screen lasers
            } else {
                // Check for collisions with enemy ships
                Iterator<Ship> enemyShipIterator = enemyShips.iterator();
                while (enemyShipIterator.hasNext()) {
                    Ship enemyShip = enemyShipIterator.next();
                    if (checkCollision(laser, enemyShip)) {
                        enemyShipIterator.remove(); // Remove the enemy ship if hit by a laser
                        laserIterator.remove(); // Remove the laser after hitting the ship
                        destroyedEnemyShipsCount++; // Increment the count of destroyed enemy ships
                        break; // Break to avoid ConcurrentModificationException
                    }
                }
            }
        }
    }
    
    public void firePlayerLaser() {
		Laser laser = playerShip.fireLaser();
		if (laser != null) {
			playerLasers.add(laser);
			laserSound.play();
		}
	}

//    private void fireEnemiesLasers() {
//        //TODO: for each enemyShip, fire laser if it's time for it to shoot
//        for (Ship ship : enemyShips) {
//        	Laser laser = ship.fireLaser();
//        	if (laser != null) {
//        		enemyLasers.add(laser);
//        		laserSound.play();
//        	}
//        }
//    }

    // Method to fire a laser from a single enemy
    public void fireEnemyLaser(Ship enemyShip) {
        Laser laser = enemyShip.fireLaser();
        if (laser != null) {
            enemyLasers.add(laser);
            laserSound.play();
        }
    }

    private void spawnPowerUp() {
        // Randomly select a position for the power up
        float powerUpX = MathUtils.random(0, WORLD_WIDTH - 20);
        float powerUpY = MathUtils.random(0, WORLD_HEIGHT - 20);

        // Randomly select a power up type
        String[] powerUpTypes = {"life", "gun", "shield", "blast"};
        String powerUpType = powerUpTypes[MathUtils.random.nextInt(powerUpTypes.length)];

        // Creating the power up
        PowerUps powerUp = new PowerUps(powerUpX, powerUpY, powerUpType, 5);

        // Adding the power up to the list
        powerUps.add(powerUp);

    }


    private void spawnEnemyShip() {

        // Define boundaries for enemy ship spawn, spawns along the bounderies of the game world
        float boundaryOffset = 40;
        float minX = boundaryOffset;
        float maxX = WORLD_WIDTH - BasicEnemyShip.basicEnemyShipTexture.getWidth() - boundaryOffset;
        float minY = WORLD_HEIGHT / 2 + boundaryOffset;
        float maxY = WORLD_HEIGHT - BasicEnemyShip.basicEnemyShipTexture.getHeight() - boundaryOffset;

        // Ensure minX and minY are not negative after applying offset
        minX = Math.max(minX, 0);
        minY = Math.max(minY, WORLD_HEIGHT / 2);

        // Generate random x and y positions within adjusted bounds
        float enemyShipX = MathUtils.random(minX, maxX);
        float enemyShipY = MathUtils.random(minY, maxY);

        // Creating the enemy ship
        Ship enemyShip = new BasicEnemyShip(enemyShipX, enemyShipY, viewport);

        // Adding the enemy ship to the list
        enemyShips.add(enemyShip);
    }

    public void update() {
        for (Iterator<Ship> iterator = getEnemyShips().iterator(); iterator.hasNext();) {
            Ship enemyShip = iterator.next();

            if (enemyShip.getBoundingRectangle().overlaps(getShip().getBoundingRectangle())) {
                iterator.remove();
                // Should also damage player
            }
        }

    }

    public int getDestroyedEnemyShipsCount() {
        return destroyedEnemyShipsCount;
    }
    public void resetDestroyedEnemyShipsCount() {
        destroyedEnemyShipsCount = 0;
    }


    public LinkedList<Ship> getEnemyShips() {
        return enemyShips;
    }

    public boolean checkCollision(Laser laser, Ship ship) {
        return laser.getBoundingRectangle().overlaps(ship.getBoundingRectangle());
    }

    public Ship getShip(){
        return playerShip;
    }

    public List<Laser> getPlayerLasers() {
        return playerLasers;
    }

    public FitViewport getViewport() {
        return viewport;
    }

    public List<Laser> getEnemyLasers() {
        return enemyLasers;
    }

    public LinkedList<PowerUps> getPowerUps() {
        return powerUps;
    }

	
}