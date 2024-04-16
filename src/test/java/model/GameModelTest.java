package model;


import com.badlogic.gdx.audio.Sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameModelTest {
	
	private GameModel model;
	
	private final FitViewport viewport = mock(FitViewport.class);
	
    private final Sound laserSound = mock(Sound.class);
    private final TextureAtlas atlas = mock(TextureAtlas.class);
    private final String userName = "Tester";

    @BeforeEach
    public void setUp() {
//        // Mocking the texture factory for Astroid class
//        Asteroid.setTextureFactory(path -> mock(Texture.class));
//        Texture textureMock = mock(Texture.class);
//
//        // Setup width and height
//        when(textureMock.getWidth()).thenReturn(4096);
//        when(textureMock.getHeight()).thenReturn(4096);
//
//        // Properly setting up texture regions
//        mockAtlasRegion("playerShip", 0, 50, 98, 75, textureMock);
//        mockAtlasRegion("basicEnemyShip", 3184, 3881, 82, 84, textureMock);
//        mockAtlasRegion("enemyLaser", 3266, 3928, 13, 37, textureMock);
//        mockAtlasRegion("playerLaser", 3184, 3825, 36, 56, textureMock);

        model = new GameModel(atlas, laserSound, viewport, userName);
    }


//    private void mockAtlasRegion(String name, int x, int y, int width, int height, Texture texture) {
//        AtlasRegion region = new AtlasRegion(texture, x, y, width, height);
//        when(atlas.findRegion(name)).thenReturn(region);
//    }

    @Test
    public void testInitialization() {
        assertNotNull(model, "Model should be initialized.");
    }

	/**
	 * This test tests that something is tested properly
	 * */
    @Test
    public void firstEnemySpawnsAfter5Seconds() {
        // Simulate the passing of time in the game's update cycle
        for (int i = 0; i < 4; i++) { // First 4 seconds
            model.updateModel(1); 
        }
        assertEquals(0, model.getEnemyShips().size(), "No enemy should spawn before 5 seconds");

        model.updateModel(1); // 5th second
        assertEquals(1, model.getEnemyShips().size(), "One enemy should spawn at 5 seconds");
    }

	
    @Test
    public void noMoreThanMaxEnemiesSpawns() {
        float timeBetweenSpawns = model.getCurrentLevel().getEnemySpawnRate();
        int maxEnemies = model.getCurrentLevel().getMaxEnemiesOnScreen();

        for (int i = 0; i < maxEnemies; i++) {
            model.updateModel(timeBetweenSpawns);
        }
        assertEquals(maxEnemies, model.getEnemyShips().size(), "Max enemies should match game level setting");

        model.updateModel(timeBetweenSpawns); // Try to spawn one more
        assertEquals(maxEnemies, model.getEnemyShips().size(), "No more than max enemies should spawn");
    }
}
