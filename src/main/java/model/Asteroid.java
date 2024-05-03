package model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents an asteroid in the game. Asteroids move downwards and can be destroyed by the player's bullets.
 */
public class Asteroid extends Sprite {

    private Animation<TextureRegion> asteroidAnimation;
    private float animationTimer;
    private final float frameDuration = 0.3f;  // Duration each frame is shown
    private Vector2 velocity;

    /**
     * Creates a new asteroid with the specified texture, starting x position, velocity, and size.
     *
     * @param asteroidTexture The texture of the asteroid.
     * @param xStartPos The starting x position of the asteroid.
     * @param velocity The velocity of the asteroid.
     * @param AsteroidSize The size of the asteroid.
     */
    public Asteroid(TextureRegion asteroidTexture, float xStartPos, Vector2 velocity, float AsteroidSize) {
        super(asteroidTexture);
        this.velocity = velocity;
        this.asteroidAnimation = new Animation<>(frameDuration, createTextureSheet(asteroidTexture));
        this.animationTimer = 0;


        TextureRegion initialFrame = asteroidAnimation.getKeyFrame(0);
        this.setRegion(initialFrame);
        this.setSize(AsteroidSize, AsteroidSize);
        this.setPosition(xStartPos, 950);  // yPos is fixed at 950
    }
    
    /**
     * A constructor only for testing!
     */
    Asteroid(TextureRegion asteroidTexture, Animation<TextureRegion> animation, float xStart, float yStart, Vector2 velocity, float asteroidSize) {
    	super(asteroidTexture);
    	this.velocity = velocity;
    	this.asteroidAnimation = animation;
    	this.animationTimer = 0;
    	
    	TextureRegion initialFrame = asteroidAnimation.getKeyFrame(0);
        this.setRegion(initialFrame);
        this.setSize(asteroidSize, asteroidSize);
        this.setPosition(xStart, yStart);
    }

    private TextureRegion[] createTextureSheet(TextureRegion asteroidTexture) {
        TextureRegion AsteroidTexture = asteroidTexture;
        int frameWidth = 90;
        int frameCount = (int) this.getWidth() / frameWidth;
        TextureRegion[] AsteroidFrames = new TextureRegion[frameCount];

        for (int i = 0; i < frameCount; i++) {
            AsteroidFrames[i] = new TextureRegion(AsteroidTexture, i * frameWidth, 0, frameWidth, (int) this.getHeight());
        }

        return AsteroidFrames;
    }

    /**
     * Updates the asteroid's position and animation.
     *
     * @param deltaTime The time since the last frame in seconds.
     */
    public void update(float deltaTime) {
        animationTimer += deltaTime;
        TextureRegion currentFrame = asteroidAnimation.getKeyFrame(animationTimer, true); // true for looping

        this.setRegion(currentFrame);
        moveAsteroid(deltaTime);
    }

    private void moveAsteroid(float deltaTime) {
        this.translate(velocity.x * deltaTime, velocity.y * deltaTime);
    }

    /**
     * Draws the asteroid.
     *
     * @param batch The batch to draw the asteroid on.
     */
    public void draw(Batch batch) {
        super.draw(batch);
    }

    /**
     * Returns whether the asteroid is off the screen.
     *
     * @param worldHeight The height of the world.
     * @return Whether the asteroid is off the screen.
     */
    public boolean isOffScreen(float worldHeight) {
        return this.getY() + this.getHeight() < 0;
    }

}
