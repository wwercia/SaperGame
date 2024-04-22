package com.example.sapergame;

import com.example.sapergame.gameElements.Field;
import com.example.sapergame.gameElements.GameMap;


public class GameController {

    private GameModel model = new GameModel();

    public void startGame(){
        GameView gameView = new GameView(new GameController());
        GameMap gameMap = new GameMap();
        int[] position = gameView.displayEmptyMapToGetXAndYClickedByUser();
        System.out.println("posiotions clicked by user:");
        System.out.println("x " + position[0]);
        System.out.println("y " + position[1]);
        Field[][] mapFields = gameMap.createNewMap(position[0],position[1]);
        gameView.initGameView(mapFields);
    }

}
