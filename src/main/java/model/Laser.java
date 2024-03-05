package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Laser extends Sprite {
    private Vector2 velocity;
    private float speed = 400; // Laser speed, adjust as needed

    public Laser(Texture texture, Vector2 startPosition) {
        super(texture);
        setSize(20, 20); // Adjust size as needed
        setPosition(startPosition.x - getWidth() / 2, startPosition.y); // Center the laser on the ship
        velocity = new Vector2(0, speed); // Assumes laser moves upwards
    }

    public void update(float deltaTime) {
        this.translateY(velocity.y * deltaTime);
    }
}
