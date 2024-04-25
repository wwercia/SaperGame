package com.example.sapergame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartView {

    public VBox initView(Stage stage) {
        VBox mainBox = new VBox(20);
        mainBox.getStyleClass().add("main-box");
        mainBox.setAlignment(Pos.CENTER);
        Label welcomeText = new Label("Welcome to Saper game!");
        welcomeText.getStyleClass().add("welcome-text");
        Button playButton = new Button("Play");
        playButton.getStyleClass().add("play-button");
        playButton.setOnAction(event -> {
            stage.hide();
            startGame();
        });
        mainBox.getChildren().addAll(welcomeText, playButton);
        return mainBox;
    }

    private void startGame(){
        GameController gameController = new GameController();
        gameController.startGame();
    }

}
