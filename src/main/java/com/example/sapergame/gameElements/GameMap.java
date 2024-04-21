package com.example.sapergame.gameElements;


import java.util.ArrayList;
import java.util.Random;

public class GameMap {

    private Field[][] mapFields;
    final private int numberOfBombs = 10;
    private Field fieldClickedByUser;

    public Field[][] createNewMap(int xClickedByUser, int yClickedByUser) {
        mapFields = new Field[8][8];
        fieldClickedByUser = new Field(0, false, xClickedByUser, yClickedByUser);
        initializeMap(mapFields);
        return mapFields;
    }

    private void initializeMap(Field[][] mapFields) {

        generateBombsPositions();
        int numberOfBombs = 0;
        for (int i = 0; i < mapFields.length; i++) {
            for (int j = 0; j < mapFields[i].length; j++) {

                boolean isBomb = false;

                for (Bomb bomb : listOfBombs) {
                    if (i == bomb.getX() && j == bomb.getY()) {
                        isBomb = true;
                        numberOfBombs++;
                        break;
                    }
                }

                mapFields[i][j] = new Field(0, isBomb, i, j);
            }
        }
        //System.out.println("bombs: "+ numberOfBombs);
        //displayBombsPositions();
        //displayMap();

        for (int i = 0; i < mapFields.length; i++) {
            for (int j = 0; j < mapFields[i].length; j++) {

                if (mapFields[i][j].isBomb()) {

                    boolean isFieldOnLeft = true;
                    boolean isFieldOnTop = true;
                    boolean isFieldOnRight = true;
                    boolean isFieldBelow = true;


                    if (i == 0) {
                        isFieldOnLeft = false;
                    }
                    if (i == 7) {
                        isFieldOnRight = false;
                        // nie ma pola po prawej, prawym rogu, prawej górze
                    }
                    if (j == 0) {
                        isFieldOnTop = false;
                        // nie ma pola na górze, górnym lewym rogu, górnym prawym rogu
                    }
                    if (j == 7) {
                        isFieldBelow = false;
                        // nie ma pola na dole, dolnym lewym rogu, dolnym prawym rogu
                    }


                    Field fieldLeftSideOfBomb = null;
                    if (isFieldOnLeft) {
                        fieldLeftSideOfBomb = mapFields[i - 1][j];
                    }
                    Field fieldAboveBomb = null;
                    if (isFieldOnTop) {
                        fieldAboveBomb = mapFields[i][j - 1];
                    }
                    Field fieldRightSideOfBomb = null;
                    if (isFieldOnRight) {
                        fieldRightSideOfBomb = mapFields[i + 1][j];
                    }
                    Field fieldBelowBomb = null;
                    if (isFieldBelow) {
                        fieldBelowBomb = mapFields[i][j + 1];
                    }

                    Field fieldTopLeftBomb = null;
                    if (isFieldOnLeft && isFieldOnTop) {
                        fieldTopLeftBomb = mapFields[i - 1][j - 1];
                    }
                    Field fieldTopRightBomb = null;
                    if (isFieldOnRight && isFieldOnTop) {
                        fieldTopRightBomb = mapFields[i + 1][j - 1];
                    }
                    Field fieldDownLeftBomb = null;
                    if (isFieldOnLeft && isFieldBelow) {
                        fieldDownLeftBomb = mapFields[i - 1][j + 1];
                    }
                    Field fieldDownRightBomb = null;
                    if (isFieldOnRight && isFieldBelow) {
                        fieldDownRightBomb = mapFields[i + 1][j + 1];
                    }


                    if (fieldLeftSideOfBomb != null) {
                        fieldLeftSideOfBomb.increaseNumberOfBombsAroundThisField();
                    }
                    if (fieldTopLeftBomb != null) {
                        fieldTopLeftBomb.increaseNumberOfBombsAroundThisField();
                    }
                    if (fieldAboveBomb != null) {
                        fieldAboveBomb.increaseNumberOfBombsAroundThisField();
                    }
                    if (fieldTopRightBomb != null) {
                        fieldTopRightBomb.increaseNumberOfBombsAroundThisField();
                    }
                    if (fieldRightSideOfBomb != null) {
                        fieldRightSideOfBomb.increaseNumberOfBombsAroundThisField();
                    }
                    if (fieldDownRightBomb != null) {
                        fieldDownRightBomb.increaseNumberOfBombsAroundThisField();
                    }
                    if (fieldBelowBomb != null) {
                        fieldBelowBomb.increaseNumberOfBombsAroundThisField();
                    }
                    if (fieldDownLeftBomb != null) {
                        fieldDownLeftBomb.increaseNumberOfBombsAroundThisField();
                    }


                }

            }
        }
        displayMap();


    }

    ArrayList<Bomb> listOfBombs = new ArrayList<>();

    private void generateBombsPositions() {
        Random random = new Random();
        for (int t = 0; t < numberOfBombs; t++) {
            System.out.println(t);
            int x;
            int y;
            while (true) {
                x = random.nextInt(8);
                y = random.nextInt(8);
                boolean isPositionOccupied = false;

                int xCheck = x + 1;
                int yCheck = y + 1;

                boolean isOnField = xCheck == fieldClickedByUser.getX() && yCheck == fieldClickedByUser.getY();
                boolean isOnLeft = xCheck == fieldClickedByUser.getX() - 1 && yCheck == fieldClickedByUser.getY();
                boolean isOnTopLeft = xCheck == (fieldClickedByUser.getX() - 1) && yCheck == (fieldClickedByUser.getY() - 1);
                boolean isOnTop = xCheck == fieldClickedByUser.getX() && yCheck == fieldClickedByUser.getY() - 1;
                boolean isOnTopRight = xCheck == fieldClickedByUser.getX() + 1 && yCheck == fieldClickedByUser.getY() - 1;
                boolean isOnRight = xCheck == fieldClickedByUser.getX() + 1 && yCheck == fieldClickedByUser.getY();
                boolean isOnDownRight = xCheck == fieldClickedByUser.getX() + 1 && yCheck == fieldClickedByUser.getY() + 1;
                boolean isOnDown = xCheck == fieldClickedByUser.getX() && yCheck == fieldClickedByUser.getY() + 1;
                boolean isOnDownLeft = xCheck == fieldClickedByUser.getX() - 1 && yCheck == fieldClickedByUser.getY() + 1;

                if (isOnField || isOnLeft || isOnTopLeft || isOnTop || isOnTopRight
                        || isOnRight || isOnDownRight || isOnDown || isOnDownLeft) {
                }else {
                    if (!listOfBombs.isEmpty()) {
                        for (Bomb bomb : listOfBombs) {
                            if (bomb.getX() == x && bomb.getY() == y) {
                                isPositionOccupied = true;
                                break;
                            }
                        }
                        if (!isPositionOccupied) {
                            break;
                        }
                    } else {
                        break;
                    }
                }

            }
            Bomb bomb = new Bomb(x, y);
            listOfBombs.add(bomb);
        }
        System.out.println("number of bombs " + listOfBombs.size());
    }


    private void displayMap() {

        for (int i = 0; i < mapFields.length; i++) {
            for (int j = 0; j < mapFields[i].length; j++) {

                Field field = mapFields[i][j];
                if (field.isBomb()) {
                    System.out.print("*");
                } else {
                    System.out.print(mapFields[i][j].getBombsAoundThisField());
                }

            }
            System.out.println();
        }

    }

    private void displayBombsPositions() {
        for (Bomb bomb : listOfBombs) {
            System.out.println("position: " + bomb.getX() + " " + bomb.getY());
        }
    }

}
