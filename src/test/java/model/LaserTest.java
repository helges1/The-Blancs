package model;
import com.badlogic.gdx.graphics.Texture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class LaserTest {

    @Mock
    private Texture texture;

    private Laser laser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(texture.getWidth()).thenReturn(100);
        when(texture.getHeight()).thenReturn(100);

        // Create a real TextureRegion backed by a mocked Texture
        TextureRegion textureRegion = new TextureRegion(texture);

        Vector2 position = new Vector2(100, 100);
        float speed = 200;
        float angle = 90;  

        // Initialize the laser with a texture, position, speed, and angle
        laser = new Laser(textureRegion, position, speed, angle);
    }

    @Test
    public void testLaserInitialization() {
        assertEquals(100, laser.getX());
        assertEquals(100, laser.getY());
        assertEquals(90, laser.getRotation());

        // Adjusting expected values based on the angle-to-velocity calculation
        Vector2 expectedVelocity = new Vector2(-200, 0);  // Cosine and sine of -180 degrees given the speed
        assertEquals(expectedVelocity.x, laser.getVelocity().x, 0.01);
        assertEquals(expectedVelocity.y, laser.getVelocity().y, 0.01);
    }

    @Test
    public void testWindForceApplication() {
        Vector2 windForce = new Vector2(10, 5);
        laser.setWindForce(windForce);

        float deltaTime = 1;  // Simulate one second for simplicity
        laser.update(deltaTime);

        // Expected new position calculation: original position + (velocity + windForce) * deltaTime
        Vector2 expectedPosition = new Vector2(
        100 + (-190) * deltaTime,  // initial x + (velocity.x + windForce.x) * deltaTime
        100 + (5) * deltaTime       // initial y + (velocity.y + windForce.y) * deltaTime
        );

        assertEquals(expectedPosition.x, laser.getX(), 0.01, "Expected and actual X positions should match.");
        assertEquals(expectedPosition.y, laser.getY(), 0.01, "Expected and actual Y positions should match.");
}

    @Test
    public void testOffScreenCheck() {
        // Set the y position to a value above the world height
        float worldHeight = 600;
        laser.setPosition(laser.getX(), 601);  // Slightly above worldHeight

        assertTrue(laser.isOffScreen(worldHeight));
    }
}

