package view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;

import model.TheBlancsGame;




public class Homescreen implements Screen {
    
    // endre på størrelsen til knappene og spillet 
    private static final int PLAY_BUTTON_Y = 300;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_WIDTH = 300;
    private static final int PLAY_HEIGHT = 100;
    private static final int EXIT_WIDTH = 300;
    private static final int EXIT_HEIGHT = 100; 

    // kan noen se over denne
    TheBlancsGame game;
    
    
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture background;

    Music menuMusic;


    public Homescreen() {
        this.game = game;

        Gdx.graphics.setWindowedMode(800, 600);
        Gdx.graphics.setResizable(false);

        //playButtonActive = new Texture("pictures/play_button_active.png");



        // Load the images for the play and exit buttons and the background
     
    }

    @Override
    public void render(float delta) {
       Gdx.gl.glClearColor(0, 0, 0, 1);
         Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

            game.batch.begin();
            
            game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            int x = Gdx.graphics.getWidth() / 2 - PLAY_WIDTH / 2;

            if(Gdx.input.getX() < x + PLAY_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > PLAY_BUTTON_Y) {
                game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_WIDTH, PLAY_HEIGHT);
                if(Gdx.input.isTouched()) {
                    this.dispose();
                    game.setScreen(new GameScreen());
                }
            } else {
                game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_WIDTH, PLAY_HEIGHT);
            }

            game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        menuMusic.dispose();
        
    }
}


