import com.example.demo6.Platform;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlatformTest {
    @Test
    public void testDefaultConstructor() {
        Platform platform = new Platform();

        // Test default values
        assertEquals(0.0, platform.getHeight());
        assertEquals(0.0, platform.getWidth());
        assertEquals(0.0, platform.getPosX());
        assertEquals(0.0, platform.getPosY());
    }

    @Test
    public void testParameterizedConstructor() {
        double height = 10.0;
        double posX = 5.0;
        double posY = 2.0;

        Platform platform = new Platform(height, posX, posY);

        // Test parameterized constructor values
        assertEquals(height, platform.getHeight());
        assertTrue(platform.getWidth() >= 45.0 && platform.getWidth() <= 90.0); // Check if width is within the specified range
        assertEquals(posX, platform.getPosX());
        assertEquals(posY, platform.getPosY());
    }
}
