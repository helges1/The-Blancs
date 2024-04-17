package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.math.Vector2;

public class GameLevelTest {

    @Test
    public void testEnumProperties() {
        assertEquals(3f, GameLevel.LEVEL_1.getEnemySpawnRate());
        assertEquals(3, GameLevel.LEVEL_1.getMaxEnemiesOnScreen());
        assertEquals(10f, GameLevel.LEVEL_1.getAsteroidSpawnRate());
        assertEquals(new Vector2(0, -100), GameLevel.LEVEL_1.getAsteroidVelocity());
        assertEquals(15, GameLevel.LEVEL_1.getPowerUpSpawnRate());

        assertEquals(1.5f, GameLevel.LEVEL_2.getEnemySpawnRate());
        assertEquals(6, GameLevel.LEVEL_2.getMaxEnemiesOnScreen());
        assertEquals(8f, GameLevel.LEVEL_2.getAsteroidSpawnRate());
        assertEquals(new Vector2(-15, -125), GameLevel.LEVEL_2.getAsteroidVelocity());
        assertEquals(10, GameLevel.LEVEL_2.getPowerUpSpawnRate());

        // Can add similar assertions for LEVEL_3 and LEVEL_4
    }

    @Test
    public void testGetNextLevel() {
        assertEquals(GameLevel.LEVEL_2, GameLevel.getNextLevel(GameLevel.LEVEL_1));
        assertEquals(GameLevel.LEVEL_3, GameLevel.getNextLevel(GameLevel.LEVEL_2));
        assertEquals(GameLevel.LEVEL_4, GameLevel.getNextLevel(GameLevel.LEVEL_3));
        assertEquals(GameLevel.LEVEL_1, GameLevel.getNextLevel(GameLevel.LEVEL_4));
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


    @Test
    public void testVectorEqualityInGetAsteroidVelocity() {
        // Test for vector equality since Vector2 does not override equals() method
        assertTrue(equalsVector(GameLevel.LEVEL_1.getAsteroidVelocity(), new Vector2(0, -100)));
        assertTrue(equalsVector(GameLevel.LEVEL_2.getAsteroidVelocity(), new Vector2(-15, -125)));
        // Can add similar checks for LEVEL_3 and LEVEL_4
    }

    // Helper method for comparing Vector2 instances
    private boolean equalsVector(Vector2 v1, Vector2 v2) {
        return v1.x == v2.x && v1.y == v2.y;
    }
}
