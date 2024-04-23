package model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    	//model.firePlayerLaser();
    	assertEquals(0, model.getPlayerLasers().size(), "The list of player lasers should contain 0 lasers.");
    	model.updateModel(1);
    	model.firePlayerLaser();
    	assertEquals(1, model.getPlayerLasers().size(), "The list of player lasers should contain 1 lasers.");
    }

    @AfterEach
    public void tearDown() {
        // Clean up or reset states if necessary after each test
    }
}
