package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

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
