package powerUps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import model.powerUps.BasicPowerUpsFactory;
import model.powerUps.PowerUps;

public class BasicPowerUpsFactoryTest {
    private BasicPowerUpsFactory basicPowerUpsFactory;
    private TextureAtlas atlas;

    @BeforeEach
    public void setUp() {
         // Mock the Gdx.files behavior
        Gdx.files = Mockito.mock(com.badlogic.gdx.Files.class);
        FileHandle fileHandle = Mockito.mock(FileHandle.class);
        Mockito.when(Gdx.files.internal("path/to/your/atlas")).thenReturn(fileHandle);

        atlas = new TextureAtlas(fileHandle);
        basicPowerUpsFactory = new BasicPowerUpsFactory(atlas);
    }

    @Test
    public void testCreatePowerUp() {
        float powerUpDuration = 5.0f;
        PowerUps powerUp = basicPowerUpsFactory.createPowerUp(powerUpDuration);

        assertNotNull(powerUp, "The created power up should not be null");
        assertNotNull(powerUp.getPowerUpType(), "The power up type should not be null");
        assertTrue(powerUpDuration <= powerUp.getRotation(), "The power up duration should be set correctly");
    }
}
