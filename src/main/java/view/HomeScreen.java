package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import model.TheBlancsGame;

public class HomeScreen implements Screen {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 100;
    private static final int BUTTON_SPACING = 50; // Space between buttons


    private TheBlancsGame game;
    private Stage stage;
    private TextField userNameField;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture background;

    public HomeScreen(TheBlancsGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Initialize the textures
        initTextures();

        // Initialize the text field
        initTextField();

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

        // Draw Title of Game
        BitmapFont font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        font.setColor(Color.WHITE);
        font.getData().setScale(4);

        // Use GlyphLayout to calculate the width of the text
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "The Blancs Game");

        // Calculate the x-coordinate for the text to be centered
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;

        // Draw the text
        font.draw(game.batch, layout, x, Gdx.graphics.getHeight() - 100);


        // Draw buttons centered
        int playButtonX = (Gdx.graphics.getWidth() - BUTTON_WIDTH) / 2;
        int playButtonY = (Gdx.graphics.getHeight() + BUTTON_SPACING) / 2 - BUTTON_HEIGHT;
        int exitButtonX = playButtonX;
        int exitButtonY = playButtonY - BUTTON_HEIGHT - BUTTON_SPACING;

        // Draw the play button
        Texture currentPlayButton = Gdx.input.getX() < playButtonX + BUTTON_WIDTH && Gdx.input.getX() > playButtonX
                && Gdx.graphics.getHeight() - Gdx.input.getY() < playButtonY + BUTTON_HEIGHT
                && Gdx.graphics.getHeight() - Gdx.input.getY() > playButtonY ? playButtonActive : playButtonInactive;
        game.batch.draw(currentPlayButton, playButtonX, playButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Draw the exit button
        Texture currentExitButton = Gdx.input.getX() < exitButtonX + BUTTON_WIDTH && Gdx.input.getX() > exitButtonX
                && Gdx.graphics.getHeight() - Gdx.input.getY() < exitButtonY + BUTTON_HEIGHT
                && Gdx.graphics.getHeight() - Gdx.input.getY() > exitButtonY ? exitButtonActive : exitButtonInactive;
        game.batch.draw(currentExitButton, exitButtonX, exitButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);



        // Check if play button is pressed
        if (Gdx.input.justTouched() && Gdx.input.getX() < playButtonX + BUTTON_WIDTH && Gdx.input.getX() > playButtonX
                && Gdx.graphics.getHeight() - Gdx.input.getY() < playButtonY + BUTTON_HEIGHT
                && Gdx.graphics.getHeight() - Gdx.input.getY() > playButtonY) {
            playButtonClicked();
        }

        // Check if exit button is pressed
        if (Gdx.input.justTouched() && Gdx.input.getX() < exitButtonX + BUTTON_WIDTH && Gdx.input.getX() > exitButtonX
                && Gdx.graphics.getHeight() - Gdx.input.getY() < exitButtonY + BUTTON_HEIGHT
                && Gdx.graphics.getHeight() - Gdx.input.getY() > exitButtonY) {
            exitButtonClicked();
        }
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void playButtonClicked() {

        // Setting PlayerShipController as the input processor
        Gdx.input.setInputProcessor(game.getPlayerController());
    
        // Now switch to the game screen
        this.dispose();
        game.setUserName(getUserName());
        game.setScreenType(ScreenType.GAME_SCREEN);
    }
    

    private void initTextField() {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        BitmapFont font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        font.getData().setScale(1.25f);
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;

        // Create a simple background for the text field
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        pixmap.dispose();
        textFieldStyle.background = new TextureRegionDrawable(new TextureRegion(pixmapTex));
    
        userNameField = new TextField("", textFieldStyle);
        userNameField.setMessageText("Enter your username");
        userNameField.setAlignment(1); // Centered text
    
        // Positioning the text field above the Start button
        int textFieldWidth = 300;
        int textFieldHeight = 66;
        int textFieldX = (Gdx.graphics.getWidth() - textFieldWidth) / 2; 
        int playButtonY = (Gdx.graphics.getHeight() + BUTTON_SPACING) / 2 - BUTTON_HEIGHT; 
        int textFieldY = playButtonY + BUTTON_HEIGHT + 10; 
    
        userNameField.setPosition(textFieldX, textFieldY);
        userNameField.setSize(textFieldWidth, textFieldHeight);
    
        stage.addActor(userNameField);
    }

    private void exitButtonClicked() {
        Gdx.app.exit();
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

    private String getUserName() {
        return userNameField.getText();
    }
}
