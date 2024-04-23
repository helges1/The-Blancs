package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



public class AsteroidTest {
	
	private Asteroid asteroid;
	
	private final Texture texture = mock(Texture.class);
	@SuppressWarnings("unchecked")
	private final Animation<TextureRegion> animation = mock(Animation.class);
	
	@BeforeEach
	public void setup() {
		float xPos = 0;
		float yPos = 100;
		Vector2 velocity = new Vector2(0, -1);
//		AsteroidType type = AsteroidType.Asteroid1;
		float size = 50;
		TextureRegion textureRegion = new TextureRegion(texture);
		
		Mockito.when(texture.getWidth()).thenReturn((int) size);
        Mockito.when(texture.getHeight()).thenReturn((int) size);
		Mockito.when(animation.getKeyFrame(0)).thenReturn(textureRegion);
        
        
		asteroid = new WAsteroid(textureRegion, animation, xPos, yPos, velocity, size);
	}
	
	@Test
	public void isOffScreenTest() {
		float worldHeight = 100;
		asteroid.update(50);
		assertFalse(asteroid.isOffScreen(worldHeight), "The asteroid should not be off screen yet.");
		// The size of the asteroid is 50 -> it's off screen when its yPos + its size < 0
		asteroid.update(101);
		assertTrue(asteroid.isOffScreen(worldHeight), "The asterod should be off screen by now.");
	}

}
