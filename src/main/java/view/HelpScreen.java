package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import model.GameModel;
import model.TheBlancsGame;

public class HelpScreen implements Screen {
    private static final float WORLD_WIDTH = GameModel.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = GameModel.WORLD_HEIGHT;
    private TheBlancsGame game;
    private Stage stage;
    private BitmapFont font;
    private Texture background;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;

    public HelpScreen(TheBlancsGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        this.font = new BitmapFont();
        background = new Texture("pictures/background.png");
        exitButtonActive = new Texture("pictures/exit-1.png");
        exitButtonInactive = new Texture("pictures/exit-2.png");
    }
    private void drawExitButton() {
        int buttonWidth = (int) (WORLD_WIDTH / 4);
        int buttonHeight = (int) (WORLD_HEIGHT / 10);
        int centerX = (int) (WORLD_WIDTH - buttonWidth) / 2;
        int baseY = (int) (WORLD_HEIGHT / 2 - buttonHeight / 2 + WORLD_HEIGHT * -0.10);

        int exitButtonY = (int) (baseY * 0.7);  // directly use the adjusted base Y
        Texture currentExitButton = isButtonHovered(centerX, exitButtonY, buttonWidth, buttonHeight) ? exitButtonActive : exitButtonInactive;
        game.batch.draw(currentExitButton, centerX, exitButtonY, buttonWidth, buttonHeight);
        if (isButtonPressed(centerX, exitButtonY, buttonWidth, buttonHeight)) {
            exitButtonClicked();
        }
    }

    private void exitButtonClicked() {
        game.setScreenType(ScreenType.HOME_SCREEN);
    }
    private boolean isButtonHovered(int x, int y, int width, int height) {
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.getViewport().unproject(touchPos); 
        return touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
    }

    private boolean isButtonPressed(int x, int y, int width, int height) {
        return Gdx.input.isTouched() && isButtonHovered(x, y, width, height);
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

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

    private void drawHelpInfo() {
        int textWidth = (int) (WORLD_WIDTH / 4);
        int centerX = (int) ((WORLD_WIDTH - textWidth) / 2);
        int headerY = (int) (WORLD_HEIGHT / 2 + WORLD_HEIGHT * 0.25); // Position for the header
        int helpTextY = (int) (WORLD_HEIGHT / 2 + WORLD_HEIGHT * 0.12); // Position for the help text

        // Draw header
        font.getData().setScale(3); // Larger scale for the header
        font.draw(game.batch, "Game Controls:", centerX - 50, headerY);

        // Draw controls info
        font.getData().setScale(1.5f); // Smaller scale for the control instructions
        font.draw(game.batch, "Arrow Keys: Move Ship", centerX, helpTextY);
        font.draw(game.batch, "Mouse: Aim Ship", centerX, helpTextY - 50); // 50 pixels per line for spacing
        font.draw(game.batch, "Space/Mouse Click: Fire Lasers", centerX, helpTextY - 100); // Adjusted for consistent spacing
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
    public void hide() {}

    @Override
    public void dispose() {
        font.dispose();
    }
}
