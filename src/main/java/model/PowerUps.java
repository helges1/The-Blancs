package model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class PowerUps extends Sprite {

    public enum PowerUpType {
        SHIELD("powerup-shield", "Shield Activated"),
        LIFE("powerup-life", "Extra Life"),
        BLAST("powerup-blast", "Blast Activated"),
        GUN("powerup-gun", "Gun Upgraded");

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

    public PowerUps(TextureRegion powerUpTexture, float xPos, float yPos, PowerUpType powerUpType, float powerUpDuration) {
        super(powerUpTexture);
        this.powerUpType = powerUpType;
        this.powerUpDuration = powerUpDuration;
        this.xPos = xPos;
        this.yPos = yPos;
        this.setPosition(xPos, yPos);
        this.setSize(size, size);
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
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

    public void draw(SpriteBatch batch) {
        batch.draw(this, xPos, yPos);
    }
}
