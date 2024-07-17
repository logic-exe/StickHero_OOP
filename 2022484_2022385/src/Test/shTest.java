import com.example.demo6.sh;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class shTest {
    @Test
    public void testSh() {
        // Create an instance of the sh class
        sh Shero = new sh();

        // Test initial position values
        assertEquals(0.0, Shero.getX());
        assertEquals(0.0, Shero.getY());

        // Set position values
        Shero.setPos(10.0, 20.0);
        assertEquals(10.0, Shero.getX());
        assertEquals(20.0, Shero.getY());
    }
}
