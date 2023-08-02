package com.example.joshuaburt_comp1011sec005_lab03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Assignment3Application extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Assignment3Application.class.getResource("assignment3View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1150, 270);
        stage.setTitle("Game Database");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
