package com.example.demo6;

import java.util.Random;

public class Platform {
    private double height;
    private double width;
    private double posX;
    private double posY;
    private Stick stick;

    public Platform() {
        // Default values
        this.height = 0;
        this.width = 0;
        this.posX = 0;
        this.posY = 0;
    }

    private double generateRandomWidth() {
        // Generate a random width between a specified range
        Random random = new Random();
        return random.nextDouble() * (90 - 45) + 45; // Adjust the range as needed
    }

    // Parameterized constructor with random width
    public Platform(double height, double posX, double posY) {
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.width = generateRandomWidth();
    }

    public double getHeight(){
        return this.height;
    }

    public double getWidth(){
        return this.width;
    }


    public double getPosX(){
        return this.posX;
    }

    public double getPosY(){
        return this.posY;
    }

}
