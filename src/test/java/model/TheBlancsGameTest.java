package model;

import controller.PlayerShipController;
import view.ScreenType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




public class TheBlancsGameTest {
    private TheBlancsGame theBlancsGame;

    @BeforeEach
    public void setUp() {
        theBlancsGame = new TheBlancsGame();
    }

    @Test
    public void testSetUserName() {
        String userName = "TestUser";
        theBlancsGame.setUserName(userName);
        assertEquals(userName, theBlancsGame.getUserName());
    }

    @Test
    public void testSetScreenType() {
        ScreenType screenType = ScreenType.GAME_SCREEN;
        theBlancsGame.setScreenType(screenType);
        assertEquals(screenType, theBlancsGame.getScreenType());

        screenType = ScreenType.HOME_SCREEN;
        theBlancsGame.setScreenType(screenType);
        assertEquals(screenType, theBlancsGame.getScreenType());

        screenType = ScreenType.GAME_OVER_SCREEN;
        theBlancsGame.setScreenType(screenType);
        assertEquals(screenType, theBlancsGame.getScreenType());

        screenType = ScreenType.HELP_SCREEN;
        theBlancsGame.setScreenType(screenType);
        assertEquals(screenType, theBlancsGame.getScreenType());
    }


    @Test
    public void testGetUserName() {
        String userName = "TestUser";
        theBlancsGame.setUserName(userName);
        assertEquals(userName, theBlancsGame.getUserName());
    }

    @Test
    public void testGetGameModel() {
        GameModel gameModel = theBlancsGame.getGameModel();
        assertEquals(gameModel, theBlancsGame.getGameModel());
    }

    @Test
    public void testGetPlayerController() {
        PlayerShipController playerShipController = theBlancsGame.getPlayerController();
        assertEquals(playerShipController, theBlancsGame.getPlayerController());
    }
    
}
