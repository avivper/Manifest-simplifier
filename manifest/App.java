package com.manifest;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Manifest Simplifier");
        stage.setResizable(false);
        stage.setWidth(400);
        stage.setHeight(400);
        input(stage);

        stage.show();
    }

    public void input(Stage stage) {
        StackPane inputPane = new StackPane();
        inputPane.getChildren().addAll(VerticalInput(stage));
        inputPane.setAlignment(Pos.TOP_CENTER);
        inputPane.setPadding(new Insets(40, 40, 40, 40));

        Scene inputScene = new Scene(inputPane, 200, 200);
        stage.setScene(inputScene);
    }

    public VBox VerticalInput(Stage stage) {
        VBox inputBox = new VBox();

        TextField input = new TextField();
        input.setMinWidth(150);
        input.setMaxWidth(200);

        Button browse = new Button("Browse");

        browse.setOnAction(e -> {
            InputFile inputFile = new InputFile();
            String path = inputFile.getPath();
            if (path != null) {
                input.setText(path);
            }
        });


        inputBox.getChildren().addAll(input, browse);
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.TOP_CENTER);
        inputBox.setPadding(new Insets(10, 10, 10, 10));

        return inputBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
