package model;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerUps extends Sprite{

    public enum PowerUpType {
        SHIELD(new Texture("pictures/powerup-shield.png")),
        LIFE(new Texture("pictures/powerup-life.png")),
        BLAST(new Texture("pictures/powerup-blast.png")),
        GUN(new Texture("pictures/powerup-gun.png"));

        private final Texture powerUpTexture;

        PowerUpType(Texture powerUpTexture) {
            this.powerUpTexture = powerUpTexture;
        }

        public Texture getPowerUpTexture() {
            return powerUpTexture;
        }

    }



    private final float powerUpWidth = 100;
    private final float powerUpHeight = 100;
    private float powerUpDuration;
    private float xPos, yPos;

    private Texture powerUpTexture;
    private boolean isCollected = false;

    PowerUpType powerUpType;

    public PowerUps(float xPos, float yPos, PowerUpType powerUpType, float powerUpDuration) {
        this.powerUpType = powerUpType;
        this.powerUpDuration = powerUpDuration;
        this.xPos = xPos;
        this.yPos = yPos;

        try {
            powerUpTexture = powerUpType.getPowerUpTexture();
            this.setTexture(powerUpTexture);
            this.setSize(powerUpWidth, powerUpHeight); // Set the sprite to a specific size
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid powerUpType: " + powerUpType);
        }
    }


    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    public Texture getPowerUpTexture() {
        return powerUpTexture;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public void update(float deltaTime) {
        powerUpDuration -= deltaTime;
    }

    public boolean isExpired() {
        return powerUpDuration <= 0;
    }

    public void dispose() {
        powerUpTexture.dispose();
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(xPos, yPos, powerUpTexture.getWidth(), powerUpTexture.getHeight());
    }

    public void draw(SpriteBatch batch) {
        batch.draw(powerUpTexture, xPos, yPos);
    }
}
