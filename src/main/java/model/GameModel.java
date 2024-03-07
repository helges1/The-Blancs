package model;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Iterator;

public class GameModel {

    private Ship playerShip;
    private ArrayList<Laser> lasers; // Player lasers

    // World values for boundaries or other purposes
    private final static float WORLD_WIDTH = 1080;
    private final static float WORLD_HEIGHT = 720;

    // Textures for game entities
    private Texture playerShipTexture;
    private Texture laserTexture; // Added missing laserTexture declaration

    public GameModel(){
        // Load textures
        playerShipTexture = new Texture("pictures/playerShip.png");
        laserTexture = new Texture("pictures/Laser.png");

        // Initialize player
        playerShip = new Ship(playerShipTexture, this); // Updated to pass this GameModel instance
        lasers = new ArrayList<>();
    }

    public void updateModel(float deltaTime) {
        // Update player ship
        playerShip.update(deltaTime);

        // Update all lasers
        Iterator<Laser> laserIterator = lasers.iterator();
        while(laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update(deltaTime);
            if (laser.isOffScreen(WORLD_HEIGHT)) {
                laserIterator.remove(); // Remove off-screen lasers
            }
        }
    }

    public Ship getShip(){
        return playerShip;
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public void addLaser(Laser laser) {
        lasers.add(laser); // Method to add a laser to the list
    }

    // Method to get laser texture
    public Texture getLaserTexture() {
        return laserTexture;
    }

    // Method to return a default laser speed
    public float getLaserSpeed() {
        return 300; // Example speed
    }
}
