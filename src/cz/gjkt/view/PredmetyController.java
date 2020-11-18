package cz.gjkt.view;

import cz.gjkt.application.Main;
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
import javafx.scene.Group;
import javafx.stage.Stage;
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


public class PredmetyController implements Initializable {

    @FXML
    TableView tableView;

    PredmetyDAOJDBC predmetyDao = new PredmetyDAOJDBC();
    List<Predmet> predmety;
    ObservableList<Predmet> items;
    ObservableList<Predmet> selectedItems = null;


    public void fillTable(){
        predmety = predmetyDao.getAll();
        items = FXCollections.observableList(predmety);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, Predmet> nazevColumn = new TableColumn<>("Název");
        nazevColumn.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        TableColumn<String, Predmet> popisColumn = new TableColumn<>("Popis");
        popisColumn.setCellValueFactory(new PropertyValueFactory<>("popis"));
        TableColumn<String, Predmet> rocnikColumn = new TableColumn<>("Ročník");
        rocnikColumn.setCellValueFactory(new PropertyValueFactory<>("rocnik"));
        TableColumn<String, Predmet> zkratkaColumn = new TableColumn<>("Zkratka");
        zkratkaColumn.setCellValueFactory(new PropertyValueFactory<>("zkratka"));
        zkratkaColumn.setEditable(true);

        tableView.getColumns().addAll(nazevColumn, popisColumn, rocnikColumn, zkratkaColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<Predmet> selectionModel = tableView.getSelectionModel();
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
        Dialog<Predmet> dialog = new Dialog<>();
        dialog.setTitle("Nový předmět");
        dialog.setWidth(400);
        dialog.setHeight(300);
        predmetDialog(dialog);

        final Optional<Predmet> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            Predmet novyPredmet = vysledek.get();
            predmety.add(novyPredmet);
            predmetyDao.insert(novyPredmet);
        }
        tableView.refresh();

    }

    private void predmetDialog(Dialog dialog){
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
        TextField popisTextField = new TextField();
        Label popisLabel = new Label ("Popis");
        TextField rocnikTextField = new TextField();
        Label rocnikLabel = new Label("Ročník");
        TextField zkratkaTextField = new TextField();
        Label zkratkaLabel = new Label("Zkratka");

        grid.add(nazevLabel, 0, 0);
        grid.add(nazevTextField, 1, 0);
        grid.add(popisLabel, 0, 1);
        grid.add(popisTextField,1, 1);
        grid.add(rocnikLabel, 0, 2);
        grid.add(rocnikTextField, 1, 2);
        grid.add(zkratkaLabel,0,3);
        grid.add(zkratkaTextField,1,3);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, Predmet>() {
            @Override
            public Predmet call(ButtonType param) {
                if (param == createButtonType) {
                    Predmet predmet = new Predmet();
                    predmet.setNazev(nazevTextField.getText());
                    predmet.setPopis(popisTextField.getText());
                    predmet.setZkratka(zkratkaTextField.getText());
                    return predmet;
                }
                return null;
            }
        });
    }

    public void handleSmazButton(){

        Predmet item = (Predmet) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        predmety.remove(item);
        predmetyDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){
        try {

        Predmet item = (Predmet) tableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/Predmet.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        PredmetController controller = (PredmetController) loader.getController();
        controller.setPredmet(item);
        /*controller.setNazev(item.getNazev());
        controller.setZkratka(item.getZkratka());*/
        controller.setPredmetyScene(tableView.getScene());
        controller.setPredmetyController(this);
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
}
