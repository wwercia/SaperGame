package com.example.sapergame;

import javafx.scene.control.Button;

public class GameButton extends Button {
    private final int x;
    private final int y;
    private final int numberOfBombsAround;
    private final boolean isBomb;

    public GameButton(int x, int y, int numberOfBombsAround, boolean isBomb) {
        this.x = x;
        this.y = y;
        this.numberOfBombsAround = numberOfBombsAround;
        this.isBomb = isBomb;
    }

    public GameButton(String s, int x, int y, int numberOfBombsAround, boolean isBomb) {
        super(s);
        this.x = x;
        this.y = y;
        this.numberOfBombsAround = numberOfBombsAround;
        this.isBomb = isBomb;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumberOfBombsAround() {
        return numberOfBombsAround;
    }

    public boolean isBomb() {
        return isBomb;
    }

}
