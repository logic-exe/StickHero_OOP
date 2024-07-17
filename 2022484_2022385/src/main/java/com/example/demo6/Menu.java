package com.example.demo6;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import java.io.IOException;

public class Menu extends Application implements Opening{
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(createContent(stage)));
        stage.show();
    }

    public void displayBG(){
        System.out.println("BG image displayed for menu");
    }

    private Parent createContent(Stage stage){
        Pane root = new Pane();
        root.setPrefSize(1280,720);
        Image bgImage = new Image(getClass().getResource("menubg.jpg").toExternalForm(), 1280, 720, false, true);
        displayBG();
        VBox box = new VBox(10, new MenuItem("PLAY",()-> {
            try {
                Game game = new Game(0, 0, "0", "0");
                game.start(new Stage());
                stage.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ), new MenuItem("HIGHSCORES",()->{
            Highscores.loadHighscores("highscores.ser");
            Highscores thehighscore = new Highscores();
            thehighscore.start(new Stage());
            stage.close();

        }), new MenuItem("QUIT",()-> Platform.exit()));
        box.setBackground(new Background(new BackgroundFill(Color.web("black",0.6), null, null)));
        box.setTranslateX(100);
        box.setTranslateY(250);
        root.getChildren().addAll(new ImageView(bgImage), box);
        return root;
    }

    private static class MenuItem extends StackPane {
        MenuItem(String name, Runnable action){
            LinearGradient gradient = new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0.1, Color.web("black", 0.75)), new Stop (1.0, Color.web("white", 0.15)));
            Rectangle bg = new Rectangle(250,50, gradient);

            Rectangle line = new Rectangle(5, 50);
            Text text = new Text(name);
            text.setFont(Font.font(22.0));
            text.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.LIGHTGREY));
            line.widthProperty().bind(Bindings.when(hoverProperty()).then(8).otherwise(5));
            line.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.MEDIUMPURPLE).otherwise(Color.LIGHTGREY));

            setOnMouseClicked(e -> action.run());
            setOnMousePressed(e -> bg.setFill(Color.LIGHTGREY));
            setOnMouseReleased(e -> bg.setFill(gradient));
            setAlignment(Pos.CENTER_LEFT);

            HBox box = new HBox(50, line, text);
            box.setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(bg, box);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}