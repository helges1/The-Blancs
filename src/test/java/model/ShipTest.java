package model;

import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;

public class ShipTest {

    private Ship ship;
    private Texture texture; // Mocked Texture

    @BeforeEach
    public void setUp() {
        // Mock the Texture class
        texture = Mockito.mock(Texture.class);
        
        // Use the mocked Texture when creating a Ship instance
        ship = new Ship(texture);
    }

    @Test
    public void testMoveUp() {
        float initialY = ship.getY();
        ship.moveUp(1); // Move the ship up for 1 second
        assertEquals(initialY + ship.speed, ship.getY(), 0.1);
    }
    @Test
    public void testMoveDown() {
        float initialY = ship.getY();
        ship.moveDown(1); // Move the ship down for 1 second
        assertEquals(initialY - ship.speed, ship.getY(), 0.1);
    }

    @Test
    public void testMoveLeft() {
        float initialX = ship.getX();
        ship.moveLeft(1); // Move the ship left for 1 second
        assertEquals(initialX - ship.speed, ship.getX(), 0.1);
    }

    @Test
    public void testMoveRight() {
        float initialX = ship.getX();
        ship.moveRight(1); // Move the ship right for 1 second
        assertEquals(initialX + ship.speed, ship.getX(), 0.1);
    }

}
    
