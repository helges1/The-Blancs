package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class Ship extends Sprite {
    float speed = 200; // Example speed

    public Ship(Texture texture) {
        super(texture);
        setSize(40, 40); // Set the size of the ship
        setPosition(500, 500); // Set the initial position of the ship
    }

    public void moveUp(float deltaTime) {
        float newY = getY() + speed * deltaTime;
        if (newY + getHeight() > Gdx.graphics.getHeight()) { // Check upper bound
            newY = Gdx.graphics.getHeight() - getHeight();
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
        if (newX + getWidth() > Gdx.graphics.getWidth()) { // Check right bound
            newX = Gdx.graphics.getWidth() - getWidth();
        }
        setPosition(newX, getY());
    }
    // TODO: Implement better resize handling
    // check out LibGDX's Viewport it can help with this i think - Seb
    public void rotateShip() {
        // Get the mouse cursor's position
        Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        // Get the ship's position
        Vector2 shipPosition = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        // Calculate the angle between the ship's position and the mouse cursor
        Vector2 direction = mousePosition.sub(shipPosition);
        // Convert the angle to degrees
        float angle = direction.angleDeg();
        // Adjust the ship's origin to its center for proper rotation
        setOrigin(getWidth() / 2, getHeight() / 2);
        // Set the ship's rotation to face towards the cursor
        setRotation(angle - 90);
    }

    public Laser shootLaser() {
        // Calculate the starting position of the laser to be at the top center of the ship
        Vector2 startPosition = new Vector2(getX() + getWidth() / 2, getY() + getHeight());
        // Create a new Laser instance with the specified texture and starting position
        return new Laser(new Texture("pictures/laser.png"), startPosition);
    }




}