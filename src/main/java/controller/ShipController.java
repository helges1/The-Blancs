package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import model.Ship;
import model.Laser;
import java.util.ArrayList;
import java.util.List;

public class ShipController implements InputProcessor {
    private Ship ship;
    private boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
    private List<Laser> lasers;

    public ShipController(Ship ship) {
        this.ship = ship;
        lasers = new ArrayList<>();
    }

    // Update the ship's movement based on the keys pressed
    public void update(float deltaTime) {
        if (upPressed) {
            ship.moveUp(deltaTime);
        }
        if (downPressed) {
            ship.moveDown(deltaTime);
        }
        if (leftPressed) {
            ship.moveLeft(deltaTime);
        }
        if (rightPressed) {
            ship.moveRight(deltaTime);
        }

        // Rotate the ship to face the mouse cursor continuously
        ship.rotateShip();

        // Check if the space key is pressed to shoot a laser
        if (spacePressed) {
            // The shootLaser method should return a Laser object
            Laser newLaser = ship.shootLaser();
            lasers.add(newLaser);
            spacePressed = false; // Prevent continuous shooting if desired, or handle timing elsewhere
        }

        // Update lasers
        for (Laser laser : lasers) {
            laser.update(deltaTime);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                upPressed = true;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                downPressed = true;
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                leftPressed = true;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                rightPressed = true;
                break;
            case Input.Keys.SPACE:
                spacePressed = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                upPressed = false;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                downPressed = false;
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                leftPressed = false;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                rightPressed = false;
                break;
            case Input.Keys.SPACE:
                spacePressed = false; // You might want to adjust this depending on your shooting mechanism
                break;
        }
        return true;
    }

    public List<Laser> getLasers() {
        return lasers;
    }

    // Implement the remaining InputProcessor methods as needed
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
