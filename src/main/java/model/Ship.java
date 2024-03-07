package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Ship extends Sprite {
    float speed = 200;
    private Viewport viewport; // Reference to the viewport
    private GameModel gameModel; // Reference to the gameModel

    public Ship(Texture texture, GameModel gameModel) {
        super(texture);
        this.gameModel = gameModel; // Store the gameModel reference
        // Store the viewport reference
        setSize(40, 40); // Set the size of the ship
        setPosition(500, 500); // Set the initial position of the ship
        setOriginCenter(); // Set origin to center for rotation
    }

    public void moveUp(float deltaTime) {
        float newY = getY() + speed * deltaTime;
        if (newY + getHeight() > viewport.getWorldHeight()) { // Check upper bound against viewport's world height
            newY = viewport.getWorldHeight() - getHeight();
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
        if (newX + getWidth() > viewport.getWorldWidth()) { // Check right bound against viewport's world width
            newX = viewport.getWorldWidth() - getWidth();
        }
        setPosition(newX, getY());
    }

    public void rotateShip() {
        Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY()); // Burde dette heller bli gitt som argument fra Controller?
        viewport.unproject(mousePosition); // Convert mouse coordinates to world coordinates

        Vector2 shipPosition = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        Vector2 direction = mousePosition.sub(shipPosition);
        float angle = direction.angleDeg();

        setOriginCenter();
        setRotation(angle - 90);
    }

    public void fireLaser() {
        // Assuming the Laser texture is loaded in GameModel or passed here directly
        Vector2 position = new Vector2(getX() + getWidth() / 2 - 5, getY() + getHeight()); // Adjust starting position
//        Vector2 position = getNosePositionOfShip(); where the laser should appear is based on the ships rotation
        
        float angle = getRotation();

        // Use the gameModel's laser texture and speed
        Laser laser = new Laser(gameModel.getLaserTexture(), position, gameModel.getLaserSpeed(), angle); // Kanskje dette burde være en metode i GameModel?
        gameModel.addLaser(laser);
    }
    
    private Vector2 getNosePositionOfShip() {
    	//TODO: implement
    	return null;
    }


    public void setViewport(FitViewport viewport) {
        this.viewport = viewport;

    }


    public void update(float deltaTime) {
    }
}
