package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import model.GameModel;
import model.ships.Ship;

/**
 * This class is responsible for controlling the player ship in the game.
 */
public class PlayerShipController implements InputProcessor {
	private GameModel model;
    private Ship ship;
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean spacePressed, mousePressed;
    private boolean spaceJustPressed, mouseJustPressed; // New flags for tracking firing state

    /**
     * Constructor for the PlayerShipController class.
     * @param model The game model object.
     */
    public PlayerShipController(GameModel model) {

    	this.model = model;
        this.ship = model.getPlayerShip();
    }

    // Update the ship's movement and firing based on input
    public void update(float deltaTime) {
        Vector2 movementPosition = new Vector2(0, 0);
        // Movement updates
        if (upPressed) movementPosition.y += 1;
        if (downPressed) movementPosition.y -= 1;
        if (leftPressed) movementPosition.x -= 1;
        if (rightPressed) movementPosition.x += 1;

        // Normalize the movement vector to prevent faster diagonal movement
        if (movementPosition.len() > 0) {
            movementPosition.nor();
            ship.moveShip(movementPosition);
        }
        // Rotate the ship continuously to face the mouse cursor
        Vector2 rotateTowards = model.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        ship.rotateShip(rotateTowards);
        
        // Firing logic updated to only fire once per press
        if (spacePressed && !spaceJustPressed) {
            model.firePlayerLaser();
            spaceJustPressed = true; // Prevents further firing until key is released and pressed again
        }
        if (mousePressed && !mouseJustPressed) {
            model.firePlayerLaser();
            mouseJustPressed = true; // Prevents further firing until button is released and pressed again
        }
    }

    /*
     * InputProcessor methods for controlling key down events. 
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                upPressed = true; break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                downPressed = true; break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                leftPressed = true; break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                rightPressed = true; break;
            case Input.Keys.SPACE:
                if (!spacePressed) {
                    spacePressed = true;
                    spaceJustPressed = false; // Allows firing on initial press
                }
                break;
        }
        return true;
    }

    /*
     * InputProcessor method for handling key up events.
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.W:
                upPressed = false; break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                downPressed = false; break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                leftPressed = false; break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                rightPressed = false; break;
        }
        if (keycode == Input.Keys.SPACE) {
            spacePressed = false;
            spaceJustPressed = false; // Resets firing state, allowing for another shot on next press
        }

        return true;

    }

    /*
     * InputProcessor method for handling mouse movement events.
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector2 rotateTowards = model.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        ship.rotateShip(rotateTowards);
        return true;
    }

    /*
     * InputProcessor methods for handling mouse button events.
     * 
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /*
     * InputProcessor methods for handling mouse button events.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT && !mousePressed) {
            mousePressed = true;
            mouseJustPressed = false; // Allows firing on initial click
        }
        return true;
    }

    /*
     * InputProcessor methods for handling mouse button events.
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            mousePressed = false;
            mouseJustPressed = false; // Resets firing state, allowing for another shot on next click
        }
        return true;
    }

    /*
     * @ return false
     */
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /*
     * @ return false
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /*
     * @ return false
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }





}
