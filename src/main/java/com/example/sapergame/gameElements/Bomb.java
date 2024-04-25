package com.example.sapergame.gameElements;

public class Bomb {
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Bomb(int x, int y){
        this.x = x;
        this.y = y;
    }

    private final int x;
    private final int y;
}
