package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



public class AsteroidTest {
	
	private Asteroid asteroid;
	
	private final Texture texture = mock(Texture.class);
	TextureRegion textureRegion = new TextureRegion(texture);
	@SuppressWarnings("unchecked")
	private final Animation<TextureRegion> animation = mock(Animation.class);
	
	private void setup(float xPos, float yPos, float size, Vector2 velocity) {
//		float xPos = 0;
//		float yPos = 100;
//		Vector2 velocity = new Vector2(0, -1);
////		AsteroidType type = AsteroidType.Asteroid1;
//		float size = 50;
		
		Mockito.when(texture.getWidth()).thenReturn((int) size);
        Mockito.when(texture.getHeight()).thenReturn((int) size);
		Mockito.when(animation.getKeyFrame(0)).thenReturn(textureRegion);
        
        
		asteroid = new WAsteroid(textureRegion, animation, xPos, yPos, velocity, size);
	}
	
	@Test
	public void isOffScreenTest() {
		Vector2 velocity = new Vector2(0, -1);
		setup(0, 100, 50, velocity);
		
		float worldHeight = 100;
		asteroid.update(50);
		assertFalse(asteroid.isOffScreen(worldHeight), "The asteroid should not be off screen yet.");
		// The size of the asteroid is 50 -> it's off screen when its yPos + 50 < 0
		asteroid.update(100);
		assertFalse(asteroid.isOffScreen(worldHeight), "The asteroid should not be off screen yet.");
		asteroid.update(1);
		assertTrue(asteroid.isOffScreen(worldHeight), "The asterod should be off screen by now.");
	}
	
	@Test
	public void moveAsteroidVerticallyTest() {
		Vector2 velocity = new Vector2(0, -1);
		setup(0, 100, 50, velocity);
		//Assert the initial position of the asteroid
		assertEquals(0, asteroid.getX());
		assertEquals(100, asteroid.getY());
		
		asteroid.update(50);
		assertEquals(0, asteroid.getX()); //The x-axis of the velocity vector is zero
		assertEquals(50, asteroid.getY());
	}
	
	@Test
	public void moveAsteroidHorisontallyTest() {
		Vector2 velocity = new Vector2(1, 0);
		setup(0, 100, 50, velocity);
		//Assert the initial position of the asteroid
		assertEquals(0, asteroid.getX());
		assertEquals(100, asteroid.getY());
		
		asteroid.update(50);
		assertEquals(50, asteroid.getX());
		assertEquals(100, asteroid.getY()); //The y-axis of the velocity vector is zero
	}
	
	@Test
	public void moveAsteroidDiagonallyTest() {
		Vector2 velocity = new Vector2(1, -1);
		setup(0, 100, 50, velocity);
		//Assert the initial position of the asteroid
		assertEquals(0, asteroid.getX());
		assertEquals(100, asteroid.getY());
		
		asteroid.update(50);
		assertEquals(50, asteroid.getX()); //The x-axis of the velocity vector is zero
		assertEquals(50, asteroid.getY());
	}


}
