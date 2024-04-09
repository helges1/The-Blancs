package model;

import com.badlogic.gdx.math.Vector2;

enum GameLevel {
    LEVEL_1(5, 2, 10f, new Vector2(0, -100), 20),
    LEVEL_2(4, 4, 8f, new Vector2(-10, -120), 15),
    LEVEL_3(3, 6, 5f, new Vector2(-10, -140), 10),
    LEVEL_4(2, 8, 3f, new Vector2(-10, -160), 5);

    private final int enemySpawnRate;
    private final int maxEnemiesOnScreen;
    private final float astroidSpawnRate;
    private final Vector2 astroidVelocity;
    private final int powerUpSpawnRate;

    GameLevel(int enemySpawnRate, int maxEnemiesOnScreen, float astroidSpawnRate, Vector2 astroidVelocity, int powerUpSpawnRate) {
        this.enemySpawnRate = enemySpawnRate;
        this.maxEnemiesOnScreen = maxEnemiesOnScreen;
        this.astroidSpawnRate = astroidSpawnRate;
        this.astroidVelocity = astroidVelocity;
        this.powerUpSpawnRate = powerUpSpawnRate;
    }

    public int getEnemySpawnRate() {
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