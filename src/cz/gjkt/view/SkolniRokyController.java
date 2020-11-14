package cz.gjkt.view;

import cz.gjkt.application.Main;
import cz.gjkt.model.*;
import cz.gjkt.model.SkolniRok;
import cz.gjkt.model.SkolniRokyDAOJDBC;
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


public class SkolniRokyController implements Initializable {

    @FXML
    TableView tableView;

    SkolniRokyDAOJDBC skolniRokyDao = new SkolniRokyDAOJDBC();
    List<SkolniRok> skolniRoky;
    ObservableList<SkolniRok> items;
    ObservableList<SkolniRok> selectedItems = null;


    public void fillTable(){
        skolniRoky = skolniRokyDao.getAll();
        items = FXCollections.observableList(skolniRoky);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, SkolniRok> nazevColumn = new TableColumn<>("Název");
        nazevColumn.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        TableColumn<String, SkolniRok> zacatekColumn = new TableColumn<>("Začátek");
        zacatekColumn.setCellValueFactory(new PropertyValueFactory<>("zacatek"));
        TableColumn<String, SkolniRok> konecColumn = new TableColumn<>("Konec");
        konecColumn.setCellValueFactory(new PropertyValueFactory<>("konec"));
        nazevColumn.setEditable(true);
        zacatekColumn.setEditable(true);
        konecColumn.setEditable(true);

        tableView.getColumns().addAll(nazevColumn,zacatekColumn,konecColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<SkolniRok> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();

        /*selectedItems.addListener(new ListChangeListener<SkolniRok>() {
            @Override
            public void onChanged(Change<? extends SkolniRok> change) {
                System.out.println("Selection changed: " + change.getList());
                System.out.println("Selected: " + selectedItems.get(0));
            }
        });*/
    }

    public void handlePridejButton(){
        Dialog<SkolniRok> dialog = new Dialog<>();
        dialog.setTitle("Nový školní rok");
        dialog.setWidth(400);
        dialog.setHeight(300);
        skolniRokDialog(dialog);

        final Optional<SkolniRok> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            SkolniRok novySkolniRok = vysledek.get();
            skolniRoky.add(novySkolniRok);
            skolniRokyDao.insert(novySkolniRok);
        }
        tableView.refresh();

    }

    private void skolniRokDialog(Dialog dialog){
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
        TextField zacatekTextField = new TextField();
        Label zacatekLabel = new Label("Začátek");
        TextField konecTextField = new TextField();
        Label konecLabel = new Label("Konec");

        grid.add(nazevLabel, 0, 0);
        grid.add(nazevTextField, 1, 0);
        grid.add(zacatekLabel, 0, 1);
        grid.add(zacatekTextField, 1, 1);
        grid.add(konecLabel,0,2);
        grid.add(konecTextField,1,2);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, SkolniRok>() {
            @Override
            public SkolniRok call(ButtonType param) {
                if (param == createButtonType) {
                    SkolniRok skolniRok = new SkolniRok();
                    skolniRok.setNazev(nazevTextField.getText());
                    skolniRok.setZacatek(zacatekTextField.getText());
                    skolniRok.setKonec(konecTextField.getText());
                    return skolniRok;
                }
                return null;
            }
        });
    }

    public void handleSmazButton(){

        SkolniRok item = (SkolniRok) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        skolniRoky.remove(item);
        skolniRokyDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){
        try {

            SkolniRok item = (SkolniRok) tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/SkolniRok.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            SkolniRokController controller = (SkolniRokController) loader.getController();
            controller.setSkolniRok(item);
            controller.setSkolniRokyScene(tableView.getScene());
            controller.setSkolniRokController(this);
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
