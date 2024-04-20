package com.example.sapergame;

import com.example.sapergame.gameElements.Field;
import com.example.sapergame.gameElements.GameMap;


public class GameController {

    private GameModel model = new GameModel();

    public void startGame(){
        GameView gameView = new GameView(new GameController());
        gameView.initGameView();

        GameMap gameMap = new GameMap();
        Field[][] fields = gameMap.createNewMap();
    }

}
