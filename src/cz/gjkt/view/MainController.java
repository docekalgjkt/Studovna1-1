package cz.gjkt.view;

import cz.gjkt.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    Button kurzId;

    public void selectPredmety(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/Predmety.fxml"));
        AnchorPane rootLayout = null;
        try {
            rootLayout = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        
        Main.getPrimaryStage().setScene(scene);


    }

    public void selectStudenti(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/Student.fxml"));
        AnchorPane rootLayout = null;
        try {
            rootLayout = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);

        Main.getPrimaryStage().setScene(scene);


    }

    public void selectSkolniRoky(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/SkolniRoky.fxml"));
        AnchorPane rootLayout = null;
        try {
            rootLayout = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);

        Main.getPrimaryStage().setScene(scene);


    }

    public void selectKurzy(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/Kurzy.fxml"));
        AnchorPane rootLayout = null;
        try {
            rootLayout = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);

        Main.getPrimaryStage().setScene(scene);


    }




}
