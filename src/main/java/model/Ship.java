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
        this.translateY(speed * deltaTime);
    }

    public void moveDown(float deltaTime) {
        this.translateY(-speed * deltaTime);
    }

    public void moveLeft(float deltaTime) {
        this.translateX(-speed * deltaTime);
    }

    public void moveRight(float deltaTime) {
        this.translateX(speed * deltaTime);
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



}