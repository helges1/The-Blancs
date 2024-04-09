package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import model.TheBlancsGame;

public class HomeScreen implements Screen {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 100;
    private static final int BUTTON_SPACING = 50; // Space between buttons

    TheBlancsGame game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture background;

    public HomeScreen(TheBlancsGame game) {
        this.game = game;

        // Initialize the textures
        initTextures();
    }

    private void initTextures() {
        playButtonActive = new Texture("pictures/start-1.png");
        playButtonInactive = new Texture("pictures/start-2.png");
        exitButtonActive = new Texture("pictures/exit-1.png");
        exitButtonInactive = new Texture("pictures/exit-2.png");
        background = new Texture("pictures/background.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        // Draw background centered
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw buttons centered
        int playButtonX = (Gdx.graphics.getWidth() - BUTTON_WIDTH) / 2;
        int playButtonY = (Gdx.graphics.getHeight() + BUTTON_SPACING) / 2 - BUTTON_HEIGHT;
        int exitButtonX = playButtonX;
        int exitButtonY = playButtonY - BUTTON_HEIGHT - BUTTON_SPACING;

        // Draw the play button
        Texture currentPlayButton = Gdx.input.getX() < playButtonX + BUTTON_WIDTH && Gdx.input.getX() > playButtonX && Gdx.graphics.getHeight() - Gdx.input.getY() < playButtonY + BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > playButtonY ? playButtonActive : playButtonInactive;
        game.batch.draw(currentPlayButton, playButtonX, playButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Draw the exit button
        Texture currentExitButton = Gdx.input.getX() < exitButtonX + BUTTON_WIDTH && Gdx.input.getX() > exitButtonX && Gdx.graphics.getHeight() - Gdx.input.getY() < exitButtonY + BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > exitButtonY ? exitButtonActive : exitButtonInactive;
        game.batch.draw(currentExitButton, exitButtonX, exitButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Check if play button is pressed
        if (Gdx.input.justTouched() && Gdx.input.getX() < playButtonX + BUTTON_WIDTH && Gdx.input.getX() > playButtonX && Gdx.graphics.getHeight() - Gdx.input.getY() < playButtonY + BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > playButtonY) {
            playButtonClicked();
        }

        // Check if exit button is pressed
        // Here you can add the logic for exiting the game when the exit button is pressed

        game.batch.end();
    }

    private void playButtonClicked() {
        // Dispose resources and switch to the game screen
        this.dispose();
        game.setScreenType(ScreenType.GAME_SCREEN);
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen for the game
    }

    @Override
    public void resize(int width, int height) {
        // Adjust the viewport and UI elements size here if needed
    }

    @Override
    public void pause() {
        // Called when the game is paused
    }

    @Override
    public void resume() {
        // Called when the game is resumed from pause
    }

    @Override
    public void hide() {
        // Called when the current screen changes from this to a different screen
    }

    @Override
    public void dispose() {
        // Dispose of the textures to free up resources
        playButtonActive.dispose();
        playButtonInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        background.dispose();
    }
}
