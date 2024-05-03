package model.lasers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TestCannons {
	
	private TextureRegion laserTexture;
	private Vector2 position = new Vector2(20, 20);
	private float angle = 40;
	private float laserSpeed = 200;
	private float laserDamage = 10;
	private float laserWidth = 10;
	private float laserHeight = 20;
	
	@BeforeEach
	public void setup() {
		Texture texture = mock(Texture.class);
		Mockito.when(texture.getWidth()).thenReturn((int)laserWidth);
		Mockito.when(texture.getHeight()).thenReturn((int)laserHeight);
		laserTexture = new TextureRegion(texture);
	}
	
	@Test
	public void testBasicPlayerCannon() {
		Cannon cannon = Cannons::basicPlayerCannon;
		Laser expectedLaser = new Laser(laserTexture, position, laserSpeed, angle,
				laserWidth, laserHeight, laserDamage);
		expectedLaser.centreAtPoint(position);
		Laser[] expteced = new Laser[] {expectedLaser};
		Laser[] actual = cannon.fireCannon(laserTexture, position, laserSpeed, laserDamage, angle, laserWidth, laserHeight);
		assertEquals(1, actual.length);
		assertTrue(Arrays.deepEquals(expteced, actual));
	}
	
	@Test
	public void testUpgradedPlayerCannon() {
		Cannon cannon = Cannons::upgradedPlayerCannon;
		
		Laser expectedLaser1 = new Laser(laserTexture, position, laserSpeed, angle,
				laserWidth, laserHeight, laserDamage);
		expectedLaser1.centreAtPoint(position);
		Laser expectedLaser2 = new Laser(laserTexture, position, laserSpeed, angle-25,
				laserWidth, laserHeight, laserDamage);
		expectedLaser2.centreAtPoint(position);
		Laser expectedLaser3 = new Laser(laserTexture, position, laserSpeed, angle+25,
				laserWidth, laserHeight, laserDamage);
		expectedLaser3.centreAtPoint(position);
		Laser[] expected = new Laser[] {
				expectedLaser1, expectedLaser2, expectedLaser3};
		
		Laser[] actual = cannon.fireCannon(laserTexture, position, laserSpeed, laserDamage, angle, laserWidth, laserHeight);
		ArrayList<Laser> actualList = new ArrayList<>(Arrays.asList(actual));
		for (Laser expectedLaser : expected) {
			assertTrue(actualList.contains(expectedLaser));
		}
	}
	
	@Test
	public void testBasicEnemyCannon() {
		ArrayList<Laser> firedLasers = new ArrayList<>();
		Cannon cannon = Cannons::basicEnemyCannon;
		ArrayList<Laser> expected = new ArrayList<>();
		
		for (int i=0; i<3; i++) {
			Laser[] fired = cannon.fireCannon(laserTexture, position, laserSpeed, laserDamage, angle, laserWidth, laserHeight);
			if (fired != null) {
				firedLasers.add(fired[0]);
				// Whenever the cannon doesn't return null, add a laser to the expected list.
				Laser exptectedLaser = new Laser(laserTexture, position, laserSpeed, angle,
						laserWidth, laserHeight, laserDamage);
				exptectedLaser.centreAtPoint(position);
				expected.add(exptectedLaser);
			}
		}
		
		assertEquals(expected, firedLasers);
	}

}
