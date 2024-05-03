package model.powerUps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * This class is used to represent PowerUps that can be acquired in the game.
 */
public class PowerUps extends Sprite {

    /**
     * An enum containing the different types of PowerUps.
     */
    public enum PowerUpType {
        SHIELD("powerup-shield", "Shield Activated"),
        LIFE("powerup-life", "Extra Life"),
        BLAST("powerup-blast", "Blast Activated"),
        GUN("powerup-gun", "Gun Upgraded");

        public static final PowerUpType NONE = null;
        private final String textureName;
        private final String powerUpName;

        PowerUpType(String textureName, String powerUpName) {
            this.textureName = textureName;
            this.powerUpName = powerUpName;
        }

        public String getTextureName() {
            return textureName;
        }

        public String getPowerUpName() {
            return powerUpName;
        }

        public float getWidth() {
            return 100;
        }

        public float getHeight() {
            return 100;
        }
        /**
         * Get a random PowerUpType.
         * @return A random PowerUpType.
         */
        public static PowerUpType getRandomPowerUpType() {
            PowerUpType[] powerUpTypes = PowerUpType.values();
            return powerUpTypes[MathUtils.random.nextInt(powerUpTypes.length)];
        }
    }

    private final float size = 100;
    private float powerUpDuration;
    private boolean isCollected = false;
    private PowerUpType powerUpType;
    private float xPos = 0;
    private float yPos = 0;

    /**
     * Constructor for the PowerUps class.
     * @param powerUpTexture The texture of the PowerUp.
     * @param xPos The x-coordinate of the PowerUp.
     * @param yPos The y-coordinate of the PowerUp.
     * @param powerUpType The type of the PowerUp.
     * @param powerUpDuration The duration of the PowerUp.
     */
    PowerUps(TextureRegion powerUpTexture, float xPos, float yPos, PowerUpType powerUpType, float powerUpDuration) {
        super(powerUpTexture);
        this.powerUpType = powerUpType;
        this.powerUpDuration = powerUpDuration;
        this.xPos = xPos;
        this.yPos = yPos;
        this.setPosition(xPos, yPos);
        this.setSize(size, size);
    }
    /**
     * Get the type of the PowerUp.
     * @return The type of the PowerUp.
     */
    public PowerUpType getPowerUpType() {
        return powerUpType;
    }
    /**
     * Checks if the PowerUp has been collected.
     * @return True if the PowerUp has been collected, false otherwise.
     */
    public boolean isCollected() {
        return isCollected;
    }
    /**
     * Set the PowerUp as collected.
     */
    public void setCollected(boolean collected) {
        isCollected = collected;
    }
    /**
     * Updates the powerUpDuration of the PowerUp.
     */
    public void update(float deltaTime) {
        powerUpDuration -= deltaTime;
    }
    /**
     * Checks if the PowerUp has expired.
     * @return True if the PowerUp has expired, false otherwise.
     */
    public boolean isExpired() {
        return powerUpDuration <= 0;
    }
    /**
     * Draws the PowerUp.
     * @param batch The SpriteBatch used to draw the PowerUp.
     */
    public void draw(SpriteBatch batch) {
        batch.draw(this, xPos, yPos);
    }
}
