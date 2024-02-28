package controller;

import model.Ship;
import com.badlogic.gdx.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class ShipControllerTest {

    private ShipController shipController;
    private Ship ship;

    @BeforeEach
    public void setUp() {
        // Mock the Ship class
        ship = mock(Ship.class);
        // Initialize ShipController with the mocked Ship
        shipController = new ShipController(ship);
    }

    @Test
    public void testMoveUpOnKeyUpPressed() {
        // Simulate pressing the UP key
        shipController.keyDown(Input.Keys.UP);
        // Simulate the update logic
        shipController.update(1.0f);
        // Verify that the moveUp method was called
        verify(ship).moveUp(1.0f);
        

        
    }
    @Test
    public void testMoveDownOnKeyDownPressed() {
        // Simulate pressing the DOWN key
        shipController.keyDown(Input.Keys.DOWN);
        // Simulate the update logic
        shipController.update(1.0f);
        // Verify that the moveDown method was called
        verify(ship).moveDown(1.0f);

    }

    @Test
    public void testMoveLeftOnKeyLeftPressed() {
        // Simulate pressing the LEFT key
        shipController.keyDown(Input.Keys.LEFT);
        // Simulate the update logic
        shipController.update(1.0f);
        // Verify that the moveLeft method was called
        verify(ship).moveLeft(1.0f);

    }

    @Test
    public void testMoveRightOnKeyRightPressed() {
        // Simulate pressing the RIGHT key
        shipController.keyDown(Input.Keys.RIGHT);
        // Simulate the update logic
        shipController.update(1.0f);
        // Verify that the moveRight method was called
        verify(ship).moveRight(1.0f);

        
    }
    
}
