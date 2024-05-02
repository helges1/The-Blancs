package view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import model.TheBlancsGame;

public abstract class BaseScreen implements Screen {
    protected TheBlancsGame game;
    protected Viewport viewport;
    protected Stage stage;

    public BaseScreen(TheBlancsGame game, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;
        stage = new Stage(viewport);
        
    }

    public boolean isButtonHovered(int x, int y, int width, int height) {
        Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.getViewport().unproject(touchPos); // Converts the screen touch to game world coordinates
        return touchPos.x >= x && touchPos.x <= x + width && touchPos.y >= y && touchPos.y <= y + height;
    }
    protected boolean isButtonPressed(int x, int y, int width, int height) {
        return Gdx.input.isTouched() && isButtonHovered(x, y, width, height);
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