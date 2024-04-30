package view;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Vector2;

import model.GameModel;
import model.TheBlancsGame;

public class HomeScreen implements Screen {
    private static final float WORLD_WIDTH = GameModel.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = GameModel.WORLD_HEIGHT;

    private TheBlancsGame game;
    private Stage stage;
    private TextField userNameField;
    private Music backgroundMusic;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture background;

    public HomeScreen(TheBlancsGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        initTextures();
        initTextField();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ville_seppanen-1_g.mp3"));
        backgroundMusic.setLooping(true);
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

    private void drawButtons() {
        int buttonWidth = (int) (WORLD_WIDTH / 4);
        int buttonHeight = (int) (WORLD_HEIGHT / 10);
        int buttonSpacing = buttonHeight / 3; // Adjust the spacing if needed for better visual layout
        int centerX = (int) (WORLD_WIDTH - buttonWidth) / 2;

        // Center Y position moved down by 20% of the WORLD_HEIGHT
        int baseY = (int) (WORLD_HEIGHT / 2 - buttonHeight / 2 + WORLD_HEIGHT * -0.10);

        int playButtonY = baseY;  // directly use the adjusted base Y
        int exitButtonY = playButtonY - buttonHeight - buttonSpacing;  // position below the play button

        Texture currentPlayButton = isButtonHovered(centerX, playButtonY, buttonWidth, buttonHeight) ? playButtonActive : playButtonInactive;
        Texture currentExitButton = isButtonHovered(centerX, exitButtonY, buttonWidth, buttonHeight) ? exitButtonActive : exitButtonInactive;
        game.batch.draw(currentPlayButton, centerX, playButtonY, buttonWidth, buttonHeight);
        game.batch.draw(currentExitButton, centerX, exitButtonY, buttonWidth, buttonHeight);

        if (isButtonPressed(centerX, playButtonY, buttonWidth, buttonHeight)) {
            playButtonClicked();
        }
        if (isButtonPressed(centerX, exitButtonY, buttonWidth, buttonHeight)) {
            exitButtonClicked();
        }
    }


    private boolean isButtonHovered(int x, int y, int width, int height) {
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.getViewport().unproject(touchPos); // Converts the screen touch to game world coordinates
        return touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
    }

    private boolean isButtonPressed(int x, int y, int width, int height) {
        return Gdx.input.justTouched() && isButtonHovered(x, y, width, height);
    }

    private void initTextField() {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        BitmapFont font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        font.getData().setScale(1.25f);
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
    }

    private void playButtonClicked() {
        Gdx.input.setInputProcessor(game.getPlayerController());
        this.dispose();
        game.setUserName(getUserName());
        game.setScreenType(ScreenType.GAME_SCREEN);
    }
    
     
    private void exitButtonClicked() {
        Gdx.app.exit();
    }

    @Override
    public void show() {
        backgroundMusic.play();

    }
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
    @Override
    public void hide() {
        backgroundMusic.pause();
    }
    @Override
    public void dispose() {
        playButtonActive.dispose();
        playButtonInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        background.dispose();
        backgroundMusic.dispose();
    }

    private String getUserName() {
        return userNameField.getText();
    }
}