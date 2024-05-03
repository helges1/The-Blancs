package controller;

import model.GameModel;
import model.ships.Ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlayerShipControllerTest {

    private PlayerShipController shipController;
    private GameModel model;
    private Ship ship;
    private Input mockInput;
    private FitViewport mockViewPort;

    @BeforeEach
    public void setUp() {

        // Mock the Ship class
        ship = mock(Ship.class);
        model = mock(GameModel.class);
        when(model.getPlayerShip()).thenReturn(ship);
        
        // Mock the Input class
        mockInput = mock(Input.class);
        
        // Mock the Viewport class
        mockViewPort = mock(FitViewport.class);
        when(model.getViewport()).thenReturn(mockViewPort);
        
        // Initialize ShipController with the mocked Ship
        shipController = new PlayerShipController(model);

        // Mock the Gdx.input class
        Gdx.input = mockInput;
        
        // Setup common input responses
        when(mockInput.getX()).thenReturn(100);
        when(mockInput.getY()).thenReturn(150);

        // Mock the unproject method to return a Vector2
        Vector2 expectedDirection = new Vector2(100, 150);
        when(mockViewPort.unproject(any(Vector2.class))).thenReturn(expectedDirection);
    }

    @Test
    public void testMoveUpOnKeyUpPressed() {
        shipController.keyDown(Input.Keys.UP);
        shipController.update(1.0f);
        verify(ship).moveShip(new Vector2(0, 1));
    }

    @Test
    public void testMoveDownOnKeyDownPressed() {
        shipController.keyDown(Input.Keys.DOWN);
        shipController.update(1.0f);
        verify(ship).moveShip(new Vector2(0, -1));
    }

    @Test
    public void testMoveLeftOnKeyLeftPressed() {
        shipController.keyDown(Input.Keys.LEFT);
        shipController.update(1.0f);
        verify(ship).moveShip(new Vector2(-1, 0));
    }

    @Test
    public void testMoveRightOnKeyRightPressed() {
        shipController.keyDown(Input.Keys.RIGHT);
        shipController.update(1.0f);
        verify(ship).moveShip(new Vector2(1, 0));
    }

    @Test
    public void testRotateTowardsMousePosition() {
        // Verify that the ship is rotated towards the mouse position
        shipController.update(1.0f);
        verify(ship).rotateShip(new Vector2(100, 150));
    }

    @Test
    public void testFireLaserOnSpacePressed() {
        // Verify that the ship fires a laser when the space key is pressed
        shipController.keyDown(Input.Keys.SPACE);
        shipController.update(1.0f);
        verify(model).firePlayerLaser();
    }

}
