Note: Please open module settings and add all the JAVAFX SDK libraries provided in the resources/lib folder if you get the pipeline error.
Please run the Menu class first only, as other classes are dependent on a Menu object to be passed into their constructor to run.
Press SpaceBar once the StickHero falls to bring up the GameOver screen. It costs 3 cherries to revive the player. If the player had any lesser, then the Revive button will not work. Similarly, every cherry collected gives 3 times a normal score. Use SpaceBar to extend the Stick, and LMB to flip the StickHero.

**Menu Options:**
   - The menu offers three options: "PLAY," "HIGHSCORES," and "QUIT."
   - The "PLAY" option starts the game by creating an instance of the `Game` class and closing the menu stage.
   - The "HIGHSCORES" option loads high scores from a file using the `Highscores` class and displays them in a new stage.
   - The "QUIT" option exits the application using `Platform.exit()`.

**BONUS PARTS:**
Multithreading:
The code uses threads (rotateAnimate and moveAnimate) to handle stick rotation and StickHero movement, respectively. Therefore, the code runs on a total of 3 threads, one is the MAIN threadm and the other 2 being the rotateAnimate and moveAnimate threads.

File I/O and Serialization/Deserialization:
The code uses Serialization and Deserialization to save the scores of the game in a file and load them everytime the gun runs.

Multiple platforms coverage:
Unlike the original game in which only 2 platforms are visible at a time, we can directly cover multiple platforms in this implementation of the game. For example, if there are 3 platforms in the screen currently, and the user is on platform1 and the stick can extend and cover platform3, then the player can directly go from platform1 to platform3.

Randomization:
If the player does go for multiple platforms coverage and collects cherries during that, then the player may be randomly rewarded bonus points for this.

**Design Patterns:**
Factory Method Pattern:
The createContent() method in the GameOver and Menu class acts like a factory method. It is responsible for creating and returning the root Pane for the game over scene.

Composite Pattern:
The structure of the MenuItem class, which contains a StackPane with child nodes (Rectangle, HBox, Text), is reminiscent of the Composite pattern where individual objects and compositions of objects are treated uniformly.

Observer Pattern:
The code doesn't explicitly implement the Observer pattern. However, the use of event handlers (setOnMouseClicked, setOnMousePressed, setOnMouseReleased) in the MenuItem class is a form of event-driven programming, which has similarities to the Observer pattern.

**Input Handling:**
The code handles keyboard and mouse input. The SPACE key is used to extend the stick, and the left mouse button is used to flip StickHero vertically.

**JUNIT Tests:**
The Test folder contains JUNIT tests.



**Class Structure:**
The Game class extends Application, indicating that it's a JavaFX application.
The class contains several instance variables to manage the game state, such as the stage (mystage), the game over screen (gameover), and various graphical elements (images, text, rectangles, etc.). These are mostly private, and some public, depending on their usage.

**Initialization:**
The start method is the entry point for the JavaFX application. It sets up the game scene, including the background, platforms, StickHero character, and initial cherry placement. Thse are controlled by various methods, such as createPlatforms(), spawnStickHeroOnPlatform(), etc.

**Platform and StickHero Initialization:**
The code creates platforms and StickHero using the Platform, sh, and Cherry classes. Platforms are represented by rectangles, StickHero by an image (stickHeroImageView), and cherries by images (cherryImageView). Platforms are created randomly, with different widths and at different X coordinate.

**Game Mechanics:**
The game involves StickHero moving on platforms and extending a stick to reach the next platform. The stick can be extended by pressing the SPACE key. Stick extension is animated using JavaFX timelines. The StickHero character can also flip vertically when the left mouse button is pressed. In both cases, the extension of the stick and the time till duration till when StickHero is flipped depends upon how long the player holds down the key.

**Collision Detection:**
The code includes collision detection methods to check if StickHero collides with a platform or collects a cherry. The checkStickCollision method determines if the stick reaches the next platform, and checkCherryCollision checks if StickHero collects a cherry. The moveStickHero() method contains the logic for when the StickHero is flipped and collides with a platform.

**Score and Cherry Count:**
The game keeps track of the score and the number of cherries collected. The counters are updated when StickHero lands on a platform or collects a cherry.

**Animation and Transitions:**
The game involves various animations and transitions to move StickHero, extend the stick, rotate StickHero, and transition the camera when StickHero moves to a new section of the level. These are controlled using various functions such as extendStick(), rotateStick(), flipStickhero(), etc. The code employs heavy use of transitions.




**Classes:**

Menu Class:
The `Menu` class in StickHero is responsible for creating the main menu of the game. It provides options for the player to start the game, view high scores, and exit the application. It implements the Opening interface.

Game class:
This is the main class of the entire game. It imports various classes from the JavaFX library for creating a graphical user interface and handling animations.

Game Over Handling:
If the game is over (e.g., StickHero falls), a game over screen is displayed, showing the number of cherries collected and the final score. The game over screen consists of a background image (bgImage), a large "Game Over" text (gameOver), and three horizontal boxes (box1, box2, and box3). box1 contains a menu item for returning to the home screen. box2 contains a menu item for reviving the player (conditionally based on the number of cherries). box3 displays the high score.
If the number of cherries is greater than or equal to 3, the player can use three cherries to revive and continue playing. The "REVIVE" menu item is updated accordingly.
The class implements Serializable, indicating that instances of this class can be serialized, for saving/loading the player's score.

Highscores:
This class uses its instance variable to record a highscore associated with them. It implements basic setters and getters. It uses deserialization and serialization to write these highscores to a file, so that they can be loaded later.

Stick class:
This is a class representing the Stick that StickHero uses. It implements its coordinates as its instance variables, and some other getters and setters.

StickHero class:
This is a class representing StickHero. It implements its coordinates as its instance variables, as well as their getters and setters. The main implementation of StickHero is handeled in the Game class, using its ImageView object.

Cherry:
This class represents a cherry object in the game. Apart from coordinates, getters and setters, it has a method for obtaining an ImageView object of the cherry.

Platform:
This class represents a platform in the game. It is actually a rectangle, and therefore, apart from its own methods for coordinates it can also provide its coordinates through the methods defined in the Rectangle class. It also uses multiple constructors, and has a method for generating random width.
