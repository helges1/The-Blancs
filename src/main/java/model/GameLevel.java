package model;

import com.badlogic.gdx.math.Vector2;

enum GameLevel {
    LEVEL_1(3f, 3, 10f, new Vector2(0, -100), 15),
    LEVEL_2(1.5f, 6, 8f, new Vector2(-15, -125), 10),
    LEVEL_3(1f, 9, 6f, new Vector2(-15, -150), 5),
    LEVEL_4(0.5f, 12, 4f, new Vector2(-15, -175), 5);

    private final float enemySpawnRate;
    private final int maxEnemiesOnScreen;
    private final float astroidSpawnRate;
    private final Vector2 astroidVelocity;
    private final int powerUpSpawnRate;

    GameLevel(float enemySpawnRate, int maxEnemiesOnScreen, float astroidSpawnRate, Vector2 astroidVelocity, int powerUpSpawnRate) {
        this.enemySpawnRate = enemySpawnRate;
        this.maxEnemiesOnScreen = maxEnemiesOnScreen;
        this.astroidSpawnRate = astroidSpawnRate;
        this.astroidVelocity = astroidVelocity;
        this.powerUpSpawnRate = powerUpSpawnRate;
    }

    public float getEnemySpawnRate() {
        return enemySpawnRate;
    }

    public int getMaxEnemiesOnScreen() {
        return maxEnemiesOnScreen;
    }

    public float getAstroidSpawnRate() {
        return astroidSpawnRate;
    }

    public Vector2 getAstroidVelocity() {
        return astroidVelocity;
    }

    public int getPowerUpSpawnRate() {
        return powerUpSpawnRate;
    }

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
}