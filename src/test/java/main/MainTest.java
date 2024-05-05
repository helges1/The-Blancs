package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

   @Test
   void testMain() {
       //assertDoesNotThrow(() -> Main.main(new String[]{}));
       //NB:The line above CAN be uncommented and the test will pass locally, and also increase the test coverage of the project
       //However, it will fail on the CI/CD pipeline because the pipeline does not have a display to run the game on

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
