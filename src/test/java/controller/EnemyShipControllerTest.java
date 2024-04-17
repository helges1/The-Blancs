package controller;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import model.GameModel;
import model.ships.Ship;
import org.junit.jupiter.api.BeforeEach;
import java.util.LinkedList;


public class EnemyShipControllerTest {

    private GameModel gameModel;
    private EnemyShipController controller;
    private Ship mockPlayerShip;
    private LinkedList<Ship> mockEnemyShips;

    @BeforeEach
    public void setUp() {
        gameModel = mock(GameModel.class);
        mockPlayerShip = mock(Ship.class);
        mockEnemyShips = new LinkedList<>();
        Ship enemyShip1 = mock(Ship.class);
        Ship enemyShip2 = mock(Ship.class);
        mockEnemyShips.add(enemyShip1);
        mockEnemyShips.add(enemyShip2);
        
        when(gameModel.getPlayerShip()).thenReturn(mockPlayerShip);
        when(gameModel.getEnemyShips()).thenReturn(mockEnemyShips);
        
        controller = new EnemyShipController(gameModel);
    }

    // Ensure that the enemy ship shoots after a certain interval
    @Test
    public void testEnemyShipShootsAfterInterval() {
        controller.update(1.0f); // simulate passing enough time to trigger shooting
        verify(gameModel, times(1)).fireEnemyLaser(any(Ship.class)); // Check if a shot was fired from any ship
    }

    // Ensure that the enemy ship moves towards the player
    @Test
    public void testShipMovementTowardsPlayer() {
        when(mockPlayerShip.getX()).thenReturn(300f);
        when(mockPlayerShip.getY()).thenReturn(300f);
        when(mockEnemyShips.get(0).getX()).thenReturn(100f);
        when(mockEnemyShips.get(0).getY()).thenReturn(100f);
        
        controller.update(0.1f);
        
        verify(mockEnemyShips.get(0), times(1)).moveRight(anyFloat());
        verify(mockEnemyShips.get(0), times(1)).moveUp(anyFloat());
    }
    // Ensure that the enemy ship rotates to face the player
    @Test
    public void testShipRotationToFacePlayer() {
        when(mockPlayerShip.getX()).thenReturn(300f);
        when(mockPlayerShip.getY()).thenReturn(300f);
        when(mockEnemyShips.get(0).getX()).thenReturn(100f);
        when(mockEnemyShips.get(0).getY()).thenReturn(100f);
        
        controller.update(0.1f);

        verify(mockEnemyShips.get(0), times(1)).rotateShip(any());
    }
}
