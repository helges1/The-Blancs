package controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import model.Ship;

public class ShipController implements InputProcessor {
    private Ship ship;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

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
        }
        return true;
    }


    // Sebastian: Endret alle til return false for å unngå å få feilmeldinger.

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        ship.rotateShip();
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        return false;
    }





}
