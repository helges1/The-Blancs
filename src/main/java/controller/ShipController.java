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
                upPressed = true;
                break;
            case Input.Keys.DOWN:
                downPressed = true;
                break;
            case Input.Keys.LEFT:
                leftPressed = true;
                break;
            case Input.Keys.RIGHT:
                rightPressed = true;
                break;
        }
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                upPressed = false;
                break;
            case Input.Keys.DOWN:
                downPressed = false;
                break;
            case Input.Keys.LEFT:
                leftPressed = false;
                break;
            case Input.Keys.RIGHT:
                rightPressed = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchDown'");
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchUp'");
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchCancelled'");
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchDragged'");
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
    }
    
        



}
