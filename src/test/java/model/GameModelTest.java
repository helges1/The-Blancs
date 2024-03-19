package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameModelTest {
	
	GameModel model;
	
	@BeforeEach
	public void setUp() {
		Texture playerShipTexture = mock(Texture.class);
		Texture playerLaserTexture = mock(Texture.class);
		Texture enemyShipTexture = mock(Texture.class);
		Texture enemyLaserTexture = mock(Texture.class);
		Sound laserSound = mock(Sound.class);
		OrthographicCamera camera = new OrthographicCamera();
		
		model = new GameModel(playerShipTexture, playerLaserTexture,
				enemyShipTexture, enemyLaserTexture,
				laserSound,
				600, 450, 5, 5, camera);
	}
	
	@Test
	public void firstEnemtSpawnsAfter5Seconds() {
		// No enemyShips when the model is created
		assertEquals(0, model.getEnemyShips().size());
		// One second passes
		model.updateModel(1);
		// Still no enemies
		assertEquals(0, model.getEnemyShips().size());
		// 3 more seconds - 4 total
		model.updateModel(3);
		// Still no enemies
		assertEquals(0, model.getEnemyShips().size());
		// 1 more second - 5 total
		model.updateModel(1);
		// 1 enemy has spawned
		assertEquals(1, model.getEnemyShips().size());
	}
	
	@Test
	public void noMoreThan5EnemiesSpawns() {
		model.updateModel(5);
		assertEquals(1, model.getEnemyShips().size());
		model.updateModel(5);
		assertEquals(2, model.getEnemyShips().size());
		model.updateModel(5);
		assertEquals(3, model.getEnemyShips().size());
		model.updateModel(5);
		assertEquals(4, model.getEnemyShips().size());
		model.updateModel(5);
		assertEquals(5, model.getEnemyShips().size());
		
		model.updateModel(5);
		assertEquals(5, model.getEnemyShips().size());
	}

}
