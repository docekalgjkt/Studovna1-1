package cz.gjkt.view;

import cz.gjkt.application.Main;
import cz.gjkt.model.Kurz;
import cz.gjkt.model.KurzyDAOJDBC;
import cz.gjkt.model.Predmet;
import cz.gjkt.model.PredmetyDAOJDBC;
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
import javafx.util.Callback;

import javax.swing.text.LabelView;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class KurzyController implements Initializable {

    @FXML
    TableView tableView;

    KurzyDAOJDBC kurzyDao = new KurzyDAOJDBC();
    List<Kurz> kurzy;
    ObservableList<Kurz> items;
    ObservableList<Kurz> selectedItems = null;


    public void fillTable(){
        kurzy = kurzyDao.getAll();
        items = FXCollections.observableList(kurzy);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, Kurz> nazevColumn = new TableColumn<>("Název");
        nazevColumn.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        TableColumn<String, Kurz> skolniRokColumn = new TableColumn<>("Školní Rok");
        skolniRokColumn.setCellValueFactory(new PropertyValueFactory<>("skolniRok"));
        TableColumn<String, Kurz> predmetColumn = new TableColumn<>("Předmět");
        predmetColumn.setCellValueFactory(new PropertyValueFactory<>("predmet"));
        nazevColumn.setEditable(true);

        tableView.getColumns().addAll(nazevColumn,skolniRokColumn,predmetColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<Kurz> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();

        /*selectedItems.addListener(new ListChangeListener<Predmet>() {
            @Override
            public void onChanged(Change<? extends Predmet> change) {
                System.out.println("Selection changed: " + change.getList());
                System.out.println("Selected: " + selectedItems.get(0));
            }
        });*/
    }

    public void handlePridejButton(){
        Dialog<Kurz> dialog = new Dialog<>();
        dialog.setTitle("Nový kurz");
        dialog.setWidth(400);
        dialog.setHeight(300);
        kurzDialog(dialog);

        final Optional<Kurz> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            Kurz novyKurz = vysledek.get();
            kurzy.add(novyKurz);
            kurzyDao.insert(novyKurz);
        }
        tableView.refresh();

    }

    private void kurzDialog(Dialog dialog){
        // Vytvoření "potvrzovacího" tlačítka pro potvrzení dialogu
        ButtonType createButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        // Nastavení tlačítek dialogu
        dialog.getDialogPane().getButtonTypes().setAll(createButtonType, ButtonType.CANCEL);

        // Mřížka
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Komponenty
        TextField nazevTextField = new TextField();
        Label nazevLabel = new Label("Název");
        TextField skolniRokTextField = new TextField();
        Label skolniRokLabel = new Label("Školní Rok");
        TextField predmetTextField = new TextField();
        Label predmetLabel = new Label("Předmět");

        grid.add(nazevLabel, 0, 0);
        grid.add(nazevTextField, 1, 0);
        grid.add(skolniRokLabel, 0, 1);
        grid.add(skolniRokTextField, 1, 1);
        grid.add(predmetLabel,0,2);
        grid.add(predmetTextField,1,2);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, Kurz>() {
            @Override
            public Kurz call(ButtonType param) {
                if (param == createButtonType) {
                    Kurz kurz = new Kurz();
                    kurz.setNazev(nazevTextField.getText());
                    kurz.setSkolniRok(skolniRokTextField.getText());
                    kurz.setPredmet(predmetTextField.getText());
                    return kurz;
                }
                return null;
            }
        });
    }

    public void handleSmazButton(){

        Kurz item = (Kurz) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        kurzy.remove(item);
        kurzyDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){

    }

    public void handleZpetButton(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/main.fxml"));
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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumns();
        fillTable();
        handleSelection();
    }

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
