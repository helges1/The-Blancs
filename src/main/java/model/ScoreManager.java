package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreManager {
    private static int highScore = 0;
    private static final String PREFS_NAME = "highScoreFile";
    private static final String HIGH_SCORE_KEY = "high_score";
    
    // Load high score from preferences file
    public static void loadHighScore() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        highScore = prefs.getInteger(HIGH_SCORE_KEY, 0); 
    }
    // Save high score to preferences file
    public static void saveHighScore() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        prefs.putInteger(HIGH_SCORE_KEY, highScore);
        prefs.flush(); 
    }
    
    public static int getHighScore() {
        return highScore;
    }

    // Update high score if new score is higher and save it
    public static void setHighScore(int newScore) {
        if (newScore > highScore) {
            highScore = newScore;
            saveHighScore();
        }
    }
}
