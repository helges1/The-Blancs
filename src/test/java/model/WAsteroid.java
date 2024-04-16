package model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class WAsteroid extends Asteroid {

	WAsteroid(TextureRegion asteroidTexture, Animation<TextureRegion> animation, float xStart, float yStart,
			Vector2 velocity, float asteroidSize) {
		super(asteroidTexture, animation, xStart, yStart, velocity, asteroidSize);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setRegion(TextureRegion region) {
		//pass
	}

}
