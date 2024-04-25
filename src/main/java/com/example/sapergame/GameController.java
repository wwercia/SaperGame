package com.example.sapergame;

import com.example.sapergame.gameElements.Field;
import com.example.sapergame.gameElements.GameMap;


public class GameController {

    GameView gameView;
    GameMap gameMap;
    public void startGame(){
        gameView = new GameView(new GameController());
        gameView.initGameView();

        //int[] position = gameView.displayEmptyMapToGetXAndYClickedByUser();
        //System.out.println("positions clicked by user:");
        //System.out.println("x " + position[0]);
        //System.out.println("y " + position[1]);
        //Field[][] mapFields = gameMap.createNewMap(position[0],position[1]);
        //Field fieldClickedByUser = new Field(0, false, position[0], position[1]);
        //gameView.initGameView(mapFields, fieldClickedByUser);
    }
    public Field[][] initGameMap(Field fieldClickedByPlayer){
        GameMap gameMap = new GameMap();
        System.out.println("positions clicked by user:");
        System.out.println("x " + fieldClickedByPlayer.getX());
        System.out.println("y " + fieldClickedByPlayer.getY());
        Field[][] mapFields = gameMap.createNewMap(fieldClickedByPlayer.getX(), fieldClickedByPlayer.getY());
        //Field fieldClickedByUser = new Field(0, false, position[0], position[1]);
        //gameView.initGameView(mapFields, fieldClickedByUser);
        return mapFields;
    }

}
