package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreManager {
    private static int highScore = 0;
    private static String highScoreUser = "";
    private static final String PREFS_NAME = "highScoreFile";
    private static final String HIGH_SCORE_KEY = "high_score";
    private static final String HIGH_SCORE_USER_KEY = "high_score_user";
    
    // Load high score from preferences file
    // Load high score and username from preferences file
    public static void loadHighScore() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        highScore = prefs.getInteger(HIGH_SCORE_KEY, 0); 
        highScoreUser = prefs.getString(HIGH_SCORE_USER_KEY, ""); // Load the username
    }
    // Save high score and username to preferences file
    public static void saveHighScore() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        prefs.putInteger(HIGH_SCORE_KEY, highScore);
        prefs.putString(HIGH_SCORE_USER_KEY, highScoreUser); // Save the username
        prefs.flush(); 
    }
    
    public static int getHighScore() {
        return highScore;
    }

    public static String getHighScoreUser() {
        return highScoreUser;
    }

    // Update high score if new score is higher and save it
    public static void setHighScore(int newScore, String userName) {
        if (newScore > highScore) {
            highScore = newScore;
            highScoreUser = userName;
            saveHighScore();
        }
    }
}
