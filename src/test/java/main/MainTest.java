package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    // This will fail the pipeline at GitLab since it requires a GUI to run
    // Maybe mock would be a better approach?
   @Test
   void testMain() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
    
    @Test
    void create() {
        Main main = new Main();
        assertDoesNotThrow(main::create);
    }
}
