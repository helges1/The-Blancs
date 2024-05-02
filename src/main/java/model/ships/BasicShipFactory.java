package model.ships;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.GameLevel;
import model.GameModel;

public class BasicShipFactory implements ShipFactory {
	
	private final static Random random = new Random();

	private FitViewport viewport;
	private AtlasRegion playerShipTexture;
	private AtlasRegion playerLaserTexture;
	private AtlasRegion enemyShipTexture;
	private AtlasRegion enemyLaserTexture;

	public BasicShipFactory(FitViewport viewport, TextureAtlas atlas) {
		this.viewport = viewport;
		this.playerShipTexture = atlas.findRegion("playerShip");
		this.playerLaserTexture = atlas.findRegion("playerLaser");
		this.enemyShipTexture = atlas.findRegion("basicEnemyShip");
		this.enemyLaserTexture = atlas.findRegion("enemyLaser");
	}

	@Override
	public Ship getPlayerShip() {
		return new PlayerShip(playerShipTexture, playerLaserTexture,
				GameModel.WORLD_WIDTH / 2, GameModel.WORLD_HEIGHT / 2, viewport);
	}

	@Override
	public Ship getEnemyShip(GameLevel gameLevel) {
		// May spawn different types of enemies based on the current gameLevel
		Ship enemyShip = getShipBasedOnLevel(gameLevel);
        int randNum = MathUtils.random(0, 3);

        float posX = 0;
        float posY = 0;

        switch (randNum) {
            case 0:
                posX = 0 - enemyShip.getWidth();
                posY = MathUtils.random(0, GameModel.WORLD_HEIGHT);
                break;
            case 1:
                posX = GameModel.WORLD_WIDTH + enemyShip.getWidth();
                posY = MathUtils.random(0, GameModel.WORLD_HEIGHT);
                break;
            case 2:
                posX = MathUtils.random(0, GameModel.WORLD_WIDTH);
                posY = 0 - enemyShip.getHeight();
                break;
            case 3:
                posX = MathUtils.random(0, GameModel.WORLD_WIDTH);
                posY = GameModel.WORLD_HEIGHT + enemyShip.getHeight();
                break;

            default:
                break;
        }

        enemyShip.setX(posX);
        enemyShip.setY(posY);
		return enemyShip;
	}
	
	private Ship getShipBasedOnLevel(GameLevel gameLevel) {
		float spawnRate = gameLevel.getEnemySpawnRate();
		if (gameLevel.equals(GameLevel.LEVEL_1)) {
			return getBasicShip();
		}
		if (random.nextFloat(spawnRate) >= spawnRate / 2) {
			return getStrongerShip();
		}
		return getBasicShip();
	}

	private Ship getBasicShip() {
		return new BasicEnemyShip(enemyShipTexture, enemyLaserTexture,
				0, 0, viewport);
	}
	
	private Ship getStrongerShip() {
		return new StrongerEnemyShip(enemyShipTexture, enemyLaserTexture,
				0, 0, viewport);
	}

}
