package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
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

    // Constructor with position for enemyships
    public Ship(Texture texture, GameModel gameModel, float x, float y) {
        super(texture);
        this.gameModel = gameModel; // Store the gameModel reference
        // Store the viewport reference
        setSize(40, 40); // Set the size of the ship
        setPosition(x, y); // Set the initial position of the ship
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

    public void rotateShip(Vector2 rotateTowards) {
        // Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY()); // Burde dette heller bli gitt som argument fra Controller?
        viewport.unproject(rotateTowards);

        Vector2 shipPosition = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
        Vector2 direction = rotateTowards.sub(shipPosition);
        float angle = direction.angleDeg();

        setOriginCenter();
        setRotation(angle - 90);
    }

    // verdiene kan endres om det ønskes bedre plassering av laser
    private Vector2 getNosePositionOfShip() {
        float shipRotation = getRotation();

        float radians = (float)Math.toRadians(shipRotation);

        float laserCenterOffsetX = getWidth() * 0f;
        float laserCenterOffsetY = getHeight() * 1.2f ;

        float rotatedOffsetX = laserCenterOffsetX * MathUtils.cos(radians) - laserCenterOffsetY * MathUtils.sin(radians);
        float rotatedOffsetY = laserCenterOffsetX * MathUtils.sin(radians) + laserCenterOffsetY * MathUtils.cos(radians);

        float noseX = getX() + rotatedOffsetX + 2;
        float noseY = getY() + rotatedOffsetY - 6;

        return new Vector2(noseX, noseY);
    }

    public void fireLaser() {
        Vector2 position = getNosePositionOfShip();
        float angle = getRotation(); 
        Laser laser = new Laser(gameModel.getLaserTexture(), position, gameModel.getLaserSpeed(), angle);
        gameModel.addLaser(laser);
    }


    public void setViewport(FitViewport viewport) {
        this.viewport = viewport;

    }


    public void update(float deltaTime) {
    }
}
