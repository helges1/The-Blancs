package model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.MathUtils;

import model.PowerUps.PowerUpType;

public class PowerUpsFactory {

    private TextureAtlas atlas;

    public PowerUpsFactory(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public PowerUps createPowerUp(float powerUpDuration) {
        PowerUpType powerUpType = PowerUpType.getRandomPowerUpType();
        float xPos = MathUtils.random(0, GameModel.WORLD_WIDTH - powerUpType.getWidth());
        float yPos = MathUtils.random(0, GameModel.WORLD_HEIGHT - powerUpType.getHeight());
        TextureRegion powerUpTexture = atlas.findRegion(powerUpType.getTextureName());
        return new PowerUps(powerUpTexture, xPos, yPos, powerUpType, powerUpDuration);
    }
}
