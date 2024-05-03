package model;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents the different levels in the game. Each level has different enemy spawn rates, maximum enemies on screen,
 * asteroid spawn rates, asteroid velocities, and power-up spawn rates.
 */
public enum GameLevel {
    LEVEL_1(3f, 3, 10f, new Vector2(0, -100), 15),
    LEVEL_2(1.5f, 6, 8f, new Vector2(-15, -125), 10),
    LEVEL_3(1f, 9, 6f, new Vector2(-15, -150), 5),
    LEVEL_4(0.5f, 12, 4f, new Vector2(-15, -175), 5);

    private final float enemySpawnRate;
    private final int maxEnemiesOnScreen;
    private final float AsteroidSpawnRate;
    private final Vector2 AsteroidVelocity;
    private final int powerUpSpawnRate;

    /**
     * Creates a new game level with the specified enemy spawn rate, maximum enemies on screen, asteroid spawn rate,
     * asteroid velocity, and power-up spawn rate.
     *
     * @param enemySpawnRate The rate at which enemies spawn.
     * @param maxEnemiesOnScreen The maximum number of enemies that can be on screen at once.
     * @param AsteroidSpawnRate The rate at which asteroids spawn.
     * @param AsteroidVelocity The velocity of the asteroids.
     * @param powerUpSpawnRate The rate at which power-ups spawn.
     */
    private GameLevel(float enemySpawnRate, int maxEnemiesOnScreen, float AsteroidSpawnRate, Vector2 AsteroidVelocity, int powerUpSpawnRate) {
        this.enemySpawnRate = enemySpawnRate;
        this.maxEnemiesOnScreen = maxEnemiesOnScreen;
        this.AsteroidSpawnRate = AsteroidSpawnRate;
        this.AsteroidVelocity = AsteroidVelocity;
        this.powerUpSpawnRate = powerUpSpawnRate;
    }

    /**
     * Gets the rate at which enemies spawn.
     *
     * @return The rate at which enemies spawn.
     */
    public float getEnemySpawnRate() {
        return enemySpawnRate;
    }

    /**
     * Gets the maximum number of enemies that can be on screen at once.
     *
     * @return The maximum number of enemies that can be on screen at once.
     */
    public int getMaxEnemiesOnScreen() {
        return maxEnemiesOnScreen;
    }

    /**
     * Gets the rate at which asteroids spawn.
     *
     * @return The rate at which asteroids spawn.
     */
    public float getAsteroidSpawnRate() {
        return AsteroidSpawnRate;
    }

    /**
     * Gets the velocity of the asteroids.
     *
     * @return The velocity of the asteroids.
     */
    public Vector2 getAsteroidVelocity() {
        return AsteroidVelocity;
    }

    /**
     * Gets the rate at which power-ups spawn.
     *
     * @return The rate at which power-ups spawn.
     */
    public int getPowerUpSpawnRate() {
        return powerUpSpawnRate;
    }

    /**
     * Gets the next level in the sequence.
     *
     * @param level The current level.
     * @return The next level in the sequence.
     */
    public static GameLevel getNextLevel(GameLevel level) {
        switch (level) {
            case LEVEL_1:
                return LEVEL_2;
            case LEVEL_2:
                return LEVEL_3;
            case LEVEL_3:
                return LEVEL_4;
            case LEVEL_4:
                return LEVEL_1;
            default:
                return LEVEL_1;
        }
    }

    /**
     * Gets the level number. used for testing purposes.
     *
     * @return The level number.
     */
    public int getLevelNumber() {
        switch (this) {
            case LEVEL_1:
                return 1;
            case LEVEL_2:
                return 2;
            case LEVEL_3:
                return 3;
            case LEVEL_4:
                return 4;
            default:
                return 1;
        }
    }
}