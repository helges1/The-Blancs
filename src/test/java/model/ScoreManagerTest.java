package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Manual tests for ScoreManager in a game project.
 * These tests are intended to be performed manually by a tester to ensure that score management works as expected.
 */
public class ScoreManagerTest {

    @BeforeEach
    void setUp() {
        // Initialize the game to default state and navigate to the main menu.
        // Ensure ScoreManager is initialized and ready for operations.
    }

    @Test
    void testLoadHighScore() {
        // Manually load the high score at the start of the game and verify that it is displayed correctly.
        // Start the game.
        // Verify that the high score and username displayed match the previously saved high score and user name.
    }

    @Test
    void testSetHighScoreUpdatesWhenHigher() {
        // Ensure that setting a new high score updates only if it is higher than the current high score.
        // 1. Achieve a score lower than the current high score.
        // 2. Check that the high score does not change.
        // 3. Achieve a score higher than the current high score.
        // The high score should remain unchanged when the new score is lower.
        // The high score should update when the new score is higher.
    }

    @Test
    void testSaveHighScore() {
        // Verify that the high score is saved both locally and online when updated.
        // 1. Set a new high score.
        // 2. Close the game.
        // 3. Reopen the game.
        // The new high score is retained across game sessions.
        // Confirm that the high score is also updated on the online server if applicable.
    }

    @Test
    void testHighScorePersistenceAfterRestart() {
        // Ensure that the high score persists after the application is restarted.
        // Note the current high score.
        // Restart the game or the device.
        // Navigate back to the high score screen.
        // The high score displayed should match the score noted before the restart.
    }

    @Test
    void testOnlineSync() {
        // Test the functionality of online synchronization of the high score.
        // Change the high score using another device or simulation.
        // Open the game and trigger a sync
        // The high score should update to reflect changes made from another source.
    }

}
