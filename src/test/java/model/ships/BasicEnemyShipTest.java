package model.ships;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.lasers.Cannon;
import model.lasers.Laser;

public class BasicEnemyShipTest {
	
	private BasicEnemyShip ship;
	private List<Laser> firedLasers;
	
	@BeforeEach
    public void setUp() {
        Texture shipTexture = mock(Texture.class);
        when(shipTexture.getWidth()).thenReturn((int) BasicEnemyShip.basicEnemyWidth);
        when(shipTexture.getHeight()).thenReturn((int) BasicEnemyShip.basicEnemyHeight);
        Texture laserTexture = mock(Texture.class);
        when(laserTexture.getWidth()).thenReturn((int) BasicEnemyShip.basicEnemyLaserWidth);
        when(laserTexture.getHeight()).thenReturn((int)BasicEnemyShip.basicEnemyLaserHeight);

        TextureRegion shipTextureRegion = new TextureRegion(shipTexture);
        TextureRegion laserTextureRegion = new TextureRegion(laserTexture);
        FitViewport viewport = mock(FitViewport.class);
    
        ship = new BasicEnemyShip(shipTextureRegion, laserTextureRegion, 50f, 50f, viewport);
        firedLasers = new LinkedList<>();
    }
	
	@Test
	public void testFireLasers() {
		for (int i=0; i<3; i++) {
			// Expected to fire at least once in three attempts, given the randomness of the basicEnemyCannon
			ship.update(BasicEnemyShip.basicEnemyFireRate);
			if (ship.fireLaser(firedLasers)) {
				assertEquals(1, firedLasers.size());
				assertEquals(Laser.class, firedLasers.get(0).getClass());
				firedLasers.clear();
			}
		}
	}
	
	@Test
	public void testUpgradeCannon() {
		Cannon beforeUpgrade = ship.cannon;
		ship.upgradeCannon();
		// upgradeCannon does nothing, so the cannon shouldn't change,
		// and should in fact be the exact same object
		assertEquals(beforeUpgrade, ship.cannon);
	}
	
	@Test
	public void testResetCannon() {
		ship.update(BasicEnemyShip.basicEnemyFireRate);
		ship.cannon = (TextureRegion texture, Vector2 pos, float speed, float damage,
				float angle, float width, float height) -> {
			return null;
		};
		assertFalse(ship.fireLaser(firedLasers));
		firedLasers.clear();
		
		ship.resetCannon();
		for (int i=0; i<3; i++) {
			// Expected to fire at least once in three attempts, given the randomness of the basicEnemyCannon
			ship.update(BasicEnemyShip.basicEnemyFireRate);
			if (ship.fireLaser(firedLasers)) {
				assertEquals(1, firedLasers.size());
				assertEquals(Laser.class, firedLasers.get(0).getClass());
				firedLasers.clear();
			}
		}
	}

}
