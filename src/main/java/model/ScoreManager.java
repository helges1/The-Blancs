package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.io.*;
import java.net.*;
import java.util.Properties;
import com.google.gson.Gson;

import com.badlogic.gdx.files.FileHandle;
import java.io.InputStream;

/**
 * Manages the high score of the game, saving and loading it from the preferences and online.
 */
public class ScoreManager {

    // Class to store high score and user
    private static class HighScore {
        public HighScore(int highScore, String highScoreUser) {
            this.score = highScore;
            this.user = highScoreUser;
        }
        int score;
        String user;
    }

    private static final Properties config = new Properties();
    private static int highScore = 0;
    private static String highScoreUser = "";
    private static final String PREFS_NAME = "highScoreFile";
    private static final String HIGH_SCORE_KEY = "high_score";
    private static final String HIGH_SCORE_USER_KEY = "high_score_user";
    private static final String PANTRY_BASE_URL = "https://getpantry.cloud/apiv1/pantry/";
    private static String PANTRY_ID;
    private static final String BASKET_NAME = "highScoreBasket";
    
    // Load the pantry ID from the config file
    static {
        try {
            FileHandle fileHandle = Gdx.files.internal("config.properties");
            try (InputStream is = fileHandle.read()) {
                config.load(is);
                PANTRY_ID = config.getProperty("pantry_id");
                if (PANTRY_ID == null || PANTRY_ID.trim().isEmpty()) {
                    throw new IllegalStateException("PANTRY_ID is not set in the config.properties");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage(), e);
        }
    }

    /** Loads the high score from the preferences file.
     *  If the high score is updated online, it will be updated here.
     * */
    public static void loadHighScore() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        highScore = prefs.getInteger(HIGH_SCORE_KEY, 0);
        highScoreUser = prefs.getString(HIGH_SCORE_USER_KEY, "");
        
        // Load online to check for any updates
        loadHighScoreOnline();
    }

    /** 
     * Loads the high score from the online pantry. 
     * */
    public static void setHighScore(int newScore, String userName) {
        if (newScore > highScore) {
            highScore = newScore;
            highScoreUser = userName;
            saveHighScore();
        }
    }

    private static void saveHighScore() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        prefs.putInteger(HIGH_SCORE_KEY, highScore);
        prefs.putString(HIGH_SCORE_USER_KEY, highScoreUser);
        prefs.flush();

        saveHighScoreOnline();
    }

    private static void saveHighScoreOnline() {
        new Thread(() -> {
            try {
                URL url = new URL(PANTRY_BASE_URL + PANTRY_ID + "/basket/" + BASKET_NAME);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                Gson gson = new Gson();
                String jsonInputString = gson.toJson(new HighScore(highScore, highScoreUser));

                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Response from Pantry: ");
                    System.out.println(response.toString());
                }

                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void loadHighScoreOnline() {
        new Thread(() -> {
            try {
                URL url = new URL(PANTRY_BASE_URL + PANTRY_ID + "/basket/" + BASKET_NAME);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                Gson gson = new Gson();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    HighScore fetchedScore = gson.fromJson(response.toString(), HighScore.class);

                    synchronized (con) {
                        // Compare the online fetched score with the local high score
                        if (fetchedScore.score != highScore || !fetchedScore.user.equals(highScoreUser)) {
                            // Update the local high score if different from the online score
                            highScore = fetchedScore.score;
                            highScoreUser = fetchedScore.user;
                            Gdx.app.postRunnable(ScoreManager::saveHighScore);  // Save locally and update online if necessary
                        }
                    }
                }
                con.disconnect();
            } catch (Exception e) {
                System.err.println("Error fetching high score from Pantry: " + e.getMessage());
                // Handle error, potentially notify user or retry
            }
        }).start();
    }

    /*
     * Get the high score
     * 
     * @return the high score
     */
    public static int getHighScore() {
        return highScore;
    }
    /*
     * Get the user who achieved the high score
     * 
     * @return the user who achieved the high score
     */
    public static String getHighScoreUser() {
        return highScoreUser;
    }

    /*
     * Reset the high score to 0
     */
    public static void resetHighScore() {
        // Obtain the Preferences instance for your high score file
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
    
        // Clear all data from the preferences
        prefs.clear();
    
        // Immediately write the changes to storage
        prefs.flush();
    
        // Optionally, reset local static variables to their default states
        highScore = 0;
        highScoreUser = "";
    }
    


}

