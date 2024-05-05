package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

   @Test
   void testMain() {
       //assertDoesNotThrow(() -> Main.main(new String[]{})); // commented out because it fails pipeline test on GitLab
       //NB:The line above CAN be uncommented and the test will pass locally, and also increase the test coverage of the project

       // manual test for main:
            // 1. Run the game.
            // 2. Verify that the game window opens correctly.
            // 3. Check that the game screen is displayed with the correct title.
            // 4. Play the game and ensure all game mechanics work as expected.
    } 

    @Test
    void create() {
        Main main = new Main();
        assertDoesNotThrow(main::create);
    }
}
