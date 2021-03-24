package cz.gjkt.view;

import cz.gjkt.application.Main;
import cz.gjkt.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpravaKurzuController implements Initializable {

    KurzyController kurzController;
    Scene kurzyScene;
    Kurz kurz;

    @FXML
    TextField nazev;

    @FXML
    ChoiceBox<SkolniRok> skolniRok;

    @FXML
    ChoiceBox<Predmet> predmet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        skolniRok.getItems().addAll(getSkolniRoky());
        predmet.getItems().addAll(getPredmety());


    }

    private ObservableList<SkolniRok> getSkolniRoky(){
        SkolniRokyDAOJDBC daosr = new SkolniRokyDAOJDBC();
        return FXCollections.observableList(daosr.getAll());
    }

    private ObservableList<Predmet> getPredmety(){
        PredmetyDAOJDBC daop = new PredmetyDAOJDBC();
        return FXCollections.observableList(daop.getAll());
    }

    private void setNazev(String nazev){this.nazev.setText(nazev);}

    public void setKurzyScene(Scene scene){kurzyScene = scene;}
    public void setKurzyController(KurzyController controller){kurzController = controller;}

    public void setKurz(Kurz kurz){
        this.kurz = kurz;
        setNazev(kurz.getNazev());
    }

    public void handleZahodButton() {
        Main.getPrimaryStage().setScene(kurzyScene);
    }

    public void handleUlozButton(){
        KurzyDAOJDBC kurzyDAO = new KurzyDAOJDBC();
        kurz.setNazev(nazev.getText());
        kurz.setSkolniRok(skolniRok.getValue().getId());
        kurz.setPredmet(predmet.getValue().getId());
        kurzyDAO.update(kurz);
        Main.getPrimaryStage().setScene(kurzyScene);
        kurzController.refresh();
    }

}
