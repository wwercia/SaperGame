package com.example.sapergame;

import javafx.scene.control.Button;

public class GameButton extends Button {
    private int x;
    private int y;
    private int numberOfBombsAround;
    private boolean isBomb;

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
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setNumberOfBombsAround(int numberOfBombsAround) {
        this.numberOfBombsAround = numberOfBombsAround;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

}
