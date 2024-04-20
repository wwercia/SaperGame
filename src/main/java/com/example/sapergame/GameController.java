package com.example.sapergame;

import com.example.sapergame.gameElements.GameMap;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameController {

    private GameModel model = new GameModel();

    public void startGame(){
        GameView gameView = new GameView(new GameController());
        gameView.initGameView();

        GameMap gameMap = new GameMap();
        gameMap.createNewMap();
    }

}
