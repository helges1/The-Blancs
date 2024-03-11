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
    
    // Enemies
    private List<Ship> enemyShips;
    private List<Laser> enemyLasers;

    // World values for boundaries or other purposes
    private final static float WORLD_WIDTH = 1080;
    private final static float WORLD_HEIGHT = 720;
    
    private final static float TIME_BETWEEN_ENEMY_SPAWNS = 5000; // 5000 ms = 5 s.
    private float timeSinceEnemySpawned;
    private final static int MAX_ENEMIES = 5;

    // Textures for game entities
    private Texture playerShipTexture;
    private Texture playerLaserTexture; // Added missing laserTexture declaration
	private Texture enemyShipTexture;
	private Texture enemyLaserTexture;

    public GameModel(){
        // Load textures
        playerShipTexture = new Texture("the-blancs/src/main/resources/pictures/playerShip.png");
        playerLaserTexture = new Texture("the-blancs/src/main/resources/pictures/playerLaser.png");
        enemyShipTexture = new Texture("the-blancs/src/main/resources/pictures/enemyShip.png");
        enemyLaserTexture = new Texture("the-blancs/src/main/resources/pictures/enemyLaser.png");

        // Initialize player
        playerShip = new Ship(playerShipTexture, this); // Updated to pass this GameModel instance
        playerLasers = new LinkedList<>(); // Bør være LinkedList, for da kan man fjerne elementer fra midten av listen uten større kost
        // Initialize enemies
        enemyShips = new LinkedList<>();
        enemyLasers = new LinkedList<>();
        timeSinceEnemySpawned = 0;
    }

    public void updateModel(float deltaTime) {
    	timeSinceEnemySpawned += deltaTime;
    	// Update enemy ships
    	if (timeSinceEnemySpawned >= TIME_BETWEEN_ENEMY_SPAWNS &&
    			enemyShips.size() <= MAX_ENEMIES) {
    		spawnEnemyShip();
    		timeSinceEnemySpawned = 0;
            System.out.println("Enemy spawned");
    	}
    	fireEnemyLasers(deltaTime);
    	
        // Update player ship
        playerShip.update(deltaTime); 

        // Update all lasers
        Iterator<Laser> laserIterator = playerLasers.iterator();
        while(laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update(deltaTime);
            if (laser.isOffScreen(WORLD_HEIGHT)) {
                laserIterator.remove(); // Remove off-screen lasers
            }
        }

    }

    private void fireEnemyLasers(float deltaTime) {
		//TODO: for each enemyShip, fire laser if it's time for it to shoot
    	for (Ship ship : enemyShips) {
    		ship.fireLaser(); // Må legge til funksjonalitet slik at de ikke skyter hele tiden. 
    		                  // Bør ha egen klasse for playerShip og enemyShip som utvider Ship.
    	}
	}

    private void spawnEnemyShip() {

        // Planen er å lage egen klasse for enemyShip, som utvider Ship.
        // Da må denne metoden endres til å passe det.
        

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
        Ship enemyShip = new Ship(enemyShipTexture, this, enemyShipX, enemyShipY); 

        // Adding the enemy ship to the list
        enemyShips.add(enemyShip);
    }

    public List<Ship> getEnemyShips() {
        return enemyShips;
    }

	public Ship getShip(){
        return playerShip;
    }

    public List<Laser> getLasers() {
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
