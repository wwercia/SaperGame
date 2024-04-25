package com.example.sapergame.gameElements;

public class Field {

    private int bombsAroundThisField;
    private final int x;
    private final int y;
    private final boolean isBomb;
    public Field(int bombsAroundThisField, boolean isBomb, int x, int y) {
        if(bombsAroundThisField > 8){
            throw new TooManyBombsException();
        } else if (bombsAroundThisField < 0) {
            throw new NotEnoughBombsException();
        }
        this.bombsAroundThisField = bombsAroundThisField;
        this.x = x;
        this.y = y;
        this.isBomb = isBomb;
    }
    public int getBombsAroundThisField() {
        return bombsAroundThisField;
    }
    public void increaseNumberOfBombsAroundThisField(){
        bombsAroundThisField++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBomb() {
        return isBomb;
    }


}
