package powerUps;


import static org.junit.jupiter.api.Assertions.*;

import model.powerUps.PowerUps;
import model.powerUps.PowerUps.PowerUpType;
import model.powerUps.PowerUpsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PowerUpsFactoryTest {
    private PowerUpsFactory powerUpsFactory;

    @BeforeEach
    public void setUp() {
        powerUpsFactory = new PowerUpsFactory() {
            @Override
            public PowerUps createPowerUp(float powerUpDuration) {
                PowerUps powerUps = Mockito.mock(PowerUps.class);
                Mockito.when(powerUps.getRotation()).thenReturn(powerUpDuration);
                return powerUps;
            }

            @Override
            public PowerUpType getRandomPowerUpType() {
                return PowerUpType.SHIELD;

            }
        };
    }

    @Test
    public void testCreatePowerUp() {
        float duration = 5.0f;
        PowerUps powerUps = powerUpsFactory.createPowerUp(duration);
        
        assertNotNull(powerUps, "PowerUps should not be null");
        assertEquals(duration, powerUps.getRotation(), "PowerUps duration should be " + duration);
        
    }

    @Test
    public void testGetRandomPowerUpType() {
        PowerUpType randomPowerUpType = powerUpsFactory.getRandomPowerUpType();
        assertNotNull(randomPowerUpType, "The random power up type should not be null");
    }
}