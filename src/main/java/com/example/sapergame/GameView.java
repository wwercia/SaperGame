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

    public void initGameView(Field[][] gameMapFields, Field fieldClickedByUser) {
        this.gameMapFields = gameMapFields;
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Saper");

        mainBox = new HBox();
        mainBox.getStyleClass().add("main-box");
        mainBox.setAlignment(Pos.CENTER);
        initGameMap(fieldClickedByUser);

        Scene scene = new Scene(mainBox, 675, 610);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    private void initGameMap(Field fieldClickedByUser) {
        initGameMapButtons();
        displayGameMap(fieldClickedByUser);
    }

    private void initGameMapButtons() {
        for (int i = 0; i < gameMapFields.length; i++) {
            for (int j = 0; j < gameMapFields[i].length; j++) {
                Field field = gameMapFields[i][j];
                GameButton button = new GameButton(String.valueOf(field.getBombsAoundThisField()), field.getX(), field.getY(), field.getBombsAoundThisField(), field.isBomb());
                if (button.isBomb()) {
                    button.setText("  ");
                }
                gameMapButtons[i][j] = button;
            }
        }
    }

    private void displayGameMap(Field fieldClickedByUser) {
        HBox boxForVBoxes = new HBox(10);
        VBox boxForButtons = new VBox(10);
        boxForVBoxes.setAlignment(Pos.CENTER);
        boxForButtons.setAlignment(Pos.CENTER);

        for (int i = 0; i < gameMapFields.length; i++) {
            for (int j = 0; j < gameMapFields[i].length; j++) {
                GameButton gameButton = gameMapButtons[i][j];
                gameButton.getStyleClass().add("for-test-button");
                //odsłaniamy na lewo od kliknietego przez uzytkownika pola
                //exposeFieldsOnTheLeftOfThisField(fieldClickedByUser);


                //addStyle(gameButton, fieldClickedByUser);
                //fillEmptyStyles();
                //if (gameButton.isBomb()) {
                //    gameButton.getStyleClass().add("bomb-button");
                //} else {
                //    gameButton.getStyleClass().add("game-button");
                //}
                boxForButtons.getChildren().add(gameButton);
            }

            //----------------------------------------------------
            //odsłaniamy na lewo od kliknietego przez uzytkownika pola
            exposeFieldsOnTheLeftOfThisField(fieldClickedByUser);
            exposeFieldsOnTheRightOfThisField(fieldClickedByUser);
            exposeFieldsOnTheTopOfThisField(fieldClickedByUser);
            exposeFieldsOnTheDownOfThisField(fieldClickedByUser);
            exposeFieldsOnTheTopLeftOfThisField(fieldClickedByUser);
            exposeFieldsOnTheTopRightOfThisField(fieldClickedByUser);
            exposeFieldsOnTheDownRightOfThisField(fieldClickedByUser);
            exposeFieldsOnTheDownLeftOfThisField(fieldClickedByUser);

            boxForVBoxes.getChildren().add(boxForButtons);
            boxForButtons = new VBox(10);
            boxForButtons.setAlignment(Pos.CENTER);
        }

        mainBox.getChildren().add(boxForVBoxes);
    }

    //-------------------------
    private void exposeFieldsOnTheLeftOfThisField(Field field){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++){
                GameButton button = gameMapButtons[i][j];
                boolean isOnLeft = button.getX() == field.getX() - 1 && button.getY() == field.getY();
                if(isOnLeft){
                    button.getStyleClass().remove("for-test-button");
                    button.getStyleClass().add("exposed-field-game-button");
                    if(button.getNumberOfBombsAround() == 0){
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheLeftOfThisField(field1);
                    }
                }
            }
        }
    }
    private void exposeFieldsOnTheRightOfThisField(Field field){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++){
                GameButton button = gameMapButtons[i][j];
                boolean isOnRight = button.getX() == field.getX() + 1 && button.getY() == field.getY();
                if(isOnRight){
                    button.getStyleClass().remove("for-test-button");
                    button.getStyleClass().add("exposed-field-game-button");
                    if(button.getNumberOfBombsAround() == 0){
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheRightOfThisField(field1);
                    }
                }
            }
        }
    }
    private void exposeFieldsOnTheTopOfThisField(Field field){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++){
                GameButton button = gameMapButtons[i][j];
                boolean isOnTop = button.getX() == field.getX() && button.getY() == field.getY() - 1;
                if(isOnTop){
                    button.getStyleClass().remove("for-test-button");
                    button.getStyleClass().add("exposed-field-game-button");
                    if(button.getNumberOfBombsAround() == 0){
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheTopOfThisField(field1);
                    }
                }
            }
        }
    }
    private void exposeFieldsOnTheDownOfThisField(Field field){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++){
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() && button.getY() == field.getY() + 1;
                if(isOnDown){
                    button.getStyleClass().remove("for-test-button");
                    button.getStyleClass().add("exposed-field-game-button");
                    if(button.getNumberOfBombsAround() == 0){
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheDownOfThisField(field1);
                    }
                }
            }
        }
    }
    private void exposeFieldsOnTheTopLeftOfThisField(Field field){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++){
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() - 1 && button.getY() == field.getY() - 1;
                if(isOnDown){
                    button.getStyleClass().remove("for-test-button");
                    button.getStyleClass().add("exposed-field-game-button");
                    if(button.getNumberOfBombsAround() == 0){
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheTopLeftOfThisField(field1);
                    }
                }
            }
        }
    }
    private void exposeFieldsOnTheTopRightOfThisField(Field field){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++){
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() + 1 && button.getY() == field.getY() - 1;
                if(isOnDown){
                    button.getStyleClass().remove("for-test-button");
                    button.getStyleClass().add("exposed-field-game-button");
                    if(button.getNumberOfBombsAround() == 0){
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheTopRightOfThisField(field1);
                    }
                }
            }
        }
    }
    private void exposeFieldsOnTheDownRightOfThisField(Field field){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++){
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() + 1 && button.getY() == field.getY() + 1;
                if(isOnDown){
                    button.getStyleClass().remove("for-test-button");
                    button.getStyleClass().add("exposed-field-game-button");
                    if(button.getNumberOfBombsAround() == 0){
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheDownRightOfThisField(field1);
                    }
                }
            }
        }
    }
    private void exposeFieldsOnTheDownLeftOfThisField(Field field){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++){
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() - 1 && button.getY() == field.getY() + 1;
                if(isOnDown){
                    button.getStyleClass().remove("for-test-button");
                    button.getStyleClass().add("exposed-field-game-button");
                    if(button.getNumberOfBombsAround() == 0){
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheDownLeftOfThisField(field1);
                    }
                }
            }
        }
    }



    private void fillEmptyStyles(){
        for(int i = 0; i < gameMapButtons.length; i++){
            for(int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton gameButton = gameMapButtons[i][j];

                if(!gameButton.getStyleClass().contains("exposed-field-game-button")) {
                    gameButton.getStyleClass().add("hidden-field-game-button");
                }

                //if(gameButton.getStyleClass().isEmpty()){
                //    gameButton.getStyleClass().add("hidden-field-game-button");
                //} else if (gameButton.getStyleClass().contains("game-button")) {
                //    gameButton.getStyleClass().remove("game-button");
                //    gameButton.getStyleClass().add("hidden-field-game-button");

            }
        }
    }

    private void addStylee(GameButton gameButton) {

        String styleToAdd = "";

        if (gameButton.isBomb()) {

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

    Stage stage2;
    private HBox startBox;
    private GameButton[][] buttons2;

    public int[] displayEmptyMapToGetXAndYClickedByUser() {
        stage2 = new Stage();
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.setTitle("Saper");

        startBox = new HBox();
        startBox.getStyleClass().add("main-box");
        startBox.setAlignment(Pos.CENTER);
        displayEmptyMap();

        int[] position = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                GameButton button = buttons2[i][j];
                button.setOnAction(event -> {
                    position[0] = button.getX();
                    position[1] = button.getY();
                    stage2.close();
                });
            }
        }

        Scene scene = new Scene(startBox, 675, 610);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage2.setScene(scene);
        stage2.showAndWait();

        return position;
    }

    private void displayEmptyMap() {
        buttons2 = new GameButton[8][8];
        HBox boxForVBoxes = new HBox(10);
        VBox boxForButtons = new VBox(10);
        boxForVBoxes.setAlignment(Pos.CENTER);
        boxForButtons.setAlignment(Pos.CENTER);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                GameButton button = new GameButton(i, j, 0, false);
                boxForButtons.getChildren().add(button);
                button.getStyleClass().add("game-button");
                buttons2[i][j] = button;
            }
            boxForVBoxes.getChildren().add(boxForButtons);
            boxForButtons = new VBox(10);
            boxForButtons.setAlignment(Pos.CENTER);
        }

        startBox.getChildren().add(boxForVBoxes);
    }

}
