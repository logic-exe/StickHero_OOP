package com.example.demo6;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Cherry {
    private double posX;
    private double posY;

    private Image cherryImage = new Image(getClass().getResource("cherry.png").toExternalForm());
    private ImageView cherryImageView = new ImageView(cherryImage);

    public double getPosX(){
        return this.posX;
    }

    public double getPosY(){
        return this.posY;
    }

    public void setPos(double x, double y){
        this.posX = x;
        this.posY = y;
    }

    public ImageView getCherryImageView(Pane root, double cherryxpos) {
        this.cherryImageView.setFitWidth(20);
        this.cherryImageView.setFitHeight(20);
        this.cherryImageView.setLayoutX(cherryxpos);
        this.cherryImageView.setLayoutY(root.getPrefHeight() - 190);
        return cherryImageView;
    }
}
