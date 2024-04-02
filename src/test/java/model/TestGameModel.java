package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TestGameModel {
	
	private FitViewport viewport = mock(FitViewport.class);
	
	@Test
	public void firstEnemtSpawnsAfter5Seconds() {
		GameModel model = new GameModel(viewport, 5, 1);
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
		GameModel model = new GameModel(viewport, 5, 5);
		
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
