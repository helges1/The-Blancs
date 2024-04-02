package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.ships.BasicEnemyShip;
import model.ships.PlayerShip;
import model.ships.Ship;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameModel {

    // Initialize player ship and lasers
    private Ship playerShip;
    private List<Laser> playerLasers; // Player lasers
    

    // Initialize enemy ships and lasers
    private LinkedList<Ship> enemyShips;
    private List<Laser> enemyLasers;

    // Initialize power ups
    private LinkedList<PowerUps> powerUps;

    // World values for boundaries or other purposes
    public final static float WORLD_WIDTH = 800;
    public final static float WORLD_HEIGHT = 600;

    // Power up spawn values
    private float timeSincePowerUpSpawned;

    // Enemy spawn values
    private final float timeBetweenEnemiesSpawn;
    private float timeSinceEnemySpawned;
    private final int maxEnemiesOnScreen;

    // Viewport
    private FitViewport viewport;

    // Keep track of destroyed enemy ships
    private int destroyedEnemyShipsCount = 0;
    
    // Graphics
    private final TextureAtlas atlas;
    
    private TextureRegion playerShipTexture; 
    private TextureRegion playerLaserTexture; 
    private TextureRegion basicEnemyShipTexture; 
    private TextureRegion basicEnemyLaserTexture;
    
    // Sounds
    private final Sound laserSound;

    /**
     * A general constructor for the GameModel where everything is provided as
     * arguments
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
    public GameModel(TextureAtlas atlas,
//    		Texture playerShipTexture, Texture playerLaserTexture,
//    		Texture basicEnemyShipTexture, Texture basicEnemyLaserTexture,
    		Sound laserSound, FitViewport viewport, float timeBetweenEnemiesSpawn, int maxEnemiesOnScreen) {
    	
    	// Initialize field variables
    	this.atlas = atlas;
    	this.playerShipTexture = atlas.findRegion("playerShip");
    	this.playerLaserTexture = atlas.findRegion("playerLaser");
    	this.basicEnemyShipTexture = atlas.findRegion("basicEnemyShip");
    	this.basicEnemyLaserTexture = atlas.findRegion("enemyLaser");
    	
    	this.laserSound = laserSound;

        // Initialize player
        playerShip = new PlayerShip(playerShipTexture, playerLaserTexture, WORLD_WIDTH / 2, WORLD_HEIGHT / 2, viewport);
        playerLasers = new LinkedList<>();

        // Initialize enemies
        enemyShips = new LinkedList<>();
        enemyLasers = new LinkedList<>();
        powerUps = new LinkedList<>();

        // Initialize enemy spawn values
        this.timeBetweenEnemiesSpawn = timeBetweenEnemiesSpawn;
        this.timeSinceEnemySpawned = 0;
        this.maxEnemiesOnScreen = maxEnemiesOnScreen;

        // Initialize power up spawn values
        this.timeSincePowerUpSpawned = 0;

        // Set the viewport
        this.viewport = viewport;
    }

    // Method to update the game model
    public void updateModel(float deltaTime) {
        // Update timers
        timeSincePowerUpSpawned += deltaTime;
        timeSinceEnemySpawned += deltaTime;

        // Update playerShip
        playerShip.update(deltaTime);

        // Update enemyShips
        updateEnemyShips(enemyShips, deltaTime);

        // Update power ups
        updatePowerUps(deltaTime);

        // Update player and enemy lasers
        updateLasers(deltaTime);

        // Spawn new EnemyShips
        if (timeSinceEnemySpawned >= timeBetweenEnemiesSpawn &&
                enemyShips.size() < maxEnemiesOnScreen) {
            spawnEnemyShip();
            timeSinceEnemySpawned = 0;
        }

        // Spawn new PowerUps
        if (timeSincePowerUpSpawned >= 10) {
            spawnPowerUp();
            timeSincePowerUpSpawned = 0;
        }
    }

    // Helper method to update ships
    private void updateEnemyShips(List<Ship> enemyShips, float deltaTime) {
        for (Ship enemyShip : enemyShips) {
            enemyShip.update(deltaTime);
        }
    }

    // Helper method to update power-ups
    private void updatePowerUps(float deltaTime) {
        Iterator<PowerUps> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUps powerUp = powerUpIterator.next();
            powerUp.update(deltaTime);
            if (powerUp.isExpired()) {
                powerUpIterator.remove(); // Remove expired power ups
            } else {
                checkPowerUpCollision(powerUp);
            }
        }
    }

    // Helper method to update lasers
    private void updateLasers(float deltaTime) {

        // Update player lasers
        Iterator<Laser> laserIterator = playerLasers.iterator();
        while (laserIterator.hasNext()) {
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

        // Update enemies lasers
        Iterator<Laser> enemyLaserIterator = enemyLasers.iterator();
        while (enemyLaserIterator.hasNext()) {
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
    }

    // Helper method to check collision between power-up and player ship
    private void checkPowerUpCollision(PowerUps powerUp) {
        if (playerShip.getBoundingRectangle().overlaps(powerUp.getBoundingRectangle())) {
            powerUpCollected(powerUp);
        }
    }

    // Helper method to handle power-up collection
    private void powerUpCollected(PowerUps powerUp) {
        powerUps.remove(powerUp); // Remove the power-up after collecting
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

    // Method to fire a laser from the player ship
    public void firePlayerLaser() {
        if (playerShip.isGunUpgraded()) {
            // If the player ship's gun is upgraded, shoot bursts of lasers
            int burstSize = 3; 
            for (int i = 0; i < burstSize; i++) {
                Laser laser = playerShip.fireLaser(playerLasers); // Pass playerLasers list
                if (laser != null) {
                    playerLasers.add(laser);
                    laserSound.play();
                }
            }
        } else {
            // If the gun is not upgraded, fire a single laser
            Laser laser = playerShip.fireLaser(playerLasers); 
            if (laser != null) {
                playerLasers.add(laser);
                laserSound.play();
            }
        }
    }
    
    

    // Method to fire a laser from a single enemy
    public void fireEnemyLaser(Ship enemyShip) {
        Laser laser = enemyShip.fireLaser(enemyLasers); 
        if (laser != null) {
            enemyLasers.add(laser);
            laserSound.play();
        }
    }

    // Method to spawn a power up
    private void spawnPowerUp() {
        // Randomly select a position for the power up
        float powerUpX = MathUtils.random(0, WORLD_WIDTH - 20);
        float powerUpY = MathUtils.random(0, WORLD_HEIGHT - 20);

        // Randomly select a power up type
        String[] powerUpTypes = { "life", "gun", "shield", "blast" };
        String powerUpType = powerUpTypes[MathUtils.random.nextInt(powerUpTypes.length)];

        // Creating the power up
        PowerUps powerUp = new PowerUps(powerUpX, powerUpY, powerUpType, 5);

        // Adding the power up to the list
        powerUps.add(powerUp);
    }

    private void spawnEnemyShip() {
    	Ship enemyShip = new BasicEnemyShip(basicEnemyShipTexture, basicEnemyLaserTexture, 0, 0, viewport);
        int randNum = MathUtils.random(0, 3);

        float posX = 0;
        float posY = 0;

        switch (randNum) {
            case 0:
                posX = 0 - enemyShip.getWidth();
                posY = MathUtils.random(0, WORLD_HEIGHT);
                break;
            case 1:
                posX = WORLD_WIDTH + enemyShip.getWidth();
                posY = MathUtils.random(0, WORLD_HEIGHT);
                break;
            case 2:
                posX = MathUtils.random(0, WORLD_WIDTH);
                posY = 0 - enemyShip.getHeight();
                break;
            case 3:
                posX = MathUtils.random(0, WORLD_WIDTH);
                posY = WORLD_HEIGHT + enemyShip.getHeight();
                break;
        
            default:
                break;
        }

        
        enemyShip.setX(posX);
        enemyShip.setY(posY);
        enemyShips.add(enemyShip);
    }


    // Check if the generated position is valid
    private boolean isValidEnemyShipPosition(float x, float y) {
        float minDistanceToPlayer = 150; // Minimum distance from player ship
        float minDistanceToEnemy = 100; // Minimum distance from other enemy ships

        // Check distance from player ship
        float playerShipX = playerShip.getX();
        float playerShipY = playerShip.getY();
        float distanceToPlayer = new Vector2(playerShipX, playerShipY).dst(x, y);
        if (distanceToPlayer < minDistanceToPlayer) {
            return false;
        }

        // Check distance from other enemy ships
        for (Ship enemyShip : enemyShips) {
            if (enemyShip.getX() == x && enemyShip.getY() == y) {
                continue; // Skip comparison with itself
            }
            float distanceToEnemy = new Vector2(enemyShip.getX(), enemyShip.getY()).dst(x, y);
            if (distanceToEnemy < minDistanceToEnemy) {
                return false;
            }
        }

        return true;
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

    public Ship getShip() {
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