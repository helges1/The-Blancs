package model;

import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.ships.BasicEnemyShip;
import model.ships.PlayerShip;
import model.ships.Ship;
import model.Astroid.AstroidType;
import model.PowerUps.PowerUpType;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameModel {

    // Initialize player ship
    private Ship playerShip;

    // Initialize user name and score
    private String userName;
    private int score;

    // Initialize lists with objects
    private LinkedList<Ship> enemyShips;
    private LinkedList<Laser> enemyLasers;
    private LinkedList<PowerUps> powerUps;
    private LinkedList<Astroid> astroids;
    private LinkedList<Laser> playerLasers;

    // World values for boundaries or other purposes
    public final static float WORLD_WIDTH = 800;
    public final static float WORLD_HEIGHT = 600;

    // Initialize timers
    private float timeSincePowerUpSpawn;
    private float timeSinceAstroidSpawn;
    private float timeSinceEnemySpawn;

    // Initialize time values for spawning
    private float timeBetweenEnemiesSpawn;
    private float timeBetweenAstroidSpawn;
    private float timeBetweenPowerUpSpawn;

    // Initialize values for enemy spawning
    private int maxEnemiesOnScreen;

    // Keep track of destroyed enemy ships
    private int destroyedEnemyShipsCount = 0;

    // Game over boolean
    private boolean gameOver = false;

    // Initialize level
    private GameLevel currentLevel;

    // Viewport
    private FitViewport viewport;

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
            // Texture playerShipTexture, Texture playerLaserTexture,
            // Texture basicEnemyShipTexture, Texture basicEnemyLaserTexture,
            Sound laserSound, FitViewport viewport,
            String userName) {

        // Initialize field variables
        this.atlas = atlas;
        this.playerShipTexture = atlas.findRegion("playerShip");
        this.playerLaserTexture = atlas.findRegion("playerLaser");
        this.basicEnemyShipTexture = atlas.findRegion("basicEnemyShip");
        this.basicEnemyLaserTexture = atlas.findRegion("enemyLaser");

        // Initialize level
        this.currentLevel = GameLevel.LEVEL_1;

        // Initialize userName
        this.userName = userName;

        // Initialize sounds
        this.laserSound = laserSound;

        // Initialize player
        playerShip = new PlayerShip(playerShipTexture, playerLaserTexture, WORLD_WIDTH / 2, WORLD_HEIGHT / 2, viewport);
        playerLasers = new LinkedList<>();

        // Initialize enemies
        enemyShips = new LinkedList<>();
        enemyLasers = new LinkedList<>();
        powerUps = new LinkedList<>();
        astroids = new LinkedList<>();

        // Initialize enemy spawn values
        this.timeBetweenEnemiesSpawn = currentLevel.getEnemySpawnRate();
        this.timeBetweenAstroidSpawn = currentLevel.getAstroidSpawnRate();
        this.timeBetweenPowerUpSpawn = currentLevel.getPowerUpSpawnRate();

        // Initialize max enemies on screen
        this.maxEnemiesOnScreen = currentLevel.getMaxEnemiesOnScreen();

        // Initialize power up spawn values
        this.timeSincePowerUpSpawn = 0;
        this.timeSinceEnemySpawn = 0;
        this.timeSinceAstroidSpawn = 0;

        // Set the viewport
        this.viewport = viewport;
    }

    // Method to update the game model
    public void updateModel(float deltaTime) {
        // Update score
        updateScore();

        // Change level based on score
        changeLevel(score);

        // Update Level based variables
        timeBetweenEnemiesSpawn = currentLevel.getEnemySpawnRate();
        timeBetweenAstroidSpawn = currentLevel.getAstroidSpawnRate();
        timeBetweenPowerUpSpawn = currentLevel.getPowerUpSpawnRate();
        maxEnemiesOnScreen = currentLevel.getMaxEnemiesOnScreen();


        // Update timers
        timeSincePowerUpSpawn += deltaTime;
        timeSinceEnemySpawn += deltaTime;
        timeSinceAstroidSpawn += deltaTime;

        // Update playerShip
        playerShip.update(deltaTime);

        // Update enemyShips
        updateEnemyShips(enemyShips, deltaTime);

        // Update power ups
        updatePowerUps(deltaTime);

        // Update astroids
        updateAstroids(deltaTime);

        // Update player and enemy lasers
        updateLasers(deltaTime);

        // Spawn new EnemyShips
        if (timeSinceEnemySpawn >= timeBetweenEnemiesSpawn &&
                enemyShips.size() < maxEnemiesOnScreen) {
            spawnEnemyShip();
            timeSinceEnemySpawn = 0;
        }

        // Spawn new PowerUps
        if (timeSincePowerUpSpawn >= timeBetweenPowerUpSpawn) {
            spawnPowerUp();
            timeSincePowerUpSpawn = 0;
        }

        // Spawn new Astroids
        if (timeSinceAstroidSpawn >= timeBetweenAstroidSpawn) {
            spawnAstroids();
            timeSinceAstroidSpawn = 0;
        }
    }

    // Helper method to update ships
    private void updateEnemyShips(List<Ship> enemyShips, float deltaTime) {
        for (Ship enemyShip : enemyShips) {
            enemyShip.update(deltaTime);
        }
    }

    // Helper method to update score
    private void updateScore() {
        score += destroyedEnemyShipsCount * 10;
        resetDestroyedEnemyShipsCount();
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

    // Helper method to update astroids
    private void updateAstroids(float deltaTime) {
        Iterator<Astroid> astroidIterator = astroids.iterator();
        while (astroidIterator.hasNext()) {
            Astroid astroid = astroidIterator.next();
            astroid.update(deltaTime);
            if (astroid.isOffScreen(WORLD_HEIGHT)) {
                astroidIterator.remove(); // Remove off-screen astroids
            } else if (checkAstroidCollision(astroid)) {
                astroidIterator.remove(); // Remove the astroid if hit by a laser
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

                // Check for collisions with astroids
                Iterator<Astroid> astroidIterator = astroids.iterator();
                while (astroidIterator.hasNext()) {
                    Astroid astroid = astroidIterator.next();
                    if (checkCollision(laser, astroid)) {
                        astroidIterator.remove(); // Remove the astroid if hit by a laser
                        laserIterator.remove(); // Remove the laser after hitting the astroid
                        break; // Break to avoid ConcurrentModificationException
                    }
                }

            }
        }

        // Update enemies' lasers
        Iterator<Laser> enemyLaserIterator = enemyLasers.iterator();
        while (enemyLaserIterator.hasNext()) {
            Laser laser = enemyLaserIterator.next();

            if (playerShip.getActivePowerUp() == PowerUpType.BLAST) {
                Vector2 shipPosition = new Vector2(playerShip.getX(), playerShip.getY());
                Vector2 laserPosition = new Vector2(laser.getX(), laser.getY());

                float distance = shipPosition.dst(laserPosition); // Calculate distance between ship and laser

                if (distance <= 200) {
                    Vector2 directionFromShipToLaser = laserPosition.sub(shipPosition).nor(); // Direction from ship to
                                                                                              // laser, normalized
                    Vector2 windForceDirection = directionFromShipToLaser.scl(-1); // Reverse direction to push away

                    float windForceMagnitude = 10f; // Set wind force magnitude
                    Vector2 windforce = new Vector2(windForceDirection.x * windForceMagnitude,
                            windForceDirection.y * windForceMagnitude);
                    laser.setWindForce(windforce);
                }
            }

            laser.update(deltaTime);

            if (laser.isOffScreen(WORLD_HEIGHT)) {
                enemyLaserIterator.remove(); // Remove off-screen lasers
            } else {
                // Check for collisions with player ship
                if (checkCollision(laser, playerShip)) {
                    enemyLaserIterator.remove(); // Remove the laser after hitting the ship
                    playerShip.takeDamage(5); // Reduce player health
                    if (playerShip.isDestroyed()) {
                        // Game over handling
                        gameOver = true;
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

    private boolean checkAstroidCollision(Astroid astroid) {

        // Initialize collision detection to false
        boolean collisionDetected = false;

        // Calculate the centers of the player ship and the asteroid
        float playerCenterX = playerShip.getX() + playerShip.getWidth() / 2;
        float playerCenterY = playerShip.getY() + playerShip.getHeight() / 2;
        float astroidCenterX = astroid.getX() + astroid.getWidth() / 2;
        float astroidCenterY = astroid.getY() + astroid.getHeight() / 2;
    
        // Calculate the distance between the centers
        float distance = Vector2.dst(playerCenterX, playerCenterY, astroidCenterX, astroidCenterY);
        float sumRadii = playerShip.getWidth() / 2 + astroid.getWidth() / 2;
    
        // Check for collision with the player ship
        if (distance < sumRadii) {
            playerShip.takeDamage(10);
            collisionDetected = true;
            if (playerShip.isDestroyed()) {
                gameOver = true;
            }
        }
    
        // Check collisions with enemy ships
        Iterator<Ship> shipIterator = enemyShips.iterator();
        while (shipIterator.hasNext()) {
            Ship enemyShip = shipIterator.next();
            float enemyCenterX = enemyShip.getX() + enemyShip.getWidth() / 2;
            float enemyCenterY = enemyShip.getY() + enemyShip.getHeight() / 2;
    
            float distanceToEnemy = Vector2.dst(enemyCenterX, enemyCenterY, astroidCenterX, astroidCenterY);
            float sumRadiiEnemy = enemyShip.getWidth() / 2 + astroid.getWidth() / 2;
    
            if (distanceToEnemy < sumRadiiEnemy) {
                shipIterator.remove(); // Remove the enemy ship if hit by an asteroid
                collisionDetected = true;
            }
        }
        return collisionDetected;
    }
    

    // Helper method to change level based on score
    private void changeLevel(int score) {
        if (score >= 300 && score < 600) {
            currentLevel = GameLevel.LEVEL_2;
        } else if (score >= 600 && score < 900) {
            currentLevel = GameLevel.LEVEL_3;
        } else if (score >= 900) {
            currentLevel = GameLevel.LEVEL_4;
        }
    }

    // Helper method to handle power-up collection
    private void powerUpCollected(PowerUps powerUp) {
        powerUps.remove(powerUp); // Remove the power-up after collecting
        switch (powerUp.getPowerUpType()) {
            case LIFE:
                playerShip.addHealth();
                break;
            case GUN:
                playerShip.setActivePowerUp(PowerUpType.GUN);
                break;
            case SHIELD:
                playerShip.setActivePowerUp(PowerUpType.SHIELD);
                break;
            case BLAST:
                playerShip.setActivePowerUp(PowerUpType.BLAST);
                break;
        }
        playerShip.resetPowerUpTimer();
    }

    // Method to fire a laser from the player ship
    public void firePlayerLaser() {
        if (playerShip.getActivePowerUp() == PowerUpType.GUN) {
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
        PowerUpType[] powerUpTypes = PowerUpType.values();

        PowerUpType powerUpType = powerUpTypes[MathUtils.random.nextInt(powerUpTypes.length)];
        // Creating the power up
        PowerUps powerUp = new PowerUps(powerUpX, powerUpY, powerUpType, 5);

        // Adding the power up to the list
        powerUps.add(powerUp);
    }

    // Method to spawn a Astroid
    private void spawnAstroids() {
        // Randomly select a position for Astroid
        float astroidX = MathUtils.random(0 + 20, WORLD_WIDTH - 20);

        // Randomly select a Astroid type
        AstroidType[] astroidTypes = AstroidType.values();
        AstroidType powerUpType = astroidTypes[MathUtils.random.nextInt(astroidTypes.length)];

        // Create random size from 50 to 150
        int astroidSize = MathUtils.random(50, 150);
        // Creating the Astroid
        Astroid astroid = new Astroid(astroidX, new Vector2(0, -100), powerUpType, astroidSize);

        // Adding the power up to the list
        astroids.add(astroid);
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

    public void update() {
        for (Iterator<Ship> iterator = enemyShips.iterator(); iterator.hasNext();) {
            Ship enemyShip = iterator.next();
            if (enemyShip.getBoundingRectangle().overlaps(playerShip.getBoundingRectangle())) {
                playerShip.takeDamage(5);
                enemyShip.takeDamage(5);
                if (enemyShip.isDestroyed()) {
                    iterator.remove();
                }
                if (playerShip.isDestroyed()) {
                    gameOver = true;
                }
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

    public boolean checkCollision(Laser laser, Astroid astroid) {
        // Calculate the center position of the asteroid
        float astroidCenterX = astroid.getX() + astroid.getWidth() / 2;
        float astroidCenterY = astroid.getY() + astroid.getHeight() / 2;

        // Calculate the radius of the asteroid
        float astroidRadius = astroid.getHeight() / 2;

        // Calculate the position of the laser
        float laserCenterX = laser.getX() + laser.getWidth() / 2;
        float laserCenterY = laser.getY() + laser.getHeight() / 2;

        // Calculate the distance between the centers of the laser and the asteroid
        float distance = Vector2.dst(astroidCenterX, astroidCenterY, laserCenterX, laserCenterY);

        // Check if the distance is close to the radius of the asteroid
        float threshold = laser.getWidth() / 2;

        // Check if the laser hits near the circumference of the asteroid
        return Math.abs(distance - astroidRadius) <= threshold;
    }

    public Ship getPlayerShip() {
        return playerShip;
    }

    public FitViewport getViewport() {
        return viewport;
    }

    public LinkedList<Laser> getPlayerLasers() {
        return playerLasers;
    }

    public LinkedList<Laser> getEnemyLasers() {
        return enemyLasers;
    }

    public LinkedList<PowerUps> getPowerUps() {
        return powerUps;
    }

    public LinkedList<Astroid> getAstroids() {
        return astroids;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public int getScore() {
        return score;
    }

}