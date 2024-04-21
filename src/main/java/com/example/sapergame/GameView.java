package com.example.sapergame;

import com.example.sapergame.gameElements.Field;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameView {

    private GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    private HBox mainBox;
    private Field[][] gameMapFields;
    private GameButton[][] gameMapButtons = new GameButton[8][8];

    public void initGameView(Field[][] gameMapFields) {
        this.gameMapFields = gameMapFields;
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Saper");

        mainBox = new HBox();
        mainBox.getStyleClass().add("main-box");
        initGameMap();

        Scene scene = new Scene(mainBox, 400, 200);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void initGameMap() {
        initGameMapButtons();
        displayGameMap();
    }

    private void initGameMapButtons() {
        for (int i = 0; i < gameMapFields.length; i++) {
            for (int j = 0; j < gameMapFields[i].length; j++) {
                Field field = gameMapFields[i][j];
                GameButton button = new GameButton(String.valueOf(field.getBombsAoundThisField()), field.getX(), field.getY(), field.getBombsAoundThisField(), field.isBomb());
                if(button.isBomb()){
                    button.setText(" ");
                }
                button.setPrefSize("");
                gameMapButtons[i][j] = button;
            }
        }
    }

    private void displayGameMap(){
        HBox boxForVBoxes = new HBox(15);
        VBox boxForButtons = new VBox(15);
        boxForVBoxes.setAlignment(Pos.CENTER);
        boxForButtons.setAlignment(Pos.CENTER);

        for (int i = 0; i < gameMapFields.length; i++) {
            for (int j = 0; j < gameMapFields[i].length; j++) {
                GameButton gameButton = gameMapButtons[i][j];
                if(gameButton.isBomb()){
                    gameButton.getStyleClass().add("bomb-button");
                }else {
                    gameButton.getStyleClass().add("game-button");
                }
                boxForButtons.getChildren().add(gameButton);
            }
            boxForVBoxes.getChildren().add(boxForButtons);
            boxForButtons = new VBox();
        }

        mainBox.getChildren().add(boxForVBoxes);
    }
    private void addStyle(GameButton gameButton){

        String styleToAdd = "";

        if(gameButton.isBomb()){

        } else if (gameButton.getNumberOfBombsAround() == 0) {

        } else if (gameButton.getNumberOfBombsAround() == 2) {

        } else if (gameButton.getNumberOfBombsAround() == 3) {

        } else if (gameButton.getNumberOfBombsAround() == 4) {

        } else if (gameButton.getNumberOfBombsAround() == 5) {

        } else if (gameButton.getNumberOfBombsAround() == 6) {

        } else if (gameButton.getNumberOfBombsAround() == 7) {

        } else if (gameButton.getNumberOfBombsAround() == 8) {

        }
        gameButton.getStyleClass().add(styleToAdd);
    }

}
