package model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Sprite {

    private static TextureFactory textureFactory = new LibGDXTextureFactory();

    public static void setTextureFactory(TextureFactory factory) {
        textureFactory = factory;
    }

    public enum AsteroidType {
        Asteroid1("src/main/resources/pictures/asteroid1.png");

        private final Texture AsteroidTexture;

        AsteroidType(String texturePath) {
            this.AsteroidTexture = textureFactory.create(texturePath);
        }

        public Texture getAsteroidTexture() {
            return AsteroidTexture;
        }
    }

    private Animation<TextureRegion> asteroidAnimation;
    private float animationTimer;
    private final float frameDuration = 0.3f;  // Duration each frame is shown
    private Vector2 velocity;

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

    public void update(float deltaTime) {
        animationTimer += deltaTime;
        TextureRegion currentFrame = asteroidAnimation.getKeyFrame(animationTimer, true); // true for looping

        this.setRegion(currentFrame);
        moveAsteroid(deltaTime);
    }

    private void moveAsteroid(float deltaTime) {
        this.translate(velocity.x * deltaTime, velocity.y * deltaTime);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public boolean isOffScreen(float worldHeight) {
        return this.getY() + this.getHeight() < 0;
    }

    // Texture factory classes and interface
    public interface TextureFactory {
        Texture create(String path);
    }

    public static class LibGDXTextureFactory implements TextureFactory {
        @Override
        public Texture create(String path) {
            return new Texture(path);
        }
    }
}
