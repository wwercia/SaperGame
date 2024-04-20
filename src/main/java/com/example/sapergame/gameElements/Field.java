package com.example.sapergame.gameElements;

public class Field {

    private int bombsAoundThisField;
    private int x;
    private int y;
    private boolean isBomb;
    private boolean isExposed;
    public Field(int bombsAoundThisField, boolean isBomb, int x, int y) {
        if(bombsAoundThisField > 8){
            throw new TooManyBombsException();
        } else if (bombsAoundThisField < 0) {
            throw new NotEnoughBombsException();
        }
        this.bombsAoundThisField = bombsAoundThisField;
        this.x = x;
        this.y = y;
        this.isBomb = isBomb;
        this.isExposed = false;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }
    public int getBombsAoundThisField() {
        return bombsAoundThisField;
    }
    public void increaseNumberOfBombsAroundThisField(){
        bombsAoundThisField++;
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

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

}
