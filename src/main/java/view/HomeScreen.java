package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.viewport.Viewport;
import model.GameModel;
import model.TheBlancsGame;

/**
 * Represents the home screen of the game where the player can start a new game or exit.
 * This screen allows the player to enter their username and select the play or exit options.
 */
public class HomeScreen implements Screen {
    private static final float WORLD_WIDTH = GameModel.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = GameModel.WORLD_HEIGHT;
    private Viewport viewport;
    private TheBlancsGame game;
    private Stage stage;
    private TextField userNameField;
    private Music backgroundMusic;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture helpButtonActive;
    Texture helpButtonInactive;
    Texture background;
    
    /**
     * Constructs the HomeScreen with the main game context.
     * 
     * @param game The main game object, used to manage transitions and other screens.
     */
    public HomeScreen(TheBlancsGame game) {
        this.game = game;
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        initTextures();
        initTextField();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ville_seppanen-1_g.mp3"));
        backgroundMusic.setLooping(true);
    }
    
    /**
     * Initializes textures for UI elements such as buttons and background.
     */
    private void initTextures() {
        playButtonActive = new Texture("pictures/start-1.png");
        playButtonInactive = new Texture("pictures/start-2.png");
        exitButtonActive = new Texture("pictures/exit-1.png");
        exitButtonInactive = new Texture("pictures/exit-2.png");
        helpButtonActive = new Texture("pictures/help-1.png");
        helpButtonInactive = new Texture("pictures/help-2.png");
        background = new Texture("pictures/background.png");
    }

    /**
     * Renders the home screen including background, buttons, and text fields.
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

        BitmapFont font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        font.setColor(Color.WHITE);
        font.getData().setScale(3);
        GlyphLayout layout = new GlyphLayout(font, "The Blancs Game");
        float x = (WORLD_WIDTH - layout.width) /2;
        font.draw(game.batch, layout, x, WORLD_HEIGHT - 100);

        drawButtons();
        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    
    /**
     * Draws interactive buttons for starting a new game or exiting.
     */
    private void drawButtons() {
        int buttonWidth = (int) (WORLD_WIDTH / 4);
        int buttonHeight = (int) (WORLD_HEIGHT / 10);
        int buttonSpacing = buttonHeight / 3; // Adjust the spacing if needed for better visual layout
        int centerX = (int) (WORLD_WIDTH - buttonWidth) / 2;

        // Center Y position moved down by 20% of the WORLD_HEIGHT
        int baseY = (int) (WORLD_HEIGHT / 2 - buttonHeight / 2 + WORLD_HEIGHT * -0.10 );

        int playButtonY = baseY;  // directly use the adjusted base Y
        int exitButtonY = playButtonY - buttonHeight - buttonSpacing;  // position below the play button
        int helpButtonY = exitButtonY - buttonHeight - buttonSpacing;  // position below the exit button

        Texture currentPlayButton = isButtonHovered(centerX, playButtonY, buttonWidth, buttonHeight) ? playButtonActive : playButtonInactive;
        Texture currentExitButton = isButtonHovered(centerX, exitButtonY, buttonWidth, buttonHeight) ? exitButtonActive : exitButtonInactive;
        Texture currentHelpButton = isButtonHovered(centerX, helpButtonY, buttonWidth, buttonHeight) ? helpButtonActive : helpButtonInactive;
        game.batch.draw(currentPlayButton, centerX, playButtonY, buttonWidth, buttonHeight);
        game.batch.draw(currentExitButton, centerX, exitButtonY, buttonWidth, buttonHeight);
        game.batch.draw(currentHelpButton, centerX, helpButtonY, buttonWidth, buttonHeight);

        if (isButtonPressed(centerX, playButtonY, buttonWidth, buttonHeight)) {
            playButtonClicked();
        }
        if (isButtonPressed(centerX, exitButtonY, buttonWidth, buttonHeight)) {
            exitButtonClicked();
        }
        if (isButtonPressed(centerX, helpButtonY, buttonWidth, buttonHeight)) {
            helpButtonClicked();
        }
    }


    /**
     * Checks if a screen coordinate is within the bounds of a button.
     * 
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     * @return true if the current touch position is within the button's bounds, false otherwise.
     */
    private boolean isButtonHovered(int x, int y, int width, int height) {
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.getViewport().unproject(touchPos); // Converts the screen touch to game world coordinates
        return touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
    }
    
    /**
     * Checks if a button has been pressed.
     * 
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     * @return true if the button is pressed, false otherwise.
     */
    private boolean isButtonPressed(int x, int y, int width, int height) {
        return Gdx.input.justTouched() && isButtonHovered(x, y, width, height);
    }
    
    /**
     * Initializes the text field for username input.
     */
    private void initTextField() {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        BitmapFont font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        font.getData().setScale(1.25f); // Adjust font scale for better visibility
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        pixmap.dispose();

        textFieldStyle.background = new TextureRegionDrawable(new TextureRegion(pixmapTex));

        userNameField = new TextField("", textFieldStyle);
        userNameField.setMessageText("Enter your username");
        userNameField.setAlignment(1);

        int textFieldWidth = (int) (WORLD_WIDTH / 2.5);
        int textFieldHeight = (int) (WORLD_HEIGHT / 12);
        int textFieldX = (int) (WORLD_WIDTH - textFieldWidth) / 2;
        int textFieldY = (int) (WORLD_HEIGHT + textFieldHeight + 5) / 2;
        userNameField.setPosition(textFieldX, textFieldY);
        userNameField.setSize(textFieldWidth, textFieldHeight);

        stage.addActor(userNameField);

        // Set the input processor to the stage to ensure the TextField can receive input
        Gdx.input.setInputProcessor(stage);

        // Add a listener to manage focus
        userNameField.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // When the text field is clicked, set focus to it
                stage.setKeyboardFocus(userNameField);
            }
        });
    }


    /**
     * Handles the logic when the play button is clicked, transitioning to the main game screen.
     */
    private void playButtonClicked() {
        Gdx.input.setInputProcessor(game.getPlayerController());
        this.dispose();
        game.setUserName(getUserName());
        game.setScreenType(ScreenType.GAME_SCREEN);
    }
    
    /**
     * Handles the logic when the exit button is clicked, exiting the application.
     */
    private void exitButtonClicked() {
        Gdx.app.exit();
    }
    private void helpButtonClicked() {
        game.setScreenType(ScreenType.HELP_SCREEN);
    }

    
    /**
     * Called when this screen becomes the current screen for a Game.
     */
    @Override
    public void show() {
        backgroundMusic.play();


    
    }

    /**
     * Adjusts the screen's viewport when the screen size changes.
     * 
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        Gdx.input.setInputProcessor(stage);
        stage.getCamera().position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        stage.getCamera().update();
    }

    /**
     * Called when the game is paused.
     */
    @Override
    public void pause() {}

    /**
     * Called when the game resumes from a paused state.
     */
    @Override
    public void resume() {}

    /**
     * Called when this screen is no longer the current screen for a Game.
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
        playButtonActive.dispose();
        playButtonInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        background.dispose();
        backgroundMusic.dispose();
    }
    
    /**
     * Retrieves the username entered in the text field.
     * 
     * @return A string containing the username.
     */
    private String getUserName() {
        return userNameField.getText();
    }
}