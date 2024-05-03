package powerUps;

import org.junit.jupiter.api.Test;

import model.powerUps.PowerUps;

import static org.junit.jupiter.api.Assertions.*;

public class powerUpsTest {

    @Test
    public void testGetTextureName() {
        assertEquals("powerup-shield", PowerUps.PowerUpType.SHIELD.getTextureName());
        assertEquals("powerup-life", PowerUps.PowerUpType.LIFE.getTextureName());
        assertEquals("powerup-blast", PowerUps.PowerUpType.BLAST.getTextureName());
        assertEquals("powerup-gun", PowerUps.PowerUpType.GUN.getTextureName());
    }

    @Test
    public void testGetPowerUpName() {
        assertEquals("Shield Activated", PowerUps.PowerUpType.SHIELD.getPowerUpName());
        assertEquals("Extra Life", PowerUps.PowerUpType.LIFE.getPowerUpName());
        assertEquals("Blast Activated", PowerUps.PowerUpType.BLAST.getPowerUpName());
        assertEquals("Gun Upgraded", PowerUps.PowerUpType.GUN.getPowerUpName());
    }
}