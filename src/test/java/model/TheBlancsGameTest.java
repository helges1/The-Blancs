package model;

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
        assertEquals(userName, theBlancsGame.getGameModel().getUserName());
    }

    @Test
    public void testSetScreenType() {
        ScreenType screenType = ScreenType.GAME_SCREEN;
        theBlancsGame.setScreenType(screenType);
        assertEquals(screenType, theBlancsGame.getScreenType());
    }
}
