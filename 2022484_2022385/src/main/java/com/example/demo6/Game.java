package com.example.demo6;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Game extends Application {
    public Stage mystage;
    public GameOver gameover;
    public String s1;
    public String s2;
    private Pane root = new Pane();
    private Image bgImage = new Image(getClass().getResource("gamebge.jpg").toExternalForm(), 1280, 720, false, true);
    private ImageView bgImageView = new ImageView(bgImage);

    private sh stickHero;
    private Image stickHeroImage = new Image(getClass().getResource("hero1.png").toExternalForm());
    private ImageView stickHeroImageView = new ImageView(stickHeroImage);

    private ArrayList<Rectangle> platforms = new ArrayList<>();
    private int currentPlatformIndex = 0;
    private double startPosition = 480;
    private Rectangle StickLandedOn;

    private Stick stick;
    private Rectangle stickRect;
    private Timeline stickExtensionTimeline;

    private ArrayList<Cherry> cherries = new ArrayList<>();
    private boolean isFlipped = false;

    public int cherriescollected;
    public int score;
    private Image scoreImage = new Image(getClass().getResource("score1.png").toExternalForm());
    private ImageView scoreImageView = new ImageView(scoreImage);
    private Image cherryImage = new Image(getClass().getResource("cherry.png").toExternalForm());
    private ImageView cherryImageView = new ImageView(cherryImage);
    private Text scoreText;
    private Text cherryText;
    private boolean gameOver = false;

    public Game(int cherries, int score, String s1, String s2){
        this.cherriescollected = cherries;
        this.score = score;
        this.cherryText = new Text(s1);
        this.scoreText = new Text (s2);
    }

    public void displayBG() {
        System.out.println("BG image displayed for menu");
    }

    // CODE FOR CREATING PLATFORMS START
    Rectangle createPlatformRectangle(Platform platform) {
        Rectangle platformRect = new Rectangle(platform.getPosX(), platform.getPosY(), platform.getWidth(), platform.getHeight());
        //System.out.println("getX: " + platformRect.getX() + " get Y: " + platformRect.getY() + " getWidth: " + platformRect.getWidth() + " getHeight: " + platform.getHeight());
        platformRect.setFill(Color.rgb(31, 31, 31)); // Change color as needed
        return platformRect;
    }

    public void createPlatforms(Pane root){
        Random random = new Random();
        //double platformSpacing = random.nextDouble() * (200 - 100) + 100; // min 100 max 200
        double platformSpacing = random.nextDouble() * (300 - 200) + 200; // min 200 max 300
        for (int i = 0; i < 4; i++) {
            Platform platform = new Platform(200, startPosition, root.getPrefHeight() - 200);
            platforms.add(createPlatformRectangle(platform));
            root.getChildren().add(createPlatformRectangle(platform));
            startPosition += platformSpacing;
            platformSpacing = random.nextDouble() * (300 - 200) + 200;
            }
        }
    // CODE FOR CREATING PLATFORMS OVER

    // CODE FOR SPAWNING STICK HERO INITIALLY ON THE FIRST PLATFORM START
    public void spawnStickHeroOnPlatform(sh Stickhero, Rectangle platform, ImageView stickHeroImageView, Pane root){
        double stickHeroX = platforms.get(0).getX() + platforms.get(0).getWidth() - 25;
        double stickHeroY = root.getPrefHeight() - 235; // root.getprefheight -> yaxis = 0, - 235 sets stick hero y to 235 (height of pillar 200 & 35 height of stickhero jpg)
        stickHero.setPos(stickHeroX, stickHeroY);
        stickHeroImageView.setLayoutX(stickHeroX);
        stickHeroImageView.setLayoutY(stickHeroY);
    }

    public void createCherry() {
        int i = calculateCurrentPlatformIndex();
        while (i+1!=platforms.size()) {
            double platform1x2 = platforms.get(i).getX() + platforms.get(i).getWidth();
            double platform2x1 = platforms.get(i+1).getX();
            double difference = platform2x1 - platform1x2;
            double cherryxpos = platform1x2 + difference / 2;
            if (difference>=150) {
                Cherry cherry = new Cherry();
                cherry.setPos(cherryxpos, 190);
                cherries.add(cherry);
                root.getChildren().add(cherry.getCherryImageView(root, cherryxpos));
            }
            i = i + 1;
        }
    }

    // CODE FOR SPAWNING STICK HERO INITIALLY ON THE FIRST PLATFORM OVER

    // CODE FOR STICK START
    public Rectangle createStickRectangle(Stick stick) {
        double stickX = stickHero.getX() + 20; // +25 because stickhero.jpg is 25 pixels wide
        double stickY = root.getPrefHeight() - 200; // purposely dont use stickHero.getY()
        Rectangle stickRect = new Rectangle(stickX, stickY, 5, 0);
        stickRect.setFill(Color.BLACK); // Change color as needed
        return stickRect;
    }

    public void createStick() {
        stick = new Stick();
        //stick.setHeight(10); // Set the initial height of the stick
        stick.setPosY(root.getPrefHeight() - 200); // purposely dont use stickHero.getY()
        stickRect = createStickRectangle(stick);
        root.getChildren().add(stickRect);
        extendStick();
    }

    public void extendStick() {
        stickExtensionTimeline = new Timeline();
        // Define a KeyValue to change the stick height property
        KeyValue keyValue = new KeyValue(stickRect.heightProperty(), stickRect.getY());
        stickRect.yProperty().bind(stickRect.heightProperty().multiply(-1).add(root.getPrefHeight() - 200));
        // Define a KeyFrame with a duration based on how long the SPACE key is held down
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), keyValue);
        stickExtensionTimeline.getKeyFrames().add(keyFrame);
        // Start the timeline
        stickExtensionTimeline.setCycleCount(Animation.INDEFINITE);
        stickExtensionTimeline.play();
    }


    public void rotateStick() {
        double pivotX = stickRect.getX() + stickRect.getWidth() / 2;
        double pivotY = stickRect.getY() + stickRect.getHeight();

        stickRect.getTransforms().clear();  // Clear any existing transformations

        // Create a Rotate transformation
        Rotate rotate = new Rotate(0, pivotX, pivotY);
        stickRect.getTransforms().add(rotate);

        // Create a Timeline to animate the rotation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(rotate.angleProperty(), 90))
        );

        timeline.play();
        timeline.setOnFinished(e->{
            System.out.println("Stick rotation done!");
        });
    }

    public void deleteStick() {
        if (stickRect != null) {
            root.getChildren().remove(stickRect);
            stickRect = null;
            stick = null; // Optional: If Stick object needs cleanup, set it to null
        }
    }

    //CODE FOR MOVING STICKHERO AND CAMERA
    public void moveStickhero(ImageView stickHeroImageView) {
        // Create a timeline for moving the stickHero
        if (checkStickCollision()) {
            double initialX = stickHero.getX();
            double endX = StickLandedOn.getX() + StickLandedOn.getWidth() - 25;
            stickHero.setPos(endX, stickHero.getY());
            Timeline moveanimation = new Timeline();
            KeyValue kv = new KeyValue(stickHeroImageView.layoutXProperty(), endX);
            KeyFrame kf = new KeyFrame(Duration.seconds(1.5), kv);  // 500 is the duration of the animation in milliseconds
            moveanimation.getKeyFrames().add(kf);
            moveanimation.play();

            Timeline checkCollisionTimeline = new Timeline(
                    new KeyFrame(Duration.millis(100), event -> {
                        if (checkStickheroCollision()) {
                            gameOver = true;
                            moveanimation.stop();
                            Timeline fallanimation = new Timeline();
                            KeyValue kv1 = new KeyValue(stickHeroImageView.layoutYProperty(), 720);
                            KeyFrame kf1 = new KeyFrame(Duration.seconds(1), kv1);
                            fallanimation.getKeyFrames().add(kf1);
                            fallanimation.play();
                        }
                    })
            );

                Timeline checkCherryPickupTimeline = new Timeline(
                        new KeyFrame(Duration.millis(100), event -> {
                            if (checkCherryCollision()) {
                                //System.out.println("cherry removed");
                                cherriescollected = cherriescollected + 1;
                                score = score + 3;
                                updateCounters();
                            }
                        })
                );

                checkCherryPickupTimeline.setCycleCount(Animation.INDEFINITE);
                checkCollisionTimeline.setCycleCount(Animation.INDEFINITE);
                checkCherryPickupTimeline.play();
                checkCollisionTimeline.play();

                moveanimation.setOnFinished(event -> {
                    deleteStick();
                    score = score + 1;
                    updateCounters();
                    if(calculateCurrentPlatformIndex()%3==0){
                        removePlatforms();
                        createPlatforms(root);
                        createCherry();
                    }
                    double offsetX = platforms.get(calculateCurrentPlatformIndex()).getX();
                    double targetX = offsetX - 480;
                    // Smoothly transition the camera to the new position
                    TranslateTransition cameraTransition = new TranslateTransition(Duration.seconds(1), root);
                    cameraTransition.setToX(-targetX);
                    cameraTransition.play();
                    TranslateTransition bgTransition = new TranslateTransition(Duration.seconds(1), bgImageView);
                    bgTransition.setToX(targetX);
                    bgTransition.play();
                    TranslateTransition scoreImageTransition = new TranslateTransition(Duration.seconds(1), scoreImageView);
                    TranslateTransition cherryImageTransition = new TranslateTransition(Duration.seconds(1), cherryImageView);
                    TranslateTransition scoreTextTransition = new TranslateTransition(Duration.seconds(1), scoreText);
                    TranslateTransition cherryTextTransition = new TranslateTransition(Duration.seconds(1), cherryText);
                    scoreImageTransition.setToX(targetX); cherryImageTransition.setToX(targetX);
                    scoreTextTransition.setToX(targetX); cherryTextTransition.setToX(targetX);
                    scoreTextTransition.play(); scoreImageTransition.play(); cherryTextTransition.play(); cherryImageTransition.play();
                });
            }

        else {
            gameOver = true;
            double endX = stickRect.getX() + stickRect.getHeight();
            stickHero.setPos(endX, 720);
            Timeline moveanimation = new Timeline();
            KeyValue kv = new KeyValue(stickHeroImageView.layoutXProperty(), endX);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);  // 500 is the duration of the animation in milliseconds
            moveanimation.getKeyFrames().add(kf);
            moveanimation.play();
            moveanimation.setOnFinished(event -> {
                Timeline fallanimation = new Timeline();
                KeyValue kv1 = new KeyValue(stickHeroImageView.layoutYProperty(), 720);
                KeyFrame kf1 = new KeyFrame(Duration.seconds(1), kv1);
                fallanimation.getKeyFrames().add(kf1);
                fallanimation.play();
            });
        }
    }

    private void flipStickHero(Scene scene) {
        double oldgetScale = stickHeroImageView.getScaleY();
        double oldgetTransalte = stickHeroImageView.getTranslateY();
        // Flip the image both vertically and horizontally when LMB is pressed
        stickHeroImageView.setScaleY(-1); // Flip vertically
        stickHeroImageView.setTranslateY(stickHeroImageView.getFitHeight()); // Shift the image down to simulate walking on the lower side
        scene.setOnMouseReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    isFlipped = false;
                    stickHeroImageView.setScaleY(oldgetScale); // Normal vertically
                    stickHeroImageView.setTranslateY(oldgetTransalte); // Reset the vertical shift
                }
        });
    }

    // CODE FOR MOVING STICKHERO AND CAMERA OVER

    // HELPER FUNTIONS
    public boolean checkStickheroCollision() {
        int index = calculateCurrentPlatformIndex();
        //System.out.println("curr platform index: " + index);
        int ultimate = platforms.indexOf(StickLandedOn);
        for(int i=index; i<=ultimate; i++){
            double diffez = stickHeroImageView.getLayoutX() + 25;
            if(diffez>=0 && diffez >=platforms.get(i).getX() && isFlipped){
                //System.out.println("the difference is: " + diffez);
                //System.out.println("YES COLLISION DETECTED BRO");
                return true;
            }
        }
        return false;
    }

    public boolean checkCherryCollision() {
        //int index = calculateCurrentPlatformIndex();
        //System.out.println("curr platform index: " + index);
        int ultimate = platforms.indexOf(StickLandedOn);
        for(int i=0; i<cherries.size(); i++){
            double diffez = stickHeroImageView.getLayoutX() + 25;
            if(diffez>= cherries.get(i).getPosX() && isFlipped){
                root.getChildren().remove(cherries.get(i).getCherryImageView(root, cherries.get(i).getPosX()));
                cherries.remove(i);
                return true;
            }
        }
        return false;
    }

    private int calculateCurrentPlatformIndex() {
        for (int i = 0; i < platforms.size(); i++) {
            Rectangle platformRect = platforms.get(i);
            double platformX1 = platformRect.getX();
            double platformX2 = platformX1 + platformRect.getWidth();
            double stickX = stickHero.getX();
            if (stickX >= platformX1 && stickX <= platformX2) {
                currentPlatformIndex = i;
                return i;
            }
        }
        return currentPlatformIndex; // stickhero kisi platform par nahi hai
    }

    public void removePlatforms(){
        int index = calculateCurrentPlatformIndex();
        int i = 0;
        while(i<index){
            platforms.remove(0);
            i = i + 1;
        }
    }

    public boolean checkStickCollision() {
        double totaldistance = stickRect.getX() + stickRect.getHeight(); // stick kitna distance cover karegi approx
        for (int i = 0; i < platforms.size(); i++) {
            // left waale platforms skip karo
            if (i <= currentPlatformIndex) {
                i = i + 1;
            }
            Rectangle platformRect = platforms.get(i);
            double platformx1 = platformRect.getX();
            double platformx2 = platformx1 + platformRect.getWidth();
            if(totaldistance>=platformx1 && totaldistance<=platformx2){
                StickLandedOn = platformRect;
                return true;
            }
        }
        return false; // kisi right waale se intersect nahi karti stick
    }


    public void handleKeyPress(KeyEvent event) {
        if(gameOver==true){
            GameOver gameisover = new GameOver(cherriescollected, score);
            gameisover.start(new Stage());
            mystage.close();
        }

            if (event.getCode() == KeyCode.SPACE && stick == null && gameOver==false) {
                createStick();
            }
    }

    public void handleKeyRelease(KeyEvent event) {
        if(gameOver==true){
            GameOver gameisover = new GameOver(cherriescollected, score);
            gameisover.start(new Stage());
            mystage.close();
        }

            if (event.getCode() == KeyCode.SPACE && gameOver == false) {
                // Stop extending the stick when SPACE key is released
                if (stickExtensionTimeline != null) {
                    stickExtensionTimeline.stop();
                }
                stickRect.yProperty().unbind();
                Thread rotateAnimate = new Thread(()->{
                    rotateStick();
                });

                try{
                    rotateAnimate.start();
                    rotateAnimate.join();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                Thread moveAnimate = new Thread(()->{
                    moveStickhero(stickHeroImageView);
                });

                try{
                    moveAnimate.start();
                    moveAnimate.join();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    private void updateCounters() {
        scoreText.setText("" + score);
        cherryText.setText("" + cherriescollected);
    }

    // HELPER FUNCTIONS OVER
    @Override
    public void start(Stage stage) {
        root.setPrefSize(1280, 720);
        displayBG();
        root.getChildren().addAll(bgImageView);
        Scene scene = new Scene(root, 1280, 720);

        // Create initial platforms
        createPlatforms(root);

        // Create StickHero character
        stickHero = new sh();
        stickHeroImageView.setFitWidth(25); // dont change size
        stickHeroImageView.setFitHeight(35); // dont change size
        spawnStickHeroOnPlatform(stickHero, platforms.get(0), stickHeroImageView, root);
        root.getChildren().add(stickHeroImageView);

        root.getChildren().addAll(scoreImageView, scoreText, cherryImageView, cherryText);
        scoreImageView.setFitHeight(200);
        scoreImageView.setFitWidth(200);
        scoreImageView.setX(550);
        cherryImageView.setFitHeight(40);
        cherryImageView.setFitWidth(40);
        cherryImageView.setY(70);
        cherryImageView.setX(20);
        scoreText.setX(693);
        scoreText.setY(109);
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        scoreText.setFill(Color.WHITE);
        cherryText.setX(70);
        cherryText.setY(100);
        cherryText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        cherryText.setFill(Color.WHITE);


        createCherry();


        scene.setOnKeyPressed(this::handleKeyPress);
        scene.setOnKeyReleased(this::handleKeyRelease);

        scene.setOnMousePressed(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    isFlipped = true;
                    flipStickHero(scene); // Start the flip when LMB is pressed
                }
        });

        this.mystage = stage;
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
