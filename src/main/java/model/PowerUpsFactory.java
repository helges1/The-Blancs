package model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PowerUpsFactory {

    private TextureAtlas atlas;

    public PowerUpsFactory(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public PowerUps createPowerUp(PowerUps.PowerUpType powerUpType, float xPos, float yPos, float powerUpDuration) {
        TextureRegion powerUpTexture = atlas.findRegion(powerUpType.getTextureName());
        return new PowerUps(powerUpTexture, xPos, yPos, powerUpType, powerUpDuration);
    }
}
