package view;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.Viewport;
import model.GameModel;
import model.TheBlancsGame;

/**
 * Represents the game over screen displayed when a game session ends. This screen shows the final score
 * and provides options to restart the game or exit.
 * <p>
 * This class manages the UI elements on the game over screen, such as displaying the player's final score,
 * and rendering buttons for replaying the game or exiting. 
 */
public class GameOverScreen implements Screen {
    private static final float WORLD_WIDTH = GameModel.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = GameModel.WORLD_HEIGHT;

    private Viewport viewport;

    private TheBlancsGame game;
    private Stage stage;
    private String scoreText = "Score: 0";
    private BitmapFont font;
    private BitmapFont buttonFont;
    private boolean resourcesDisposed = false;

    Texture background;
    Texture newGameButtonActive;
    Texture newGameButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture blankButtonActive;
    Texture blankButtonInactive;


    private Music backgroundMusic;

    /**
     * Constructor to initialize the game over screen.
     * @param game The main game object that controls the screens.
     */
    public GameOverScreen(TheBlancsGame game) {
        this.game = game;

        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        // Create a stage for the game over screen
        stage = new Stage(viewport);
        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);
        
        // Load fonts
        this.font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        this.font.getData().setScale(3);
        this.buttonFont = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        this.buttonFont.getData().setScale(1);

        // Load textures
        background = new Texture("pictures/background.png");
        newGameButtonActive = new Texture("pictures/start-1.png");
        newGameButtonInactive = new Texture("pictures/start-2.png");
        exitButtonActive = new Texture("pictures/exit-1.png");
        exitButtonInactive = new Texture("pictures/exit-2.png");
        blankButtonActive = new Texture("pictures/blank-1.png");
        blankButtonInactive = new Texture("pictures/blank-2.png");

        // Load background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/GameOverMusic.ogg"));
        backgroundMusic.setLooping(true);

    }

    /**
     * Called when this screen becomes the current screen for the game.
     */
    @Override
    public void show() {
        backgroundMusic.play();
    }

    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().update();
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        
        // Draw the background
        game.batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    
        // Draw the game over text
        GlyphLayout gameOverLayout = new GlyphLayout(font, "Game Over");
        float x = (WORLD_WIDTH - gameOverLayout.width) / 2;
        float y = (WORLD_HEIGHT + gameOverLayout.height) / 2 + 100;
        font.draw(game.batch, gameOverLayout, x, y);


        // Draw the score text
        GlyphLayout scoreLayout = new GlyphLayout(font, scoreText);
        float scoreX = (WORLD_WIDTH - scoreLayout.width) / 2;
        float scoreY = y - 70;
        font.draw(game.batch, scoreLayout, scoreX, scoreY);

        // Draw the buttons
        drawButtons();

        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Helper method to draw interactive buttons on the screen.
     */
    private void drawButtons() {
        int buttonWidth = (int) (WORLD_WIDTH / 4);
        int buttonHeight = (int) (WORLD_HEIGHT / 10);
        int buttonSpacing = buttonHeight / 3; 
        int centerX = (int) (WORLD_WIDTH - buttonWidth) / 2;

        // Center Y position moved down by 20% of the WORLD_HEIGHT
        int baseY = (int) (WORLD_HEIGHT / 2 - buttonHeight / 2 + WORLD_HEIGHT * -0.10);

        int playButtonY = baseY;  // directly use the adjusted base Y
        int exitButtonY = playButtonY - buttonHeight - buttonSpacing;  // position below the play button

        // Draws the hover effect for the buttons based on the mouse position
        Texture currentPlayButton = isButtonHovered(centerX, playButtonY, buttonWidth, buttonHeight) ? blankButtonActive : blankButtonInactive;
        Texture currentExitButton = isButtonHovered(centerX, exitButtonY, buttonWidth, buttonHeight) ? exitButtonActive : exitButtonInactive;
        game.batch.draw(currentPlayButton, centerX, playButtonY, buttonWidth, buttonHeight);
        game.batch.draw(currentExitButton, centerX, exitButtonY, buttonWidth, buttonHeight);

        // Draws text on the blank button
        GlyphLayout playAgainLayout = new GlyphLayout(buttonFont, "Play Again");
        float textX = centerX + (buttonWidth - playAgainLayout.width) / 2;
        float textY = playButtonY + (buttonHeight + playAgainLayout.height) / 2;
        buttonFont.draw(game.batch, "Play Again", textX, textY);


        if (isButtonPressed(centerX, playButtonY, buttonWidth, buttonHeight)) {
            Gdx.app.log("GameOverScreen", "Play button pressed");
            playButtonClicked();
        }
        if (isButtonPressed(centerX, exitButtonY, buttonWidth, buttonHeight)) {
            Gdx.app.log("GameOverScreen", "Exit button pressed");
            exitButtonClicked();
        }
    }

    /**
     * Checks if a button is currently hovered by the mouse.
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     * @return true if the button is hovered, false otherwise.
     */
    private boolean isButtonHovered(int x, int y, int width, int height) {
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.getViewport().unproject(touchPos); // Converts the screen touch to game world coordinates
        boolean hovered = touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
        return hovered;
    }

    /**
     * Checks if a button is currently being pressed.
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     * @return true if the button is pressed, false otherwise.
     */
    private boolean isButtonPressed(int x, int y, int width, int height) {
        return Gdx.input.isTouched() && isButtonHovered(x, y, width, height);
    }

    /**
     * Logic to execute when the play button is clicked.
     */
    private void playButtonClicked() {
        Gdx.input.setInputProcessor(game.getPlayerController());
        game.getGameModel().resetGameState();
        game.setScreenType(ScreenType.GAME_SCREEN);
    }
    
    /**
     * Logic to execute when the exit button is clicked.
     */
    private void exitButtonClicked() {
        this.disposeResourcesForExit();
        Gdx.app.exit();
    }

    /**
     * Called when the screen is resized.
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getCamera().position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        stage.getCamera().update();
    }

    /**
     * Called when the screen is paused.
     */
    @Override
    public void pause() {
       
    }
    
    /**
     * Called when the screen is resumed from a paused state.
     */
    @Override
    public void resume() {
       
    }

    /**
     * Called when this screen is no longer the current screen for the game.
     */
    @Override
    public void hide() {
        backgroundMusic.pause();
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
    }

    /**
     * Called to dispose of resources when the game is exiting completely.
     */
    public void disposeResourcesForExit() {
        // This method is called only when the game is completely exiting.
        if (!resourcesDisposed) {
            font.dispose();
            background.dispose();
            newGameButtonActive.dispose();
            newGameButtonInactive.dispose();
            exitButtonActive.dispose();
            exitButtonInactive.dispose();
            backgroundMusic.dispose();
            resourcesDisposed = true;
        }
    }

    /**
     * Sets the final score to be displayed on the game over screen.
     * @param score The score achieved in the game.
     */
    public void setFinalScore(int score) {
        this.scoreText = "Score: " + score;
    }



}
