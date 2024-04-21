package com.example.sapergame;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameView {

    private GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    private HBox mainBox;

    public void initGameView() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Saper");

        mainBox = new HBox();
        mainBox.getStyleClass().add("main-box");
        Label test = new Label("playing game");
        test.getStyleClass().add("welcome-text");
        mainBox.getChildren().add(test);

        Scene scene = new Scene(mainBox, 400, 200);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void initGAmeMap(){



    }

}
