package cz.gjkt.view;

import cz.gjkt.application.Main;
import cz.gjkt.model.*;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class TypyZnamekController implements Initializable {

    @FXML
    TableView tableView;

    TypyZnamekDAOJDBC typyZnamekDao = new TypyZnamekDAOJDBC();
    List<TypZnamky> typyZnamek;
    ObservableList<TypZnamky> items;
    ObservableList<TypZnamky> selectedItems = null;


    public void fillTable(){
        typyZnamek = typyZnamekDao.getAll();
        items = FXCollections.observableList(typyZnamek);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, TypZnamky> nazevColumn = new TableColumn<>("Název");
        nazevColumn.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        TableColumn<String, TypZnamky> popisColumn = new TableColumn<>("Popis");
        popisColumn.setCellValueFactory(new PropertyValueFactory<>("popis"));
        TableColumn<String, TypZnamky> minColumn = new TableColumn<>("Min.");
        minColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        TableColumn<String, TypZnamky> vahaColumn = new TableColumn<>("Váha");
        vahaColumn.setCellValueFactory(new PropertyValueFactory<>("vaha"));
        TableColumn<String, TypZnamky> rocnikColumn = new TableColumn<>("Ročník");
        rocnikColumn.setCellValueFactory(new PropertyValueFactory<>("rocnik"));
        TableColumn<String, TypZnamky> pololetiColumn = new TableColumn<>("Pololetí");
        pololetiColumn.setCellValueFactory(new PropertyValueFactory<>("pololeti"));
        TableColumn<String, TypZnamky> predmetColumn = new TableColumn<>("Předmět");
        predmetColumn.setCellValueFactory(new PropertyValueFactory<>("predmet"));
        nazevColumn.setEditable(true);

        tableView.getColumns().addAll(nazevColumn,popisColumn,minColumn,vahaColumn,rocnikColumn,pololetiColumn,predmetColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<TypZnamky> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();
    }

    public void handlePridejButton(){
        Dialog<TypZnamky> dialog = new Dialog<>();
        dialog.setTitle("Nový typ známky");
        dialog.setWidth(400);
        dialog.setHeight(300);
        typZnamkyDialog(dialog);

        final Optional<TypZnamky> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            TypZnamky novyTypZnamky = vysledek.get();
            typyZnamek.add(novyTypZnamky);
            typyZnamekDao.insert(novyTypZnamky);
        }
        tableView.refresh();

    }

    private void typZnamkyDialog(Dialog dialog){
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
        Label popisLabel = new Label("Popis");
        TextField minTextField = new TextField();
        Label minLabel = new Label("Min.");
        TextField vahaTextField = new TextField();
        Label vahaLabel = new Label("Váha");
        TextField rocnikTextField = new TextField();
        Label rocnikLabel = new Label("Ročník");
        TextField pololetiTextField = new TextField();
        Label pololetiLabel = new Label("Pololetí");
        TextField predmetTextField = new TextField();
        Label predmetLabel = new Label("Předmět");


        grid.add(nazevLabel, 0, 0);
        grid.add(nazevTextField, 1, 0);
        grid.add(popisLabel, 0, 1);
        grid.add(popisTextField, 1, 1);
        grid.add(minLabel,0,2);
        grid.add(minTextField,1,2);
        grid.add(vahaLabel, 0, 3);
        grid.add(vahaTextField, 1, 3);
        grid.add(rocnikLabel, 0, 4);
        grid.add(rocnikTextField, 1, 4);
        grid.add(pololetiLabel, 0, 5 );
        grid.add(pololetiTextField, 1, 5 );
        grid.add(predmetLabel, 0, 6);
        grid.add(predmetTextField, 1, 6);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, TypZnamky>() {
            @Override
            public TypZnamky call(ButtonType param) {
                if (param == createButtonType) {
                    TypZnamky typZnamky = new TypZnamky();
                    typZnamky.setNazev(nazevTextField.getText());
                    typZnamky.setPopis(popisTextField.getText());
                    typZnamky.setMin(minTextField.getText());
                    typZnamky.setVaha(vahaTextField.getText());
                    typZnamky.setRocnik(rocnikTextField.getText());
                    typZnamky.setPololeti(pololetiTextField.getText());
                    typZnamky.setPredmet(predmetTextField.getText());
                    return typZnamky;
                }
                return null;
            }
        });
    }

    public void handleSmazButton(){

        TypZnamky item = (TypZnamky) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        typyZnamek.remove(item);
        typyZnamekDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){

        try {

            TypZnamky item = (TypZnamky) tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/UpravaTypuZnamky.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            UpravaTypuZnamkyController controller = (UpravaTypuZnamkyController) loader.getController();
            controller.setTypZnamky(item);
            controller.setTypyZnamekScene(tableView.getScene());
            controller.setTypyZnamekController(this);
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
