package view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import model.TheBlancsGame;

/**
 * Represents the base screen for the game. This class provides common functionality
 * for all screens with buttons in the game, such as checking if a button is hovered by the mouse.
 */
public abstract class BaseScreen implements Screen {
    // Variables for the game and the viewport
    protected TheBlancsGame game;
    protected Viewport viewport;
    protected Stage stage;

    public BaseScreen(TheBlancsGame game, Viewport viewport) {
        // Set the game and viewport variables
        this.game = game;
        this.viewport = viewport;
        stage = new Stage(viewport);
        
    }

    /**
     * Checks if the button is hovered by the mouse.
     * 
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     * @return True if the button is hovered, false otherwise.
     */
    protected boolean isButtonHovered(int x, int y, int width, int height) {
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.getViewport().unproject(touchPos); // Converts the screen touch to game world coordinates
        return touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
    }

    /**
     * Checks if the button is pressed.
     * 
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     * @return True if the button is pressed, false otherwise.
     */
    protected boolean isButtonPressed(int x, int y, int width, int height) {
        return Gdx.input.isTouched() && isButtonHovered(x, y, width, height);
    }

    /**
     * Handles the logic when the exit button is clicked, exiting the application.
     */
    protected void exitButtonClicked() {
        Gdx.app.exit();
    }

    /**
     * Handles the logic when the play button is clicked, transitioning to the main game screen.
     */
    protected void playButtonClicked() {
        game.setScreenType(ScreenType.GAME_SCREEN);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Render method is specific to each screen, so it remains abstract
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Dispose resources specific to each screen
    }
}