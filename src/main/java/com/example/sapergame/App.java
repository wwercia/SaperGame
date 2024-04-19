package com.example.sapergame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        View view = new View();
        VBox root = view.initView(stage);
        Scene scene = new Scene(root, 400, 200);
        stage.setTitle("Saper");
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
