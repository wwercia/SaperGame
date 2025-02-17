package com.example.sapergame;

import com.example.sapergame.gameElements.Field;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameView {

    final private int numberOfBombs = 15;
    private final GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    private VBox mainBox;
    private Field[][] gameMapFields = new Field[8][8];
    private GameButton[][] gameMapButtons = new GameButton[8][8];
    private Field fieldClickedByPlayer;
    private boolean markBombsButtonClicked = false;

    Stage gameStage;

    public void initGameView() {
        gameStage = new Stage();
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Saper");

        mainBox = new VBox(15);
        mainBox.getStyleClass().add("main-box");
        mainBox.setAlignment(Pos.CENTER);

        HBox boxForButtons = new HBox(10);
        boxForButtons.setAlignment(Pos.CENTER);

        Button newGameButton = new Button("New game");
        newGameButton.getStyleClass().add("play-button");
        newGameButton.setOnAction(event -> {
            gameStage.close();
            gameStage.close();
            gameController.startGame();
        });

        Button markWithBombButton = new Button("Mark with bomb");
        markWithBombButton.getStyleClass().add("not-clicked-mark-bomb-button");
        markWithBombButton.setOnAction(event -> {
            boolean isReady = isMapReady();
            System.out.println(isReady);
            if (isReady) {
                changeColorOfMarkWithBombButton(markWithBombButton);
                markBombsButtonClicked = !markBombsButtonClicked;
                if (markBombsButtonClicked) {
                    markBombsButtonClicked();
                } else {
                    setActionsOnButtons();
                }
            }
        });

        boxForButtons.getChildren().addAll(newGameButton, markWithBombButton);
        mainBox.getChildren().add(boxForButtons);

        //Button button = new Button("1");
        //button.getStyleClass().add("hidden-field-game-button");
        //mainBox.getChildren().add(button);
        //button.getStyleClass().add("test");


        initGameMapToGetFieldClickedByUser();

        Scene scene = new Scene(mainBox, 675, 670);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        gameStage.setScene(scene);
        gameStage.show();

    }

    private boolean isMapReady() {
        boolean isReady = false;
        for (GameButton[] gameMapButton : gameMapButtons) {
            for (GameButton gameButton : gameMapButton) {
                if (!gameButton.getStyleClass().contains("hidden-field-game-button")) {
                    isReady = true;
                    break;
                }
            }
        }
        return isReady;
    }

    private void initGameMapToGetFieldClickedByUser() {
        displayEmptyButtons();
        getPositionOfClickedButton();
    }

    private void displayEmptyButtons() {
        gameMapButtons = new GameButton[8][8];
        HBox boxForVBoxes = new HBox(10);
        VBox boxForButtons = new VBox(10);
        boxForVBoxes.setAlignment(Pos.CENTER);
        boxForButtons.setAlignment(Pos.CENTER);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                GameButton button = new GameButton(i, j, 0, false);
                button.setText("  ");
                boxForButtons.getChildren().add(button);
                button.getStyleClass().add("hidden-field-game-button");
                gameMapButtons[i][j] = button;
            }
            boxForVBoxes.getChildren().add(boxForButtons);
            boxForButtons = new VBox(10);
            boxForButtons.setAlignment(Pos.CENTER);
        }
        mainBox.getChildren().add(boxForVBoxes);
    }

    private void getPositionOfClickedButton() {
        Integer[] position = new Integer[2];
        for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton button = gameMapButtons[i][j];
                int finalI = i;
                int finalJ = j;
                button.setOnAction(event -> {
                    position[0] = button.getX();
                    position[1] = button.getY();
                    Field fieldClicked = new Field(0, false, finalI, finalJ);
                    Field[][] fields = gameController.initGameMap(fieldClicked);
                    fieldClickedByPlayer = fieldClicked;
                    this.gameMapFields = fields;
                    initGameMap(fieldClickedByPlayer);
                });
            }
        }
    }

    private void initGameMap(Field fieldClickedByUser) {
        initGameMapButtons();
        setButtonStartStyles(fieldClickedByUser);
        setActionsOnButtons();
    }

    private void initGameMapButtons() {
        for (int i = 0; i < gameMapFields.length; i++) {
            for (int j = 0; j < gameMapFields[i].length; j++) {
                Field field = gameMapFields[i][j];
                GameButton button = gameMapButtons[i][j];
                button.setText(String.valueOf(field.getBombsAroundThisField()));
                button.setNumberOfBombsAround(field.getBombsAroundThisField());
                button.setX(field.getX());
                button.setY(field.getY());
                button.setBomb(field.isBomb());
                if (button.isBomb()) {
                    button.setText("  ");
                }
                //gameMapButtons[i][j] = button;
            }
        }
    }

    private void setButtonStartStyles(Field fieldClickedByUser) {
        for (int i = 0; i < gameMapButtons.length; i++) {
            for (int j = 0; j < gameMapButtons[i].length; j++) {
                GameButton gameButton = gameMapButtons[i][j];
                gameButton.getStyleClass().add("hidden-field-game-button");
            }
        }
        exposeFieldsOnTop(fieldClickedByUser);
        exposeBelowFieldsFromTopFields();
        exposeFieldsBelow(fieldClickedByUser);
        exposeTopFieldsFromBelowFields();
    }

    // nie ruszaj tego! odpowiada za wyswietlenie odpowiednich pol na podstawie pierwszego klikniecia gracza
    private final ArrayList<Field> listOfFieldsFromTop = new ArrayList<>();

    private void exposeFieldsOnTop(Field fieldClickedByUser) {

        GameButton button = gameMapButtons[fieldClickedByUser.getX()][fieldClickedByUser.getY()];
        addStyle(button);

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
            if (fieldLeftSideOfBomb.getBombsAroundThisField() == 0) {
                if (!listOfFieldsFromBelow.contains(fieldLeftSideOfBomb) && !listOfFieldsFromTop.contains(fieldLeftSideOfBomb)) {
                    listOfFieldsFromTop.add(fieldLeftSideOfBomb);
                }
                exposeFieldsOnTop(fieldLeftSideOfBomb);
            } else {
                GameButton button1 = gameMapButtons[fieldLeftSideOfBomb.getX()][fieldLeftSideOfBomb.getY()];
                addStyle(button1);
            }
        }
        if (fieldTopLeftBomb != null) {
            if (fieldTopLeftBomb.getBombsAroundThisField() == 0) {
                if (!listOfFieldsFromBelow.contains(fieldTopLeftBomb) && !listOfFieldsFromTop.contains(fieldTopLeftBomb)) {
                    listOfFieldsFromTop.add(fieldTopLeftBomb);
                }
                exposeFieldsOnTop(fieldTopLeftBomb);
            } else {
                GameButton button1 = gameMapButtons[fieldTopLeftBomb.getX()][fieldTopLeftBomb.getY()];
                addStyle(button1);
            }
        }
        if (fieldAboveBomb != null) {
            if (fieldAboveBomb.getBombsAroundThisField() == 0) {
                if (!listOfFieldsFromBelow.contains(fieldAboveBomb) && !listOfFieldsFromTop.contains(fieldAboveBomb)) {
                    listOfFieldsFromTop.add(fieldAboveBomb);
                }
                exposeFieldsOnTop(fieldAboveBomb);
            } else {
                GameButton button1 = gameMapButtons[fieldAboveBomb.getX()][fieldAboveBomb.getY()];
                addStyle(button1);
            }
        }
        if (fieldTopRightBomb != null) {
            if (fieldTopRightBomb.getBombsAroundThisField() == 0) {
                if (!listOfFieldsFromBelow.contains(fieldTopRightBomb) && !listOfFieldsFromTop.contains(fieldTopRightBomb)) {
                    listOfFieldsFromTop.add(fieldTopRightBomb);
                }
                exposeFieldsOnTop(fieldTopRightBomb);
            } else {
                GameButton button1 = gameMapButtons[fieldTopRightBomb.getX()][fieldTopRightBomb.getY()];
                addStyle(button1);
            }
        }
    }

    private void exposeBelowFieldsFromTopFields() {
        System.out.println("in exposeBelowFieldsFromTopFields");
        System.out.println(listOfFieldsFromTop.size());
        for (Field field : listOfFieldsFromTop) {
            exposeFieldsBelow(field);
        }
    }

    private void exposeTopFieldsFromBelowFields() {
        System.out.println("in exposTopFieldsFromBelowFields");
        System.out.println(listOfFieldsFromBelow.size());
        for (Field field : listOfFieldsFromBelow) {
            exposeFieldsOnTop(field);
        }
    }

    private final ArrayList<Field> listOfFieldsFromBelow = new ArrayList<>();

    private void exposeFieldsBelow(Field field) {

        GameButton button = gameMapButtons[field.getX()][field.getY()];
        button.getStyleClass().remove("hidden-field-game-button");
        addStyle(button);

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
            if (fieldRightSideOfBomb.getBombsAroundThisField() == 0) {
                if (!listOfFieldsFromTop.contains(fieldRightSideOfBomb) && !listOfFieldsFromBelow.contains(fieldRightSideOfBomb)) {
                    listOfFieldsFromBelow.add(fieldRightSideOfBomb);
                }
                exposeFieldsBelow(fieldRightSideOfBomb);

            } else {
                GameButton button1 = gameMapButtons[fieldRightSideOfBomb.getX()][fieldRightSideOfBomb.getY()];
                addStyle(button1);
            }
        }

        if (fieldDownRightBomb != null) {
            if (fieldDownRightBomb.getBombsAroundThisField() == 0) {
                if (!listOfFieldsFromTop.contains(fieldDownRightBomb) && !listOfFieldsFromBelow.contains(fieldDownRightBomb)) {
                    listOfFieldsFromBelow.add(fieldDownRightBomb);
                }
                exposeFieldsBelow(fieldDownRightBomb);
            } else {
                GameButton button1 = gameMapButtons[fieldDownRightBomb.getX()][fieldDownRightBomb.getY()];
                addStyle(button1);
            }
        }
        if (fieldBelowBomb != null) {
            if (fieldBelowBomb.getBombsAroundThisField() == 0) {
                if (!listOfFieldsFromTop.contains(fieldBelowBomb) && !listOfFieldsFromBelow.contains(fieldBelowBomb)) {
                    listOfFieldsFromBelow.add(fieldBelowBomb);
                }
                exposeFieldsBelow(fieldBelowBomb);
            } else {
                GameButton button1 = gameMapButtons[fieldBelowBomb.getX()][fieldBelowBomb.getY()];
                addStyle(button1);
            }
        }
        if (fieldDownLeftBomb != null) {
            if (fieldDownLeftBomb.getBombsAroundThisField() == 0) {
                if (!listOfFieldsFromTop.contains(fieldDownLeftBomb) && !listOfFieldsFromBelow.contains(fieldDownLeftBomb)) {
                    listOfFieldsFromBelow.add(fieldDownLeftBomb);
                }
                exposeFieldsBelow(fieldDownLeftBomb);
            } else {
                GameButton button1 = gameMapButtons[fieldDownLeftBomb.getX()][fieldDownLeftBomb.getY()];
                addStyle(button1);
            }
        }
    }


    private void setActionsOnButtons() {
        for (GameButton[] gameMapButton : gameMapButtons) {
            for (GameButton button : gameMapButton) {
                button.setOnAction(event -> {
                    if (button.isBomb()) {
                        displayLoosingScreen();
                    } else if (button.getNumberOfBombsAround() == 0 && !button.isBomb()) {
                        Field field = new Field(button.getNumberOfBombsAround(), button.isBomb(), button.getX(), button.getY());
                        exposeFieldsOnTop(field);
                        exposeBelowFieldsFromTopFields();
                        exposeFieldsBelow(field);
                        exposeTopFieldsFromBelowFields();
                    }
                    addStyle(button);
                    button.setOnAction(event1 -> {
                    });
                    checkIfPlayerWon();
                });
            }
        }
    }

    private void addStyle(GameButton button) {
        button.getStyleClass().remove("hidden-field-game-button");
        button.getStyleClass().remove("hidden-field-game-button");
        button.getStyleClass().remove("hidden-field-game-button");
        button.getStyleClass().remove("marked-with-bomb-game-button");
        button.getStyleClass().remove("marked-with-bomb-game-button");
        int bombsAround = button.getNumberOfBombsAround();
        if (button.isBomb()) {
            button.getStyleClass().add("bomb-button");
        } else if (bombsAround == 0) {
            button.getStyleClass().add("zero-bomb-exposed-field-game-button");
        } else if (bombsAround == 1) {
            button.getStyleClass().add("one-bomb-exposed-field-game-button");
        } else if (bombsAround == 2) {
            button.getStyleClass().add("two-bombs-exposed-field-game-button");
        } else if (bombsAround == 3) {
            button.getStyleClass().add("three-bombs-exposed-field-game-button");
        } else if (bombsAround == 4) {
            button.getStyleClass().add("four-bombs-exposed-field-game-button");
        } else if (bombsAround == 5) {
            button.getStyleClass().add("five-bombs-exposed-field-game-button");
        } else if (bombsAround == 6) {
            button.getStyleClass().add("six-bombs-exposed-field-game-button");
        } else if (bombsAround == 7) {
            button.getStyleClass().add("seven-bombs-exposed-field-game-button");
        } else if (bombsAround == 8) {
            button.getStyleClass().add("eight-bombs-exposed-field-game-button");
        }
    }

    private void changeColorOfMarkWithBombButton(Button button) {
        if (button.getStyleClass().contains("clicked-mark-bomb-button")) {
            button.getStyleClass().remove("clicked-mark-bomb-button");
            button.getStyleClass().add("not-clicked-mark-bomb-button");
        } else {
            button.getStyleClass().remove("not-clicked-mark-bomb-button");
            button.getStyleClass().add("clicked-mark-bomb-button");
        }
    }

    private void markBombsButtonClicked() {
        for (GameButton[] gameMapButton : gameMapButtons) {
            for (GameButton button : gameMapButton) {
                button.setOnAction(event -> markWithBomb(button));
            }
        }
    }

    private void markWithBomb(Button button) {
        if (button.getStyleClass().contains("hidden-field-game-button")) {

            if (button.getStyleClass().contains("marked-with-bomb-game-button")) {
                button.getStyleClass().remove("marked-with-bomb-game-button");
            } else {
                button.getStyleClass().add("marked-with-bomb-game-button");
            }
        }
    }

    private void displayLoosingScreen() {
        blockButtons();
        displayAllBombs();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Saper");

        VBox box = new VBox(10);
        box.getStyleClass().add("main-box");
        box.setAlignment(Pos.CENTER);

        Label label = new Label("You lost!");
        label.getStyleClass().add("welcome-text");

        Button playAgainButton = new Button("Play again");
        playAgainButton.getStyleClass().add("play-button");
        playAgainButton.setOnAction(event -> {
            gameStage.close();
            stage.close();
            gameController.startGame();
        });

        box.getChildren().addAll(label, playAgainButton);

        Scene scene = new Scene(box, 200, 200);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void displayAllBombs() {
        for (GameButton[] gameMapButton : gameMapButtons) {
            for (GameButton button : gameMapButton) {
                if (button.isBomb()) {
                    addStyle(button);
                }
            }
        }
    }

    private void checkIfPlayerWon() {

        int numberOfHiddenButtons = 0;

        for (GameButton[] gameMapButton : gameMapButtons) {
            for (GameButton button : gameMapButton) {
                if (button.getStyleClass().contains("hidden-field-game-button")) {
                    numberOfHiddenButtons++;
                }
            }
        }
        if (numberOfHiddenButtons == numberOfBombs) {
            blockButtons();
            displayAllBombs();
            displayWiningScreen();
        }
        System.out.println(numberOfHiddenButtons);

    }

    private void displayWiningScreen() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Saper");

        VBox box = new VBox(10);
        box.getStyleClass().add("main-box");
        box.setAlignment(Pos.CENTER);

        Label label = new Label("You Won!");
        label.getStyleClass().add("welcome-text");
        Label label2 = new Label("Congratulations!");
        label2.getStyleClass().add("welcome-text");

        Button playAgainButton = new Button("Play again");
        playAgainButton.getStyleClass().add("play-button");
        playAgainButton.setOnAction(event -> {
            gameStage.close();
            stage.close();
            gameController.startGame();
        });

        box.getChildren().addAll(label, label2, playAgainButton);

        Scene scene = new Scene(box, 350, 200);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void blockButtons() {
        for (GameButton[] gameMapButton : gameMapButtons) {
            for (GameButton button : gameMapButton) {
                button.setOnAction(event -> {
                });
            }
        }
    }
}
