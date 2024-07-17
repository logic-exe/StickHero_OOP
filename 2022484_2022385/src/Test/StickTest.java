import com.example.demo6.Stick;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StickTest {
    @Test
    public void testStickProperties() {
        Stick stick = new Stick();

        // Test initial values
        assertEquals(0.0, stick.getPosX());
        assertEquals(0.0, stick.getPosY());
        assertEquals(0.0, stick.getHeight());

        // Test setting and getting X position
        stick.setPosX(10.5);
        assertEquals(10.5, stick.getPosX());

        // Test setting and getting Y position
        stick.setPosY(20.3);
        assertEquals(20.3, stick.getPosY());

        // Test setting and getting height
        stick.setHeight(5.0);
        assertEquals(5.0, stick.getHeight());
    }
}
