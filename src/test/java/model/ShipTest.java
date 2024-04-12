package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import model.ships.Ship;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.mockito.Mockito;


public class ShipTest {

    private Ship ship;
    private Texture texture;
    private float xPos = 250;
    private float yPos = 250;
    private float width = 50;
    private float height = 50;
    private float speed = 200;
    private float health = 100;

   
    @BeforeEach
    public void setUp() {
        texture = Mockito.mock(Texture.class);
        Mockito.when(texture.getWidth()).thenReturn((int) width);
        Mockito.when(texture.getHeight()).thenReturn((int) height);
    
        TextureRegion textureRegion = new TextureRegion(texture);
        Viewport testViewport = new FitViewport(800, 600); // Or mock this as well
    
        // Create a Ship instance for testing
        ship = new Ship(textureRegion, xPos, yPos, width, height, speed, health, testViewport) {
            @Override
            public void update(float delta) {
                // Do nothing for now
            }
    
            @Override
            public Laser fireLaser(List<Laser> lasers) {
                return null; // Do nothing for now
            }
        };
    }
    
     
    @Test
    public void testMoveUp() {
        float initialX = ship.getY();
        ship.moveUp(1); // Move the ship up for 1 second
        assertEquals(initialX + ship.speed , ship.getY(), 0.1);
    }
    @Test
    public void testMoveDown() {
        float initialY = ship.getY();
        ship.moveDown(1); // Move the ship down for 1 second
        assertEquals(initialY - ship.speed , ship.getY(), 0.1);
    }

    @Test
    public void testMoveLeft() {
        float initialX = ship.getX();
        ship.moveLeft(1); // Move the ship left for 1 second
        assertEquals(initialX - ship.speed , ship.getX(), 0.1);
    }
    
    @Test
    public void testMoveRight() {
        ship.setPosition(100, 100);
        float initialX = ship.getX();
        ship.moveRight(1); // Move the ship right for 1 second
        assertEquals(initialX + ship.speed, ship.getX(), 0.1);
    } 

}
    
