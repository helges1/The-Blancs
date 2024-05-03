package model.powerUps;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.MathUtils;

import model.GameModel;
import model.powerUps.PowerUps.PowerUpType;

/**
 * A basic <code>PowerUpsFactory</code>
 */
public class BasicPowerUpsFactory implements PowerUpsFactory {

    private TextureAtlas atlas;

    /**
     * @param atlas to have access to the appropriate PowerUp-textures.
     */
    public BasicPowerUpsFactory(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    public PowerUps createPowerUp(float powerUpDuration) {
        PowerUpType powerUpType = PowerUpType.getRandomPowerUpType();
        float xPos = MathUtils.random(0, GameModel.WORLD_WIDTH - powerUpType.getWidth());
        float yPos = MathUtils.random(0, GameModel.WORLD_HEIGHT - powerUpType.getHeight());
        TextureRegion powerUpTexture = atlas.findRegion(powerUpType.getTextureName());
        return new PowerUps(powerUpTexture, xPos, yPos, powerUpType, powerUpDuration);
    }

    
    
}
