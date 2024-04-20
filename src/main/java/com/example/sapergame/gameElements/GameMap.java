package com.example.sapergame.gameElements;


import java.util.ArrayList;
import java.util.Random;

public class GameMap {

    private Field[][] mapFields;
    private int numberOfBombs = 10;
    private int numberOfEmptyFields;

    public Field[][] createNewMap(){
        mapFields = new Field[8][8];
        initializeMap(mapFields);
        return mapFields;
    }

    private void initializeMap(Field[][] mapFields){

        generateBombsPositions();
        int numberOfBombs = 0;
        for(int i = 0; i < mapFields.length; i++){
            for(int j = 0; j < mapFields[i].length; j++){

                boolean isBomb = false;

                for(Bomb bomb : listOfBombs){
                    if(i == bomb.getX() && j == bomb.getY()){
                        isBomb = true;
                        numberOfBombs++;
                        break;
                    }
                }

                mapFields[i][j] = new Field(2, isBomb,  i, j);
            }
        }
        //System.out.println("bombs: "+ numberOfBombs);
        //displayBombsPositions();
        //displayMap();
    }
    ArrayList<Bomb> listOfBombs = new ArrayList<>();
    private void generateBombsPositions(){
        Random random = new Random();
        for(int t = 0; t < numberOfBombs; t++){
            System.out.println(t);
            int x;
            int y;
            while (true){
                x = random.nextInt(8);
                y = random.nextInt(8);
                boolean isPositionOccupied = false;
                if(!listOfBombs.isEmpty()){
                    for (Bomb bomb : listOfBombs){
                        if(bomb.getX() == x && bomb.getY() == y){
                            isPositionOccupied = true;
                            break;
                        }
                    }
                    if(!isPositionOccupied){
                        break;
                    }
                }else {
                    break;
                }
            }
            Bomb bomb = new Bomb(x, y);
            listOfBombs.add(bomb);
        }
        System.out.println("number of bombs " + listOfBombs.size());
    }

    private void displayMap(){

        for(int i = 0; i < mapFields.length; i++) {
            for (int j = 0; j < mapFields[i].length; j++) {

                Field field = mapFields[i][j];
                if(field.isBomb()){
                    System.out.print("0");
                }else {
                    System.out.print("1");
                }

            }
            System.out.println();
        }

    }

    private void displayBombsPositions(){
        for(Bomb bomb : listOfBombs){
            System.out.println("position: " + bomb.getX() + " " + bomb.getY());
        }
    }

}
