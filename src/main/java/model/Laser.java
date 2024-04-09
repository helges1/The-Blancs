package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Laser extends Sprite {
    private Vector2 velocity;
    private Vector2 windForce;
    // private static Sound laserSound; // Static to avoid reloading for each laser
    //
    // static {
    // laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser1.mp3"));
    // }

    public Laser(TextureRegion texture, Vector2 position, float speed, float angle) {
        super(texture);
        setPosition(position.x, position.y);
        setRotation(angle);

        // Assuming the angle is correctly adjusted for your game's coordinate system,
        // and 'speed' represents the constant speed you want for the laser.
        float radians = (float) Math.toRadians(angle - 270);
        velocity = new Vector2((float) Math.cos(radians) * speed, (float) Math.sin(radians) * speed);
    }

    // Alternative constructor to allow setting custom width and height
    public Laser(TextureRegion texture, Vector2 position, float speed, float angle, float width, float height) {
        super(texture);
        setPosition(position.x, position.y);
        setRotation(angle);
        setSize(width, height);

        // Assuming the angle is correctly adjusted for your game's coordinate system,
        // and 'speed' represents the constant speed you want for the laser.
        float radians = (float) Math.toRadians(angle - 270);
        velocity = new Vector2((float) Math.cos(radians) * speed, (float) Math.sin(radians) * speed);
    }

    public void setWindForce(Vector2 windForce) {
        this.windForce = windForce;
    }

    public void update(float deltaTime) {
        // Apply wind force to the velocity
        if (windForce != null) velocity.add(windForce.x * deltaTime, windForce.y * deltaTime);

        setPosition(getX() + velocity.x * deltaTime, getY() + velocity.y * deltaTime);
    }

    public boolean isOffScreen(float worldHeight) {
        return getY() > worldHeight;
    }

    public static void disposeSound() {
        // Static method to dispose the sound resource when the game is exiting
        // laserSound.dispose();
    }
}
