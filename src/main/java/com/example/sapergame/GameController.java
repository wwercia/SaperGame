package com.example.sapergame;

import com.example.sapergame.gameElements.Field;
import com.example.sapergame.gameElements.GameMap;


public class GameController {

    public void startGame(){
        GameView gameView = new GameView(new GameController());
        GameMap gameMap = new GameMap();
        int[] position = gameView.displayEmptyMapToGetXAndYClickedByUser();
        System.out.println("positions clicked by user:");
        System.out.println("x " + position[0]);
        System.out.println("y " + position[1]);
        Field[][] mapFields = gameMap.createNewMap(position[0],position[1]);
        Field fieldClickedByUser = new Field(0, false, position[0], position[1]);
        gameView.initGameView(mapFields, fieldClickedByUser);
    }

}
