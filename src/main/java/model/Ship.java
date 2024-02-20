package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

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

    // Additional methods for boundary checks, rotation, etc., can be added here
}