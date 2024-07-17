import com.example.demo6.Highscores;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HighscoresTest {

    @Test
    public void testHighscores() {
        // Create Highscores instances
        Highscores highscores1 = new Highscores(100, 3);
        Highscores highscores2 = new Highscores(150, 5);

        // Test getters
        assertEquals(100, highscores1.getScore());
        assertEquals(3, highscores1.getCherry());

        assertEquals(150, highscores2.getScore());
        assertEquals(5, highscores2.getCherry());

        // Test save and load methods
        ArrayList<Highscores> highscoresList = new ArrayList<>();
        highscoresList.add(highscores1);
        highscoresList.add(highscores2);

        Highscores.saveHighscores(highscoresList, "test_highscores.ser");

        ArrayList<Highscores> loadedHighscores = Highscores.loadHighscores("test_highscores.ser");

        assertEquals(highscoresList.size(), loadedHighscores.size());

        for (int i = 0; i < highscoresList.size(); i++) {
            assertEquals(highscoresList.get(i).getScore(), loadedHighscores.get(i).getScore());
            assertEquals(highscoresList.get(i).getCherry(), loadedHighscores.get(i).getCherry());
        }
    }
}
