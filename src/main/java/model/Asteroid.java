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
        Asteroid1("pictures/asteroid1.png");

        private final Texture AsteroidTexture;

        AsteroidType(String texturePath) {
            this.AsteroidTexture = textureFactory.create(texturePath);
        }

        public Texture getAsteroidTexture() {
            return AsteroidTexture;
        }
    }

    private Animation<TextureRegion> AsteroidAnimation;
    private float animationTimer;
    private final float frameDuration = 0.3f;  // Duration each frame is shown
    private Vector2 velocity;

    public Asteroid(float xStartPos, Vector2 velocity, AsteroidType AsteroidType, float AsteroidSize) {
        this.velocity = velocity;
        this.AsteroidAnimation = new Animation<>(frameDuration, createTextureSheet(AsteroidType));
        this.animationTimer = 0;


        TextureRegion initialFrame = AsteroidAnimation.getKeyFrame(0);
        this.setRegion(initialFrame);
        this.setSize(AsteroidSize, AsteroidSize);
        this.setPosition(xStartPos, 950);  // yPos is fixed at 950
    }

    private TextureRegion[] createTextureSheet(AsteroidType AsteroidType) {
        Texture AsteroidTexture = AsteroidType.getAsteroidTexture();
        int frameWidth = 90;
        int frameCount = AsteroidTexture.getWidth() / frameWidth;
        TextureRegion[] AsteroidFrames = new TextureRegion[frameCount];

        for (int i = 0; i < frameCount; i++) {
            AsteroidFrames[i] = new TextureRegion(AsteroidTexture, i * frameWidth, 0, frameWidth, AsteroidTexture.getHeight());
        }

        return AsteroidFrames;
    }

    public void update(float deltaTime) {
        animationTimer += deltaTime;
        TextureRegion currentFrame = AsteroidAnimation.getKeyFrame(animationTimer, true); // true for looping

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
