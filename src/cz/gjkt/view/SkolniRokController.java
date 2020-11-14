package cz.gjkt.view;

import cz.gjkt.application.Main;
import cz.gjkt.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import javax.swing.text.LabelView;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SkolniRokController implements Initializable {

    SkolniRokyController skolniRokyController;
    Scene skolniRokyScene;
    SkolniRok skolniRok;

    @FXML
    TextField nazev;

    @FXML
    TextField zacatek;

    @FXML
    TextField konec;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setNazev(String nazev){this.nazev.setText(nazev);}
    private void setZacatek(String zacatek){this.zacatek.setText(zacatek);}
    private void setKonec(String konec){this.konec.setText(konec);}

    public void setSkolniRokyScene(Scene scene){skolniRokyScene = scene;}
    public void setSkolniRokController(SkolniRokyController controller){skolniRokyController = controller;}

    public void setSkolniRok(SkolniRok skolniRok){
        this.skolniRok = skolniRok;
        setNazev(skolniRok.getNazev());
        setZacatek(skolniRok.getZacatek());
        setKonec(skolniRok.getKonec());
    }

    public void handleZahodButton() {
        Main.getPrimaryStage().setScene(skolniRokyScene);
    }

    public void handleUlozButton(){
        SkolniRokyDAOJDBC skolniRokyDAO = new SkolniRokyDAOJDBC();
        skolniRok.setNazev(nazev.getText());
        skolniRok.setZacatek(zacatek.getText());
        skolniRok.setKonec(konec.getText());
        skolniRokyDAO.update(skolniRok);
        Main.getPrimaryStage().setScene(skolniRokyScene);
        skolniRokyController.refresh();
    }

}
