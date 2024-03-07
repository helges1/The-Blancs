package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import model.Ship;

public class ShipController implements InputProcessor {
    private Ship ship;
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean spacePressed;

    public ShipController(Ship ship) {
        this.ship = ship;
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

        // If space is pressed, we shoot a laser
        if (spacePressed) {
            ship.fireLaser(); // This method needs to be implemented in the Ship class
        }
        // if left mouse button is pressed, we shoot a laser
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            ship.fireLaser(); // This method needs to be implemented in the Ship class
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
                spacePressed = true; // Set spacePressed to true when space is pressed
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
                spacePressed = false; // Set spacePressed to false when space is released
                break;
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        ship.rotateShip();
        return true;
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
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }





}
