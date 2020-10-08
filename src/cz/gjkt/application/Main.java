package cz.gjkt.application;

import cz.gjkt.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static Stage primaryStage;
    private AnchorPane rootLayout;

    public static void main(String[] args){
        launch();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        initRootLayoutFXML();
    }

    public void initRootLayoutCode() {
        AnchorPane root = new AnchorPane();
        Label label1 = new Label();
        label1.setText("Nadpis");
        label1.setLayoutX(10);
        label1.setLayoutY(10);
        root.getChildren().add(label1);
        Scene scene = new Scene(root, 400, 300, Color.GRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initRootLayoutFXML() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Main.fxml"));
            rootLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
