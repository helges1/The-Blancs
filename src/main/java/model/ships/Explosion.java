package model.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


/*
    * This class is responsible for creating an explosion animation when an enemy ship is destroyed.

 */
public class Explosion {

    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;

    private Rectangle boundingBox;

    private Texture texture;

    public Explosion(Rectangle boundingBox, float totalAnimationTime) {
        this.texture = new Texture("pictures/explosion.png");
        // Create a bounding box that is twice the size of the original bounding box
        this.boundingBox = boundingBox.setHeight(boundingBox.height * 2f).setWidth(boundingBox.width * 2f);
        
        this.explosionTimer = totalAnimationTime;

        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, 128, 128);
        TextureRegion[] textureRegion1D = new TextureRegion[64];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                textureRegion1D[index++] = textureRegion2D[i][j];
            }
        }
        explosionAnimation = new Animation<TextureRegion>(totalAnimationTime / 64, textureRegion1D);
        explosionTimer = 0;
    }

    public void update (float deltaTime) {
        explosionTimer += deltaTime;
    }

    public void draw (SpriteBatch batch) {
        if (!explosionAnimation.isAnimationFinished(explosionTimer)) {
            batch.draw(explosionAnimation.getKeyFrame(explosionTimer), boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        }
    }

    public boolean isFinished() {
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }

}
