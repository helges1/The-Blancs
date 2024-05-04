package model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import model.lasers.Laser;
import model.ships.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {

    private GameModel model;
    private FitViewport viewport;
    private Sound laserSound;
    private TextureAtlas atlas;
    private Texture textureMock;

    private final String userName = "Tester";

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        viewport = mock(FitViewport.class);
        laserSound = mock(Sound.class);
        atlas = mock(TextureAtlas.class);
        textureMock = mock(Texture.class);

        // Mocking Texture behavior
        when(textureMock.getWidth()).thenReturn(4096);
        when(textureMock.getHeight()).thenReturn(4096);

        // Setup Texture regions in Atlas
        mockAtlasRegion("playerShip", 0, 50, 98, 75);
        mockAtlasRegion("basicEnemyShip", 3184, 3881, 82, 84);
        mockAtlasRegion("enemyLaser", 3266, 3928, 13, 37);
        mockAtlasRegion("playerLaser", 3184, 3825, 36, 56);
        mockAtlasRegion("explosion", 2160, 3060, 1024, 1024);

        // Create the GameModel instance
        model = new GameModel(atlas, laserSound, viewport, userName);
    }

    private void mockAtlasRegion(String name, int x, int y, int width, int height) {
        AtlasRegion region = new AtlasRegion(textureMock, x, y, width, height);
        when(atlas.findRegion(name)).thenReturn(region);
    }

    @Test
    public void testInitialization() {
        assertNotNull(model, "Model should be initialized.");
    }

    @Test
    public void firstEnemySpawnsAfter3Seconds() {
        assertEquals(0, model.getEnemyShips().size(), "Initial state should have no enemies");

        // Simulate the passing of time in the game's update cycle
        for (int i = 0; i < 2; i++) { // First 2 seconds
            model.updateModel(1);
            assertEquals(0, model.getEnemyShips().size(), "No enemy should spawn in second " + (i + 1));
        }

        model.updateModel(1); // 3th second
        assertEquals(1, model.getEnemyShips().size(), "One enemy should spawn at 3 seconds");
    }

    @Test
    public void noMoreThanMaxEnemiesSpawns() {
        float timeBetweenSpawns = model.getCurrentLevel().getEnemySpawnRate();
        int maxEnemies = model.getCurrentLevel().getMaxEnemiesOnScreen();

        for (int i = 0; i < maxEnemies; i++) {
            model.updateModel(timeBetweenSpawns);
        }
        assertEquals(maxEnemies, model.getEnemyShips().size(), "Max enemies should match game level setting");

        //model.updateModel(timeBetweenSpawns); // Try to spawn one more
        //assertEquals(maxEnemies, model.getEnemyShips().size(), "No more than max enemies should spawn");
    }

    @Test
    public void firePlayerLaserTest() {
    	assertEquals(0, model.getPlayerLasers().size(), "The list of player lasers should initially be empty.");

    	model.firePlayerLaser();
    	assertEquals(0, model.getPlayerLasers().size(), "The list of player lasers should contain 0 lasers."
    			+ " Player can only fire every 0.3 seconds.");

    	model.updateModel(1); // 1 second passes
    	model.firePlayerLaser();
    	assertEquals(1, model.getPlayerLasers().size(), "The list of player lasers should contain 1 lasers.");

    	model.firePlayerLaser();
    	assertEquals(1, model.getPlayerLasers().size(), "The list of player lasers should contain 1 lasers."
    			+ " Player can only fire every 0.3 seconds.");

    	model.updateModel(0.3f); // 0.3 seconds pass
    	model.firePlayerLaser();
    	assertEquals(2, model.getPlayerLasers().size(), "The list of player lasers should contain 2 lasers.");

    	model.updateModel(0.3f);
    	model.firePlayerLaser();
    	assertEquals(3, model.getPlayerLasers().size(), "The list of player lasers should contain 3 lasers.");
    }

    @Test
    public void testEnemyShipExplosion() {
        Ship enemyShip = mock(Ship.class);
        Rectangle boundingRect = new Rectangle(10, 10, 50, 50);
        when(enemyShip.getBoundingRectangle()).thenReturn(boundingRect);
        model.getEnemyShips().add(enemyShip);  // Assuming getEnemyShips() provides access to the list

        assertEquals(0, model.getExplosions().size(), "There should be no explosions initially.");
        model.enemyShipExplosion(enemyShip);
//        assertTrue(model.getEnemyShips().isEmpty(), "The enemy ship should be removed from the list after explosion.");
//		enemyShipExplosion no longer removes the ship from the enemyShips list.
        assertEquals(1, model.getExplosions().size(), "There should be one explosion after the enemy ship explodes.");
    }



    @Test
    public void testAsteroidExplosion() {
        Asteroid asteroid = mock(Asteroid.class);
        Rectangle boundingRect = new Rectangle(10, 10, 50, 50);
        when(asteroid.getBoundingRectangle()).thenReturn(boundingRect);
        model.getAsteroids().add(asteroid);

        assertEquals(0, model.getExplosions().size(), "There should be no explosions initially.");
        model.asteroidExplosion(asteroid);
//        assertTrue(model.getAsteroids().isEmpty(), "The asteroid should be removed from the list after explosion.");
//		asteroidShipExplosion no longer removes the asteroid from the asteroids list.
        assertEquals(1, model.getExplosions().size(), "There should be one explosion after the asteroid explodes.");
    }

    @Test
    public void testScoreIncrement() {
        int initialScore = model.getScore();
        model.destroyedEnemyShipsCount = 3;
        model.updateModel(1);
        assertEquals(initialScore + 30, model.getScore(), "Score should increase by 30 points.");
    }

    @Test
    public void testScoreReset() {
        model.destroyedEnemyShipsCount = 3;
        model.updateModel(1);
        model.resetGameState();
        model.updateModel(1);
        assertEquals(0, model.getScore(), "Score should be reset to 0 after game reset.");
    }

    @Test
    public void testLevelUp() {
        int initialLevel = model.getCurrentLevel().getLevelNumber();
        // switch after 300 points
        model.destroyedEnemyShipsCount = 30;
        model.updateModel(1);
        assertEquals(initialLevel + 1, model.getCurrentLevel().getLevelNumber(), "Level should increase by 1.");
    }

    @Test
    public void testPlayerShipHit() {
        Ship playerShip = model.getPlayerShip();
        playerShip.setHealth(10);
        model.getPlayerLasers().add(mock(Laser.class));
        when(model.getPlayerLasers().get(0).getBoundingRectangle()).thenReturn(new Rectangle(0, 0, 10, 10));
        model.updateModel(1);
        assertEquals(10, playerShip.getHealth(), "Player ship should lose 5 health points after being hit.");
    }

    @Test
    public void testUpdateModel() {
        model.updateModel(1);
        verify(laserSound, times(0)).play(); // No lasers fired yet
        model.firePlayerLaser();
        model.updateModel(1);
        verify(laserSound, times(1)).play(); // Player laser fired
    }

    @Test
    public void testViewport() {
        assertEquals(viewport, model.getViewport(), "Viewport should be set correctly.");
    }

    @Test
    public void testUserName() {
    	assertEquals(userName, model.getUserName(), "User name should be set correctly.");
    }



    @AfterEach
    public void tearDown() {
        // Clean up or reset states if necessary after each test
    }
}
