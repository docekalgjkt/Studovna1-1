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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.text.LabelView;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class KurzController implements Initializable {

    @FXML
    TableView tableView;

    StudentiKurzuDAO studentiKurzuDAO = new StudentiKurzuDAO();
    List<Student> studentiKurzu;
    ObservableList<Student> items;
    ObservableList<Student> selectedItems = null;

    public void fillTable(){
        studentiKurzu = studentiKurzuDAO.getAll();
        items = FXCollections.observableList(studentiKurzu);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, Student> jmenoColumn = new TableColumn<>("Jméno");
        jmenoColumn.setCellValueFactory(new PropertyValueFactory<>("jmeno"));
        TableColumn<String, Student> prijmeniColumn = new TableColumn<>("Příjmení");
        prijmeniColumn.setCellValueFactory(new PropertyValueFactory<>("prijmeni"));


        tableView.getColumns().addAll(jmenoColumn, prijmeniColumn);
    }

    public void handleSelection() {
        TableView.TableViewSelectionModel<Student> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumns();
        fillTable();
        handleSelection();
    }
}


