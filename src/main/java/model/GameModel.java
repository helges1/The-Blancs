package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameModel {

    // Player
    private Ship playerShip;
    private List<Laser> playerLasers; // Player lasers
    private float playerLaserSpeed;

    // Enemies
    private List<Ship> enemyShips;
    private List<Laser> enemyLasers;
    private float enemyLaserSpeed;

    // World values for boundaries or other purposes
    public final static float WORLD_WIDTH = 800;
    public final static float WORLD_HEIGHT = 600;

    private final static float TIME_BETWEEN_ENEMY_SPAWNS = 5; // 5000 ms = 5 s.
    private float timeSinceEnemySpawned;
    private final static int MAX_ENEMIES = 5;

    // Textures for game entities
    private Texture playerShipTexture;
    private Texture playerLaserTexture; // Added missing laserTexture declaration
    private Texture enemyShipTexture;
    private Texture enemyLaserTexture;

    public GameModel(){
        // Load textures
        playerShipTexture = new Texture("pictures/playerShip.png");
        playerLaserTexture = new Texture("pictures/playerLaser.png");
        enemyShipTexture = new Texture("pictures/enemyShip.png");
        enemyLaserTexture = new Texture("pictures/enemyLaser.png");

        // Initialize player
        playerShip = new Ship(playerShipTexture); // Updated to pass this GameModel instance
        playerLasers = new LinkedList<>(); // Bør være LinkedList, for da kan man fjerne elementer fra midten av listen uten større kost
        playerLaserSpeed = 600;
        // Initialize enemies
        enemyShips = new LinkedList<>();
        enemyLasers = new LinkedList<>();
        enemyLaserSpeed = 450; // Eksempelspeed
        timeSinceEnemySpawned = 0;
    }

    public void updateModel(float deltaTime) {
        timeSinceEnemySpawned += deltaTime;
        playerShip.update(deltaTime);
        for (Ship ship : enemyShips)
        	ship.update(deltaTime);

        if (timeSinceEnemySpawned >= TIME_BETWEEN_ENEMY_SPAWNS &&
                enemyShips.size() <= MAX_ENEMIES) {
            spawnEnemyShip();
            timeSinceEnemySpawned = 0;
            System.out.println("Enemy spawned");
        }
        fireEnemyLasers();


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
                        break; // Break to avoid ConcurrentModificationException
                    }
                }
            }
        }


    }
    
    public void firePlayerLaser() {
		Laser laser = playerShip.fireLaser(playerLaserTexture, playerLaserSpeed);
		if (laser != null) 
			playerLasers.add(laser);
	}

    private void fireEnemyLasers() {
        //TODO: for each enemyShip, fire laser if it's time for it to shoot
        for (Ship ship : enemyShips) {
        	Laser laser = ship.fireLaser(enemyLaserTexture, enemyLaserSpeed);
        	if (laser != null) {
        		enemyLasers.add(laser);
        		System.out.println("Enemy fired");
        	}
        }
        
    }


    private void spawnEnemyShip() {

        // Define boundaries for enemy ship spawn, spawns along the bounderies of the game world
        float boundaryOffset = 40;
        float minX = boundaryOffset;
        float maxX = WORLD_WIDTH - enemyShipTexture.getWidth() - boundaryOffset;
        float minY = WORLD_HEIGHT / 2 + boundaryOffset;
        float maxY = WORLD_HEIGHT - enemyShipTexture.getHeight() - boundaryOffset;

        // Ensure minX and minY are not negative after applying offset
        minX = Math.max(minX, 0);
        minY = Math.max(minY, WORLD_HEIGHT / 2);

        // Generate random x and y positions within adjusted bounds
        float enemyShipX = MathUtils.random(minX, maxX);
        float enemyShipY = MathUtils.random(minY, maxY);

        // Creating the enemy ship
        Ship enemyShip = new Ship(enemyShipTexture, enemyShipX, enemyShipY, 40, 40,
        		100, enemyLaserSpeed, 2.3f, null);

        // Adding the enemy ship to the list
        enemyShips.add(enemyShip);
    }

    public List<Ship> getEnemyShips() {
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

    public void addLaser(Laser laser) {
        playerLasers.add(laser); // Method to add a laser to the list
    }

    // Method to get laser texture
    public Texture getLaserTexture() {
        return playerLaserTexture;
    }

    // Method to return a default laser speed
    public float getLaserSpeed() {
        return 600; // Example speed
    }

	
}