package model;

import org.w3c.dom.Text;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerUps extends Sprite{

    private final float powerUpWidth = 100;
    private final float powerUpHeight = 100;
    private float powerUpDuration;
    private float xPos, yPos;

    private String powerUpType;
    private Texture powerUpTexture;
    private boolean isCollected = false;

    public PowerUps(float xPos, float yPos, String powerUpType, float powerUpDuration) {
        this.powerUpType = powerUpType;
        this.powerUpDuration = powerUpDuration;
        this.xPos = xPos;
        this.yPos = yPos;

        try {
            powerUpTexture = new Texture("pictures/powerup-" + powerUpType + ".png");
            this.setTexture(powerUpTexture);
            this.setSize(powerUpWidth, powerUpHeight); // Set the sprite to a specific size
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid powerUpType: " + powerUpType);
        }
    }


    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public float getPowerUpDuration() {
        return powerUpDuration;
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
