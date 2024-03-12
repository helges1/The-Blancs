package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Laser extends Sprite {
    private Vector2 velocity;
    private static Sound laserSound; // Static to avoid reloading for each laser

    static {
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3"));
    }

    public Laser(Texture texture, Vector2 position, float speed, float angle) {
        super(texture);
        // setSize(15, 15);
        setPosition(position.x, position.y);
        setRotation(angle);
        float radians = (float)Math.toRadians(angle - 270); // gjerne fiks opp i vinklene. prøvde å få til en løsning som virket bare
        velocity = new Vector2((float)Math.cos(radians) * speed, (float)Math.sin(radians) * speed);

        // Play the laser sound when a laser is instantiated
        laserSound.play();
    }

    public void update(float deltaTime) {
        setPosition(getX() + velocity.x * deltaTime, getY() + velocity.y * deltaTime);
    }

    public boolean isOffScreen(float worldHeight) {
        return getY() > worldHeight;
    }

    public static void disposeSound() {
        // Static method to dispose the sound resource when the game is exiting
        laserSound.dispose();
    }
}
