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
        init(stage);

        stage.show();
    }

    public void init(Stage stage) {
        StackPane inputPane = new StackPane();
        inputPane.getChildren().addAll(VerticalParent(stage));
        inputPane.setAlignment(Pos.TOP_CENTER);
        inputPane.setPadding(new Insets(40, 40, 40, 40));

        Scene inputScene = new Scene(inputPane, 200, 200);
        stage.setScene(inputScene);

    }

    public VBox VerticalOutput() {
        VBox outputBox = new VBox();

        Button execute = new Button("Execute");
        outputBox.getChildren().add(execute);
        outputBox.setSpacing(10);
        outputBox.setAlignment(Pos.BOTTOM_CENTER);
        outputBox.setPadding(new Insets(20, 20, 20, 20));

        return outputBox;
    }

    public VBox VerticalInput() {
        VBox inputBox = new VBox();
        TextField input = new TextField();
        input.setMinWidth(150);
        input.setMaxWidth(200);

        Button browse = new Button("Browse");

        browse.setOnAction(e -> {
            InputFile inputFile = new InputFile();
            String path = inputFile.getPath();
            if (path != null) {  // todo: exclude non-xlsx files
                input.setText(path);
            } else {
                System.out.println("Invalid"); // todo: throw error msg
            }
        });

        inputBox.getChildren().addAll(input, browse);
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.TOP_CENTER);
        inputBox.setPadding(new Insets(10, 10, 10, 10));

        return inputBox;
    }

    public VBox VerticalParent(Stage stage) {
        VBox parent = new VBox();
        parent.getChildren().addAll(VerticalInput(), VerticalOutput());
        parent.setAlignment(Pos.CENTER);
        parent.setSpacing(10);
        parent.setPadding(new Insets(10, 10, 10, 10));

        return parent;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
