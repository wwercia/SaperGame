package com.example.sapergame.gameElements;

public class Field {

    private int bombs;
    private int x;
    private int y;
    private boolean isMarkedWithBomb;


    public int getBombs() {
        return bombs;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMarkedWithBomb() {
        return isMarkedWithBomb;
    }

    public void setMarkedWithBomb(boolean markedWithBomb) {
        isMarkedWithBomb = markedWithBomb;
    }

}
