package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.math.Vector2;

public class GameLevelTest {

    @Test
    public void testEnumProperties() {
        // Level 1
        assertEquals(3f, GameLevel.LEVEL_1.getEnemySpawnRate());
        assertEquals(3, GameLevel.LEVEL_1.getMaxEnemiesOnScreen());
        assertEquals(10f, GameLevel.LEVEL_1.getAsteroidSpawnRate());
        assertEquals(new Vector2(0, -100), GameLevel.LEVEL_1.getAsteroidVelocity());
        assertEquals(15, GameLevel.LEVEL_1.getPowerUpSpawnRate());
        // Level 2
        assertEquals(1.5f, GameLevel.LEVEL_2.getEnemySpawnRate());
        assertEquals(6, GameLevel.LEVEL_2.getMaxEnemiesOnScreen());
        assertEquals(8f, GameLevel.LEVEL_2.getAsteroidSpawnRate());
        assertEquals(new Vector2(-15, -125), GameLevel.LEVEL_2.getAsteroidVelocity());
        assertEquals(10, GameLevel.LEVEL_2.getPowerUpSpawnRate());
        // Level 3
        assertEquals(1f, GameLevel.LEVEL_3.getEnemySpawnRate());
        assertEquals(9, GameLevel.LEVEL_3.getMaxEnemiesOnScreen());
        assertEquals(6f, GameLevel.LEVEL_3.getAsteroidSpawnRate());
        assertEquals(new Vector2(-15, -150), GameLevel.LEVEL_3.getAsteroidVelocity());
        assertEquals(5, GameLevel.LEVEL_3.getPowerUpSpawnRate());
        // Level 4
        assertEquals(0.5f, GameLevel.LEVEL_4.getEnemySpawnRate());
        assertEquals(12, GameLevel.LEVEL_4.getMaxEnemiesOnScreen());
        assertEquals(4f, GameLevel.LEVEL_4.getAsteroidSpawnRate());
        assertEquals(new Vector2(-15, -175), GameLevel.LEVEL_4.getAsteroidVelocity());
        assertEquals(5, GameLevel.LEVEL_4.getPowerUpSpawnRate());
    }

    @Test
    public void testGetNextLevelCycling() {
        // Test cycling through levels
        GameLevel currentLevel = GameLevel.LEVEL_1;
        currentLevel = GameLevel.getNextLevel(currentLevel);
        assertSame(GameLevel.LEVEL_2, currentLevel, "Should move to Level 2");

        currentLevel = GameLevel.getNextLevel(currentLevel);
        assertSame(GameLevel.LEVEL_3, currentLevel, "Should move to Level 3");

        currentLevel = GameLevel.getNextLevel(currentLevel);
        assertSame(GameLevel.LEVEL_4, currentLevel, "Should move to Level 4");

        currentLevel = GameLevel.getNextLevel(currentLevel);
        assertSame(GameLevel.LEVEL_1, currentLevel, "Should cycle back to Level 1");
    }

}