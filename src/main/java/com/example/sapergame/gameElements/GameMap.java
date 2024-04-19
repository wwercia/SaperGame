package com.example.sapergame.gameElements;


public class GameMap {

    private Field[][] mapFields;

    public Field[][] createNewMap(){
        mapFields = new Field[8][8];
        initializeMap(mapFields);
        return mapFields;
    }

    private void initializeMap(Field[][] mapFields){

    }

}
