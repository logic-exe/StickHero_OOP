package com.example.demo6;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;


public class GameOver extends Application implements Serializable{
    public int cherries;
    public int score;
    public static ArrayList<Highscores> highscores = Highscores.loadHighscores("highscores.ser");


    public GameOver(int cherries, int score){
        this.cherries = cherries;
        this.score = score;
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(createContent(stage)));
        stage.show();
    }

    public Parent createContent(Stage stage){
        Pane root = new Pane();
        root.setPrefSize(1280,720);
        Image bgImage = new Image(getClass().getResource("gamebge.jpg").toExternalForm(), 1280, 720, false, true);

        HBox box1 = new HBox(new MenuItem("HOME SCREEN",()-> {
            highscores.add(new Highscores(this.score, this.cherries));
            Highscores.saveHighscores(GameOver.highscores, "highscores.ser");
            try {
                Menu menu = new Menu();
                menu.start(new Stage());
                stage.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }));

        HBox box2 = new HBox(new MenuItem("REVIVE",()->{}));

        HBox box3 = new HBox(new MenuItem("HIGHSCORE: " + this.score,()-> {
        }));

        if(this.cherries>=3){
            this.cherries = this.cherries - 3;
            box2 = new HBox(new MenuItem("REVIVE",()->{
                Game game = new Game(this.cherries, this.score, String.valueOf(this.cherries), String.valueOf(this.score));
                game.start(new Stage());
                stage.close();
            }));
        }

        box1.setTranslateX(460);
        box1.setTranslateY(420);
        box2.setTranslateX(730);
        box2.setTranslateY(420);
        box3.setTranslateX(560);
        box3.setTranslateY(100);

        root.getChildren().addAll(new ImageView(bgImage), box1, box2, box3);

        Text gameOver = new Text("Game Over");
        gameOver.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 140));
        gameOver.setFill(Color.BLACK);
        gameOver.setTranslateX(285); // Adjust the X position as needed
        gameOver.setTranslateY(350); // Adjust the Y position as needed

        // Add the game over text after other elements
        root.getChildren().add(gameOver);
        return root;
    }

    private static class MenuItem extends StackPane {
        MenuItem(String name, Runnable action){

            Text text = new Text(name);
            text.setFont(Font.font(22.0));
            text.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK));
            setOnMouseClicked(e -> action.run());
            setAlignment(Pos.CENTER_LEFT);

            HBox box = new HBox(50, text);
            box.setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(box);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
