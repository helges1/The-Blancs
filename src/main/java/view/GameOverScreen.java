package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.TheBlancsGame;

public class GameOverScreen implements Screen {

    private TheBlancsGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background;

   

    public GameOverScreen(TheBlancsGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont(); 

        // Load textures
        background = new Texture("pictures/background.png");
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Draw the background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    
        // Use GlyphLayout to center text
        GlyphLayout gameOverLayout = new GlyphLayout(font, "Game Over");
        GlyphLayout restartLayout = new GlyphLayout(font, "Tap to restart");
    
        float gameOverX = (Gdx.graphics.getWidth() - gameOverLayout.width) / 2;
        float gameOverY = (Gdx.graphics.getHeight() + gameOverLayout.height) / 2;
        float restartX = (Gdx.graphics.getWidth() - restartLayout.width) / 2;
        float restartY = gameOverY - 50; 

        font.draw(batch, gameOverLayout, gameOverX, gameOverY);
        font.draw(batch, restartLayout, restartX, restartY);
    
        batch.end();

        // Check for player input to restart the game or return to the main menu
        if (Gdx.input.justTouched()) {
            this.dispose(); 
            game.setScreenType(ScreenType.GAME_SCREEN);
    }
    }
    

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
       
    }

    @Override
    public void resume() {
       
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    
}
