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


public class DruhyZnamekController implements Initializable {

    @FXML
    TableView tableView;

    DruhyZnamekDAOJDBC druhyZnamekDao = new DruhyZnamekDAOJDBC();
    List<DruhZnamky> druhyZnamek;
    ObservableList<DruhZnamky> items;
    ObservableList<DruhZnamky> selectedItems = null;


    public void fillTable(){
        druhyZnamek = druhyZnamekDao.getAll();
        items = FXCollections.observableList(druhyZnamek);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, DruhZnamky> nazevColumn = new TableColumn<>("Název");
        nazevColumn.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        TableColumn<String, DruhZnamky> datumColumn = new TableColumn<>("Datum");
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        TableColumn<String, DruhZnamky> popisColumn = new TableColumn<>("Popis");
        popisColumn.setCellValueFactory(new PropertyValueFactory<>("popis"));
        TableColumn<String, DruhZnamky> typZnamkyColumn = new TableColumn<>("Typ Známky");
        typZnamkyColumn.setCellValueFactory(new PropertyValueFactory<>("typZnamky"));
        TableColumn<String, DruhZnamky> kurzColumn = new TableColumn<>("Kurz");
        kurzColumn.setCellValueFactory(new PropertyValueFactory<>("kurz"));
        nazevColumn.setEditable(true);

        tableView.getColumns().addAll(nazevColumn,datumColumn,popisColumn,typZnamkyColumn,kurzColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<DruhZnamky> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();
    }

    public void handlePridejButton(){
        Dialog<DruhZnamky> dialog = new Dialog<>();
        dialog.setTitle("Nový druh známky");
        dialog.setWidth(400);
        dialog.setHeight(300);
        DruhZnamkyDialog(dialog);

        final Optional<DruhZnamky> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            DruhZnamky novyDruhZnamky = vysledek.get();
            druhyZnamek.add(novyDruhZnamky);
            druhyZnamekDao.insert(novyDruhZnamky);
        }
        tableView.refresh();

    }

    private void DruhZnamkyDialog(Dialog dialog){
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
        TextField datumTextField = new TextField();
        Label datumLabel = new Label("Datum");
        TextField popisTextField = new TextField();
        Label popisLabel = new Label("Popis");
        TextField typZnamkyTextField = new TextField();
        Label typZnamkyLabel = new Label("Typ Známky");
        TextField kurzTextField = new TextField();
        Label kurzLabel = new Label("Kurz");


        grid.add(nazevLabel, 0, 0);
        grid.add(nazevTextField, 1, 0);
        grid.add(datumLabel, 0, 1);
        grid.add(datumTextField, 1, 1);
        grid.add(popisLabel,0,2);
        grid.add(popisTextField,1,2);
        grid.add(typZnamkyLabel, 0, 3);
        grid.add(typZnamkyTextField, 1, 3);
        grid.add(kurzLabel, 0, 4);
        grid.add(kurzTextField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, DruhZnamky>() {
            @Override
            public DruhZnamky call(ButtonType param) {
                if (param == createButtonType) {
                    DruhZnamky druhZnamky = new DruhZnamky();
                    druhZnamky.setNazev(nazevTextField.getText());
                    druhZnamky.setDatum(datumTextField.getText());
                    druhZnamky.setPopis(popisTextField.getText());
                    druhZnamky.setTypZnamky(typZnamkyTextField.getText());
                    druhZnamky.setKurz(kurzTextField.getText());
                    return druhZnamky;
                }
                return null;
            }
        });
    }

    public void handleSmazButton(){

        DruhZnamky item = (DruhZnamky) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        druhyZnamek.remove(item);
        druhyZnamekDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){
        try {

            DruhZnamky item = (DruhZnamky) tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/UpravaDruhuZnamky.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            UpravaDruhuZnamkyController controller = (UpravaDruhuZnamkyController) loader.getController();
            controller.setDruhZnamky(item);
            controller.setDruhyZnamekScene(tableView.getScene());
            controller.setDruhyZnamekController(this);
            Scene scene = new Scene(root);
            Stage ps = Main.getPrimaryStage();
            ps.setScene(scene);


        }catch (IOException e){e.printStackTrace();}
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

    public void refresh() {
        tableView.refresh();
    }

    public void selectTypyZnamek(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/TypyZnamek.fxml"));
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

    public void selectDruhyZnamek(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/DruhyZnamek.fxml"));
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
