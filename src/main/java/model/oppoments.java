package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class oppoments extends Sprite{
    private Vector2 velocity;
    private float speed = 100; // Example speed
    private GameModel gameModel; // Reference to the gameModel

    public void Opponent(Texture texture, GameModel gameModel) {

      //  super(texture);
        this.gameModel = gameModel; // Store the gameModel reference
        velocity = new Vector2(0, 0); // Initialize the velocity to (0, 0)
        setSize(40, 40); // Set the size of the opponent
        //setPosition(Math.random() * Gdx.graphics.getWidth(), Math.random() * Gdx.graphics.getHeight()); // Set the initial position of the opponent randomly
    }

    public void move(float deltaTime) {
        // Implement the logic for moving the opponent here
        // For example, you could set the velocity towards the player's ship and then update the position based on the velocity
    }
}
