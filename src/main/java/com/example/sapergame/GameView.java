package com.example.sapergame;

import com.example.sapergame.gameElements.Field;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

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
            /*
            exposeFieldsOnTheLeftOfThisField(fieldClickedByUser);
            exposeFieldsOnTheRightOfThisField(fieldClickedByUser);
            exposeFieldsOnTheTopOfThisField(fieldClickedByUser);
            exposeFieldsOnTheDownOfThisField(fieldClickedByUser);
            exposeFieldsOnTheTopLeftOfThisField(fieldClickedByUser);
            exposeFieldsOnTheTopRightOfThisField(fieldClickedByUser);
            exposeFieldsOnTheDownRightOfThisField(fieldClickedByUser);
            exposeFieldsOnTheDownLeftOfThisField(fieldClickedByUser);

             */


            boxForVBoxes.getChildren().add(boxForButtons);
            boxForButtons = new VBox(10);
            boxForButtons.setAlignment(Pos.CENTER);
        }
        exposeFieldsOnTop(fieldClickedByUser);
        exposeBelowFieldsFromTopFields();
        exposeFieldsBelow(fieldClickedByUser);
        exposeTopFieldsFromBelowFields();

        mainBox.getChildren().add(boxForVBoxes);
    }

    //------------------------

    private ArrayList<Field> listOfFieldsFromTop = new ArrayList<>();

    private void exposeFieldsOnTop(Field fieldClickedByUser){

        GameButton button = gameMapButtons[fieldClickedByUser.getX()][fieldClickedByUser.getY()];
        button.getStyleClass().remove("for-test-button");
        button.getStyleClass().add("exposed-field-game-button");

        boolean isFieldOnLeft = true;
        boolean isFieldOnTop = true;
        boolean isFieldOnRight = true;

        int x = fieldClickedByUser.getX();
        int y = fieldClickedByUser.getY();

        if (x == 0) {
            isFieldOnLeft = false;
        }
        if (x == 7) {
            isFieldOnRight = false;
            // nie ma pola po prawej, prawym rogu, prawej górze
        }
        if (y == 0) {
            isFieldOnTop = false;
            // nie ma pola na górze, górnym lewym rogu, górnym prawym rogu
        }

        Field fieldLeftSideOfBomb = null;
        if (isFieldOnLeft) {
            fieldLeftSideOfBomb = gameMapFields[x - 1][y];
        }
        Field fieldAboveBomb = null;
        if (isFieldOnTop) {
            fieldAboveBomb = gameMapFields[x][y - 1];
        }
        Field fieldTopLeftBomb = null;
        if (isFieldOnLeft && isFieldOnTop) {
            fieldTopLeftBomb = gameMapFields[x - 1][y - 1];
        }
        Field fieldTopRightBomb = null;
        if (isFieldOnRight && isFieldOnTop) {
            fieldTopRightBomb = gameMapFields[x + 1][y - 1];
        }


        if (fieldLeftSideOfBomb != null) {
            if(fieldLeftSideOfBomb.getBombsAoundThisField() == 0){
                if(!listOfFieldsFromBelow.contains(fieldLeftSideOfBomb) && !listOfFieldsFromTop.contains(fieldLeftSideOfBomb)){
                    listOfFieldsFromTop.add(fieldLeftSideOfBomb);
                }
                exposeFieldsOnTop(fieldLeftSideOfBomb);
            }else {
                GameButton button1 = gameMapButtons[fieldLeftSideOfBomb.getX()][fieldLeftSideOfBomb.getY()];
                button1.getStyleClass().remove("for-test-button");
                button1.getStyleClass().add("exposed-field-game-button");
            }
        }
        if (fieldTopLeftBomb != null) {
            if(fieldTopLeftBomb.getBombsAoundThisField() == 0){
                if(!listOfFieldsFromBelow.contains(fieldTopLeftBomb) && !listOfFieldsFromTop.contains(fieldTopLeftBomb)){
                    listOfFieldsFromTop.add(fieldTopLeftBomb);
                }
                exposeFieldsOnTop(fieldTopLeftBomb);
            }else {
                GameButton button1 = gameMapButtons[fieldTopLeftBomb.getX()][fieldTopLeftBomb.getY()];
                button1.getStyleClass().remove("for-test-button");
                button1.getStyleClass().add("exposed-field-game-button");
            }
        }
        if (fieldAboveBomb != null) {
            if(fieldAboveBomb.getBombsAoundThisField() == 0){
                if(!listOfFieldsFromBelow.contains(fieldAboveBomb) && !listOfFieldsFromTop.contains(fieldAboveBomb)){
                    listOfFieldsFromTop.add(fieldAboveBomb);
                }
                exposeFieldsOnTop(fieldAboveBomb);
            }else {
                GameButton button1 = gameMapButtons[fieldAboveBomb.getX()][fieldAboveBomb.getY()];
                button1.getStyleClass().remove("for-test-button");
                button1.getStyleClass().add("exposed-field-game-button");
            }
        }
        if (fieldTopRightBomb != null) {
            if(fieldTopRightBomb.getBombsAoundThisField() == 0){
                if(!listOfFieldsFromBelow.contains(fieldTopRightBomb) && !listOfFieldsFromTop.contains(fieldTopRightBomb)){
                    listOfFieldsFromTop.add(fieldTopRightBomb);
                }
                exposeFieldsOnTop(fieldTopRightBomb);
            }else {
                GameButton button1 = gameMapButtons[fieldTopRightBomb.getX()][fieldTopRightBomb.getY()];
                button1.getStyleClass().remove("for-test-button");
                button1.getStyleClass().add("exposed-field-game-button");
            }
        }
    }

    private void exposeBelowFieldsFromTopFields(){
        System.out.println("in exposeBelowFieldsFromTopFields");
        System.out.println(listOfFieldsFromTop.size());
        for(Field field : listOfFieldsFromTop){
            exposeFieldsBelow(field);
        }
    }
    private void exposeTopFieldsFromBelowFields(){
        System.out.println("in exposTopFieldsFromBelowFields");
        System.out.println(listOfFieldsFromBelow.size());
        for(Field field : listOfFieldsFromBelow){
            exposeFieldsOnTop(field);
        }
    }

    private ArrayList<Field> listOfFieldsFromBelow = new ArrayList<>();

    private void exposeFieldsBelow(Field field){

        GameButton button = gameMapButtons[field.getX()][field.getY()];
        button.getStyleClass().remove("for-test-button");
        button.getStyleClass().add("exposed-field-game-button");

        boolean isFieldOnLeft = true;
        boolean isFieldOnRight = true;
        boolean isFieldBelow = true;

        int x = field.getX();
        int y = field.getY();

        if (x == 0) {
            isFieldOnLeft = false;
        }
        if (x == 7) {
            isFieldOnRight = false;
            // nie ma pola po prawej, prawym rogu, prawej górze
        }
        if (y == 7) {
            isFieldBelow = false;
            // nie ma pola na dole, dolnym lewym rogu, dolnym prawym rogu
        }


        Field fieldRightSideOfBomb = null;
        if (isFieldOnRight) {
            fieldRightSideOfBomb = gameMapFields[x + 1][y];
        }
        Field fieldBelowBomb = null;
        if (isFieldBelow) {
            fieldBelowBomb = gameMapFields[x][y + 1];
        }
        Field fieldDownLeftBomb = null;
        if (isFieldOnLeft && isFieldBelow) {
            fieldDownLeftBomb = gameMapFields[x - 1][y + 1];
        }
        Field fieldDownRightBomb = null;
        if (isFieldOnRight && isFieldBelow) {
            fieldDownRightBomb = gameMapFields[x + 1][y + 1];
        }

        if (fieldRightSideOfBomb != null) {
            if(fieldRightSideOfBomb.getBombsAoundThisField() == 0){
                if(!listOfFieldsFromTop.contains(fieldRightSideOfBomb) && !listOfFieldsFromBelow.contains(fieldRightSideOfBomb)){
                    listOfFieldsFromBelow.add(fieldRightSideOfBomb);
                }
                exposeFieldsBelow(fieldRightSideOfBomb);

            }else {
                GameButton button1 = gameMapButtons[fieldRightSideOfBomb.getX()][fieldRightSideOfBomb.getY()];
                button1.getStyleClass().remove("for-test-button");
                button1.getStyleClass().add("exposed-field-game-button");
            }
        }

        if (fieldDownRightBomb != null) {
            if(fieldDownRightBomb.getBombsAoundThisField() == 0){
                if(!listOfFieldsFromTop.contains(fieldDownRightBomb) && !listOfFieldsFromBelow.contains(fieldDownRightBomb)){
                    listOfFieldsFromBelow.add(fieldDownRightBomb);
                }
                exposeFieldsBelow(fieldDownRightBomb);
            }else {
                GameButton button1 = gameMapButtons[fieldDownRightBomb.getX()][fieldDownRightBomb.getY()];
                button1.getStyleClass().remove("for-test-button");
                button1.getStyleClass().add("exposed-field-game-button");
            }
        }
        if (fieldBelowBomb != null) {
            if(fieldBelowBomb.getBombsAoundThisField() == 0){
                if(!listOfFieldsFromTop.contains(fieldBelowBomb) && !listOfFieldsFromBelow.contains(fieldBelowBomb)){
                    listOfFieldsFromBelow.add(fieldBelowBomb);
                }
                exposeFieldsBelow(fieldBelowBomb);
            }else {
                GameButton button1 = gameMapButtons[fieldBelowBomb.getX()][fieldBelowBomb.getY()];
                button1.getStyleClass().remove("for-test-button");
                button1.getStyleClass().add("exposed-field-game-button");
            }
        }
        if (fieldDownLeftBomb != null) {
            if(fieldDownLeftBomb.getBombsAoundThisField() == 0){
                if(!listOfFieldsFromTop.contains(fieldDownLeftBomb) && !listOfFieldsFromBelow.contains(fieldDownLeftBomb)){
                    listOfFieldsFromBelow.add(fieldDownLeftBomb);
                }
                exposeFieldsBelow(fieldDownLeftBomb);
            }else {
                GameButton button1 = gameMapButtons[fieldDownLeftBomb.getX()][fieldDownLeftBomb.getY()];
                button1.getStyleClass().remove("for-test-button");
                button1.getStyleClass().add("exposed-field-game-button");
            }
        }

    }



    //-------------------------

    private HashMap<Integer, ArrayList<Field>> zeroFields = new HashMap<>();

    private void exposeFieldss(Field fieldClickedByUser) {

        for(int i = 0; i < gameMapFields.length; i++){
            for(int j = 0; j < gameMapFields[i].length; j++){
                GameButton gameButton = gameMapButtons[i][j];
                if(gameButton.getNumberOfBombsAround() == 0){

                }
            }
        }

    }


    //nie działa
    private void exposeFieldsOnTheLeftOfThisField(Field field) {


        for (int i = 0; i < field.getX(); i++) {
            GameButton button = gameMapButtons[i][field.getY()];
            boolean isOnLeft = button.getX() == field.getX() - 1 && button.getY() == field.getY();
            if (isOnLeft) {
                button.getStyleClass().remove("for-test-button");

                if (!button.getStyleClass().contains("exposed-field-game-button")) {
                    button.getStyleClass().add("exposed-field-game-button");
                }
                if (button.getNumberOfBombsAround() == 0) {
                    Field field1 = gameMapFields[i][field.getY()];
                    exposeFieldsOnTheLeftOfThisField(field1);

                    //test czy zadziala to tak
                    exposeFieldsOnTheTopLeftOfThisField(field1);
                    exposeFieldsOnTheDownLeftOfThisField(field1);
                }
            }
        }



/*
        for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                boolean isOnLeft = button.getX() == field.getX() - 1 && button.getY() == field.getY();
                if (isOnLeft) {
                    button.getStyleClass().remove("for-test-button");

                    if (!button.getStyleClass().contains("exposed-field-game-button")) {
                        button.getStyleClass().add("exposed-field-game-button");
                    }
                    if (button.getNumberOfBombsAround() == 0) {
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheLeftOfThisField(field1);

                        //test czy zadziala to tak
                        exposeFieldsOnTheTopLeftOfThisField(field1);
                        exposeFieldsOnTheDownLeftOfThisField(field1);
                    }
                }
            }
        }*/
    }

    private void exposeFieldsOnTheRightOfThisField(Field field) {

        for (int i = 7; i > field.getX(); i--) {
            GameButton button = gameMapButtons[i][field.getY()];
            boolean isOnLeft = button.getX() == field.getX() + 1 && button.getY() == field.getY();
            if (isOnLeft) {
                button.getStyleClass().remove("for-test-button");

                if (!button.getStyleClass().contains("exposed-field-game-button")) {
                    button.getStyleClass().add("exposed-field-game-button");
                }
                if (button.getNumberOfBombsAround() == 0) {
                    Field field1 = gameMapFields[i][field.getY()];
                    exposeFieldsOnTheRightOfThisField(field1);

                    //test czy zadziala to tak
                    exposeFieldsOnTheTopRightOfThisField(field1);
                    exposeFieldsOnTheDownRightOfThisField(field1);
                }
            }
        }

        /*
        for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                boolean isOnRight = button.getX() == field.getX() + 1 && button.getY() == field.getY();
                if (isOnRight) {
                    button.getStyleClass().remove("for-test-button");

                    if (!button.getStyleClass().contains("exposed-field-game-button")) {
                        button.getStyleClass().add("exposed-field-game-button");
                    }
                    if (button.getNumberOfBombsAround() == 0) {
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheRightOfThisField(field1);

                        exposeFieldsOnTheTopRightOfThisField(field1);
                        exposeFieldsOnTheDownRightOfThisField(field1);
                    }
                }
            }
        }

         */
    }

    private void exposeFieldsOnTheTopOfThisField(Field field) {

        for (int i = field.getY(); i >= 0; i--) {
            GameButton button = gameMapButtons[i][field.getY()];
            boolean isOnLeft = button.getX() == field.getX() && button.getY() == field.getY() - 1;
            if (isOnLeft) {
                button.getStyleClass().remove("for-test-button");

                if (!button.getStyleClass().contains("exposed-field-game-button")) {
                    button.getStyleClass().add("exposed-field-game-button");
                }
                if (button.getNumberOfBombsAround() == 0) {
                    Field field1 = gameMapFields[i][field.getY()];
                    exposeFieldsOnTheTopOfThisField(field1);

                    exposeFieldsOnTheTopLeftOfThisField(field1);
                    exposeFieldsOnTheTopRightOfThisField(field1);
                }
            }
        }

        /*
        for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                boolean isOnTop = button.getX() == field.getX() && button.getY() == field.getY() - 1;
                if (isOnTop) {
                    button.getStyleClass().remove("for-test-button");

                    if (!button.getStyleClass().contains("exposed-field-game-button")) {
                        button.getStyleClass().add("exposed-field-game-button");
                    }
                    if (button.getNumberOfBombsAround() == 0) {
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheTopOfThisField(field1);

                        exposeFieldsOnTheTopLeftOfThisField(field1);
                        exposeFieldsOnTheTopRightOfThisField(field1);
                    }
                }
            }
        }

         */
    }

    private void exposeFieldsOnTheDownOfThisField(Field field) {
        for (int i = 7; i > field.getY(); i--) {
            GameButton button = gameMapButtons[i][field.getY()];
            boolean isOnLeft = button.getX() == field.getX() && button.getY() == field.getY() + 1;
            if (isOnLeft) {
                button.getStyleClass().remove("for-test-button");

                if (!button.getStyleClass().contains("exposed-field-game-button")) {
                    button.getStyleClass().add("exposed-field-game-button");
                }
                if (button.getNumberOfBombsAround() == 0) {
                    Field field1 = gameMapFields[i][field.getY()];
                    exposeFieldsOnTheDownOfThisField(field1);

                    exposeFieldsOnTheDownRightOfThisField(field1);
                    exposeFieldsOnTheDownLeftOfThisField(field1);
                }
            }
        }
        /*
        for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() && button.getY() == field.getY() + 1;
                if (isOnDown) {
                    button.getStyleClass().remove("for-test-button");

                    if (!button.getStyleClass().contains("exposed-field-game-button")) {
                        button.getStyleClass().add("exposed-field-game-button");
                    }
                    if (button.getNumberOfBombsAround() == 0) {
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheDownOfThisField(field1);

                        exposeFieldsOnTheDownRightOfThisField(field1);
                        exposeFieldsOnTheDownLeftOfThisField(field1);
                    }
                }
            }
        }

         */
    }

    private void exposeFieldsOnTheTopLeftOfThisField(Field field) {
        for (int i = 0, j = 0; i < field.getX() && j < field.getY(); i++, j++) {
            GameButton button = gameMapButtons[i][j];
            boolean isOnLeft = button.getX() == field.getX() - 1 && button.getY() == field.getY() - 1;
            if (isOnLeft) {
                button.getStyleClass().remove("for-test-button");

                if (!button.getStyleClass().contains("exposed-field-game-button")) {
                    button.getStyleClass().add("exposed-field-game-button");
                }
                if (button.getNumberOfBombsAround() == 0) {
                    Field field1 = gameMapFields[i][j];
                    exposeFieldsOnTheTopLeftOfThisField(field1);

                    exposeFieldsOnTheLeftOfThisField(field1);
                    exposeFieldsOnTheTopOfThisField(field1);
                }
            }
        }
        /*for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() - 1 && button.getY() == field.getY() - 1;
                if (isOnDown) {
                    button.getStyleClass().remove("for-test-button");

                    if (!button.getStyleClass().contains("exposed-field-game-button")) {
                        button.getStyleClass().add("exposed-field-game-button");
                    }
                    if (button.getNumberOfBombsAround() == 0) {
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheTopLeftOfThisField(field1);

                        exposeFieldsOnTheLeftOfThisField(field1);
                        exposeFieldsOnTheTopOfThisField(field1);
                    }
                }
            }
        }

         */
    }

    private void exposeFieldsOnTheTopRightOfThisField(Field field) {
        for (int i = field.getX(), j = 0; i < 7 && j < field.getY(); i++, j++) {
            GameButton button = gameMapButtons[i][j];
            boolean isOnLeft = button.getX() == field.getX() + 1 && button.getY() == field.getY() - 1;
            if (isOnLeft) {
                button.getStyleClass().remove("for-test-button");

                if (!button.getStyleClass().contains("exposed-field-game-button")) {
                    button.getStyleClass().add("exposed-field-game-button");
                }
                if (button.getNumberOfBombsAround() == 0) {
                    Field field1 = gameMapFields[i][j];
                    exposeFieldsOnTheTopRightOfThisField(field1);

                    exposeFieldsOnTheTopOfThisField(field1);
                    exposeFieldsOnTheTopLeftOfThisField(field1);
                }
            }
        }
        /*for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() + 1 && button.getY() == field.getY() - 1;
                if (isOnDown) {
                    button.getStyleClass().remove("for-test-button");

                    if (!button.getStyleClass().contains("exposed-field-game-button")) {
                        button.getStyleClass().add("exposed-field-game-button");
                    }
                    if (button.getNumberOfBombsAround() == 0) {
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheTopRightOfThisField(field1);

                        exposeFieldsOnTheLeftOfThisField(field1);
                        exposeFieldsOnTheTopOfThisField(field1);
                        exposeFieldsOnTheDownOfThisField(field1);
                        exposeFieldsOnTheTopLeftOfThisField(field1);
                        exposeFieldsOnTheDownRightOfThisField(field1);
                        exposeFieldsOnTheDownLeftOfThisField(field1);
                    }
                }
            }
        }

         */
    }

    private void exposeFieldsOnTheDownRightOfThisField(Field field) {
        for (int i = 0; i < field.getX(); i++) {
            GameButton button = gameMapButtons[i][field.getY()];
            boolean isOnLeft = button.getX() == field.getX() + 1 && button.getY() == field.getY() + 1;
            if (isOnLeft) {
                button.getStyleClass().remove("for-test-button");

                if (!button.getStyleClass().contains("exposed-field-game-button")) {
                    button.getStyleClass().add("exposed-field-game-button");
                }
                if (button.getNumberOfBombsAround() == 0) {
                    Field field1 = gameMapFields[i][field.getY()];
                    exposeFieldsOnTheDownRightOfThisField(field1);

                    exposeFieldsOnTheDownOfThisField(field1);
                    exposeFieldsOnTheRightOfThisField(field1);
                }
            }
        }
        /*for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() + 1 && button.getY() == field.getY() + 1;
                if (isOnDown) {
                    button.getStyleClass().remove("for-test-button");

                    if (!button.getStyleClass().contains("exposed-field-game-button")) {
                        button.getStyleClass().add("exposed-field-game-button");
                    }
                    if (button.getNumberOfBombsAround() == 0) {
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheDownRightOfThisField(field1);

                        exposeFieldsOnTheLeftOfThisField(field1);
                        exposeFieldsOnTheTopOfThisField(field1);
                        exposeFieldsOnTheDownOfThisField(field1);
                        exposeFieldsOnTheTopLeftOfThisField(field1);
                        exposeFieldsOnTheTopRightOfThisField(field1);
                        exposeFieldsOnTheDownLeftOfThisField(field1);
                    }
                }
            }
        }

         */
    }

    private void exposeFieldsOnTheDownLeftOfThisField(Field field) {
        for (int i = 0; i < field.getX(); i++) {
            GameButton button = gameMapButtons[i][field.getY()];
            boolean isOnLeft = button.getX() == field.getX() - 1 && button.getY() == field.getY() + 1;
            if (isOnLeft) {
                button.getStyleClass().remove("for-test-button");

                if (!button.getStyleClass().contains("exposed-field-game-button")) {
                    button.getStyleClass().add("exposed-field-game-button");
                }
                if (button.getNumberOfBombsAround() == 0) {
                    Field field1 = gameMapFields[i][field.getY()];
                    exposeFieldsOnTheDownLeftOfThisField(field1);

                    exposeFieldsOnTheLeftOfThisField(field1);
                    exposeFieldsOnTheDownOfThisField(field1);
                }
            }
        }
        /*for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                boolean isOnDown = button.getX() == field.getX() - 1 && button.getY() == field.getY() + 1;
                if (isOnDown) {
                    button.getStyleClass().remove("for-test-button");

                    if (!button.getStyleClass().contains("exposed-field-game-button")) {
                        button.getStyleClass().add("exposed-field-game-button");
                    }
                    if (button.getNumberOfBombsAround() == 0) {
                        Field field1 = gameMapFields[i][j];
                        exposeFieldsOnTheDownLeftOfThisField(field1);

                        exposeFieldsOnTheLeftOfThisField(field1);
                        exposeFieldsOnTheTopOfThisField(field1);
                        exposeFieldsOnTheDownOfThisField(field1);
                        exposeFieldsOnTheTopLeftOfThisField(field1);
                        exposeFieldsOnTheTopRightOfThisField(field1);
                        exposeFieldsOnTheDownRightOfThisField(field1);
                    }
                }
            }
        }

         */
    }


    private void fillEmptyStyles() {
        for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton gameButton = gameMapButtons[i][j];

                if (!gameButton.getStyleClass().contains("exposed-field-game-button")) {
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
