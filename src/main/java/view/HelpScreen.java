package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.GameModel;
import model.TheBlancsGame;

/**
 * Represents the help screen in the game, displaying control instructions and game information.
 * This screen provides the user with details on how to control the game's ship and interact
 * with the game environment. It includes an exit button to return to the home screen.
 */
public class HelpScreen implements Screen {
    private static final float WORLD_WIDTH = GameModel.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = GameModel.WORLD_HEIGHT;
    private TheBlancsGame game;
    private Stage stage;
    private BitmapFont font;
    private Texture background;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Music backgroundMusic;

    /**
     * Constructs a new HelpScreen object.
     * 
     * @param game The game controller which manages transitions between screens.
     */
    public HelpScreen(TheBlancsGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        font = new BitmapFont();
        background = new Texture("pictures/background.png");
        exitButtonActive = new Texture("pictures/exit-1.png");
        exitButtonInactive = new Texture("pictures/exit-2.png");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ville_seppanen-1_g.mp3"));
        backgroundMusic.setLooping(true);
    }

    /**
     * Draws the exit button on the screen.
     * Determines which texture to use based on hover state and handles the button click.
     */
    private void drawExitButton() {
        int buttonWidth = (int) (WORLD_WIDTH / 4);
        int buttonHeight = (int) (WORLD_HEIGHT / 10);
        int centerX = (int) (WORLD_WIDTH - buttonWidth) / 2;
        int baseY = (int) (WORLD_HEIGHT / 2 - buttonHeight / 2 + WORLD_HEIGHT * -0.10);
        int exitButtonY = (int) (baseY * 0.7);

        Texture currentExitButton = isButtonHovered(centerX, exitButtonY, buttonWidth, buttonHeight) ? exitButtonActive : exitButtonInactive;
        game.batch.draw(currentExitButton, centerX, exitButtonY, buttonWidth, buttonHeight);
        if (isButtonPressed(centerX, exitButtonY, buttonWidth, buttonHeight)) {
            exitButtonClicked();
        }
    }

    /**
     * Handles the action to be taken when the exit button is clicked.
     * Transitions the screen to the home screen of the game.
     */
    private void exitButtonClicked() {
        game.setScreenType(ScreenType.HOME_SCREEN);
    }

    /**
     * Checks if a button is hovered over by the mouse.
     * 
     * @param x the x-coordinate of the button.
     * @param y the y-coordinate of the button.
     * @param width the width of the button.
     * @param height the height of the button.
     * @return true if the button is hovered over, false otherwise.
     */
    private boolean isButtonHovered(int x, int y, int width, int height) {
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.getViewport().unproject(touchPos); 
        return touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
    }

    /**
     * Checks if a button is pressed.
     * 
     * @param x the x-coordinate of the button.
     * @param y the y-coordinate of the button.
     * @param width the width of the button.
     * @param height the height of the button.
     * @return true if the button is pressed, false otherwise.
     */
    private boolean isButtonPressed(int x, int y, int width, int height) {
        return Gdx.input.isTouched() && isButtonHovered(x, y, width, height);
    }

    /**
     * Called when this screen becomes the current screen.
     * Sets the input processor and starts playing the background music.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        backgroundMusic.play();
    }

    /**
     * Renders the help screen, displaying background, exit button, and help information.
     * 
     * @param delta Time since the last frame was rendered.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.getCamera().update();
        game.batch.setProjectionMatrix(stage.getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        drawExitButton();
        drawHelpInfo();
        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Draws help information text on the screen.
     */
    private void drawHelpInfo() {
        int textWidth = (int) (WORLD_WIDTH / 4);
        int centerX = (int) ((WORLD_WIDTH - textWidth) / 2);
        int headerY = (int) (WORLD_HEIGHT / 2 + WORLD_HEIGHT * 0.25); // Position for the header
        int helpTextY = (int) (WORLD_HEIGHT / 2 + WORLD_HEIGHT * 0.12); // Position for the help text

        font.getData().setScale(3); // Larger scale for the header
        font.draw(game.batch, "Game Controls:", centerX - 50, headerY);

        font.getData().setScale(1.5f); // Smaller scale for the control instructions
        font.draw(game.batch, "Arrow Keys: Move Ship", centerX, helpTextY);
        font.draw(game.batch, "Mouse: Aim Ship", centerX, helpTextY - 50);
        font.draw(game.batch, "Space/Mouse Click: Fire Lasers", centerX, helpTextY - 100);
    }

    /**
     * Handles screen resizing, adjusting the viewport accordingly.
     * 
     * @param width the new width of the screen.
     * @param height the new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getCamera().position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        stage.getCamera().update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    /**
     * Called when this screen is no longer the current screen.
     * Pauses the background music.
     */
    @Override
    public void hide() {
        backgroundMusic.pause();
    }

    /**
     * Releases all resources of this screen.
     */
    @Override
    public void dispose() {
        font.dispose();
        backgroundMusic.dispose();
    }
}
