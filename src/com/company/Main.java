package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // TODO: use sys args for gridSize and other settings such as output folder
        // TODO: sliders for brush size, hardness etc

        int gridSize = 28;

        new Main(gridSize);
    }

    public Main(int gridSize) throws InterruptedException{
        Frame frame = new Frame(gridSize);
    }
}
