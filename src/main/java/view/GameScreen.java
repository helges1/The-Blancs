package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import model.Ship;

public class GameScreen implements Screen {
	
	private SpriteBatch batch;
	private Ship ship;
	
	//private Ship shipTexture;
	
	public GameScreen() {
		batch = new SpriteBatch();
		ship = new Ship(new Texture("playerShip3_green.png"));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

		handleInput(delta);
		batch.begin();
		
		ship.draw(batch);
		
		batch.end();
	}
	private void handleInput(float deltaTime) {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ship.moveUp(deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ship.moveDown(deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ship.moveLeft(deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ship.moveRight(deltaTime);
        }
        
        
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
