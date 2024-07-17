package com.example.demo6;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

import static com.example.demo6.GameOver.highscores;

public class Highscores extends Application implements Serializable{
    private int score;
    private int cherry;
    private ArrayList<Text> textarray = new ArrayList<>();

    public Highscores(int x, int y){
        this.score = x;
        this.cherry = y;
    }

    public Highscores(){
    }

    public int getScore(){
        return this.score;
    }

    public int getCherry(){
        return this.cherry;
    }

    public static void saveHighscores(ArrayList<Highscores> highscores, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("highscores.ser"))) {
            oos.writeObject(highscores);
            System.out.println("Highscores saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Highscores> loadHighscores(String filename) {
        ArrayList<Highscores> highscores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("highscores.ser"))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                highscores = (ArrayList<Highscores>) obj;
            }
            System.out.println("Highscores loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return highscores;
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
        root.getChildren().addAll(new ImageView(bgImage));

        VBox box = new VBox(10, new MenuItem("HOME SCREEN",()-> {
            Menu menu = new Menu();
            try {
                menu.start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.close();
        }));


        for(Highscores hscore: highscores){
            Text text = new Text("Highscore: " + hscore.getScore() + "; Cherries Remaining: " + hscore.cherry);
            this.textarray.add(text);
        }

        int i = 100;
        for(Text text: this.textarray){
            text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
            text.setFill(Color.BLACK);
            text.setTranslateX(100);
            text.setTranslateY(i);
            i = i + 60;
            root.getChildren().add(text);
        }

        root.getChildren().addAll(box);
        box.setTranslateY(640);
        box.setTranslateX(1000);

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
}
