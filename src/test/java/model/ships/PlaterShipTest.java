package model.ships;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import model.lasers.Laser;

public class PlaterShipTest {
	
	private PlayerShip ship;
	private List<Laser> firedLasers;
	
	@BeforeEach
    public void setUp() {
        Texture shipTexture = mock(Texture.class);
        when(shipTexture.getWidth()).thenReturn((int) PlayerShip.playerWhidth);
        when(shipTexture.getHeight()).thenReturn((int) PlayerShip.playerHeight);
        Texture laserTexture = mock(Texture.class);
        when(laserTexture.getWidth()).thenReturn((int) PlayerShip.playerLaserWidth);
        when(laserTexture.getHeight()).thenReturn((int) PlayerShip.playerHeight);

        TextureRegion shipTextureRegion = new TextureRegion(shipTexture);
        TextureRegion laserTextureRegion = new TextureRegion(laserTexture);
        FitViewport viewport = mock(FitViewport.class);
    
        ship = new PlayerShip(shipTextureRegion, laserTextureRegion, 50f, 50f, viewport);
        firedLasers = new LinkedList<>();
    }
	
	@Test
	public void testFireLasers() {
		ship.update(PlayerShip.playerFireRate);
		assertTrue(ship.fireLaser(firedLasers));
		assertEquals(1, firedLasers.size());
		assertEquals(Laser.class, firedLasers.get(0).getClass());
	}
	
	@Test
	public void testUpgradeCannon() {
		ship.update(PlayerShip.playerFireRate);
		ship.upgradeCannon();
		assertTrue(ship.fireLaser(firedLasers));
		assertEquals(3, firedLasers.size());
		for (int i=0; i<3; i++)
			assertEquals(Laser.class, firedLasers.get(i).getClass());
	}
	
	@Test
	public void testResetCannon() {
		ship.update(PlayerShip.playerFireRate);
		ship.cannon = (TextureRegion texture, Vector2 pos, float speed, float damage,
				float angle, float width, float height) -> {
			return null;
		};
		assertFalse(ship.fireLaser(firedLasers));
		firedLasers.clear();
		
		ship.resetCannon();
		ship.update(PlayerShip.playerFireRate);
		assertTrue(ship.fireLaser(firedLasers));
		assertEquals(1, firedLasers.size());
		assertEquals(Laser.class, firedLasers.get(0).getClass());
	}

}
