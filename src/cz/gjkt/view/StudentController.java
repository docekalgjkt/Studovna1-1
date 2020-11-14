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


public class StudentController implements Initializable {

    @FXML
    TableView tableView;

    StudentiDAOJDBC2 studentiDao = new StudentiDAOJDBC2();
    List<Student> studenti;
    ObservableList<Student> items;
    ObservableList<Student> selectedItems = null;


    public void fillTable(){
        studenti = studentiDao.getAll();
        items = FXCollections.observableList(studenti);
        tableView.setItems(items);
    }

    public void initColumns() {

        TableColumn<String, Student> jmenoColumn = new TableColumn<>("Jméno");
        jmenoColumn.setCellValueFactory(new PropertyValueFactory<>("jmeno"));
        TableColumn<String, Student> prijmeniColumn = new TableColumn<>("Příjmení");
        prijmeniColumn.setCellValueFactory(new PropertyValueFactory<>("prijmeni"));
        TableColumn<String, Student> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<String, Student> rokNastupuColumn = new TableColumn<>("Rok Nástupu");
        rokNastupuColumn.setCellValueFactory(new PropertyValueFactory<>("rokNastupu"));
        jmenoColumn.setEditable(true);
        prijmeniColumn.setEditable(true);

        tableView.getColumns().addAll(jmenoColumn, prijmeniColumn, emailColumn, rokNastupuColumn);
    }

    public void handleSelection(){
        TableView.TableViewSelectionModel<Student> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        selectedItems = selectionModel.getSelectedItems();

    }

    public void handlePridejButton(){
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Nový Student");
        dialog.setWidth(400);
        dialog.setHeight(300);
        studentDialog(dialog);

        final Optional<Student> vysledek = dialog.showAndWait();
        if(vysledek.isPresent()){
            Student novyStudent = vysledek.get();
            studenti.add(novyStudent);
            studentiDao.insert(novyStudent);
        }
        tableView.refresh();

    }

    private void studentDialog(Dialog dialog){
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
        TextField jmenoTextField = new TextField();
        Label jmenoLabel = new Label("Jméno");
        TextField prijmeniTextField = new TextField();
        Label prijmeniLabel = new Label("Příjmení");
        TextField emailTextField = new TextField();
        Label emailLabel = new Label ("Email");
        TextField rokNastupuTextField = new TextField();
        Label rokNastupuLabel = new Label("Rok nástupu");

        grid.add(jmenoLabel, 0, 0);
        grid.add(jmenoTextField, 1, 0);
        grid.add(prijmeniLabel, 0, 1);
        grid.add(prijmeniTextField,1,1);
        grid.add(emailLabel,0,2);
        grid.add(emailTextField,1,2);
        grid.add(rokNastupuLabel,0,3);
        grid.add(rokNastupuTextField,1,3);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, Student>() {
            @Override
            public Student call(ButtonType param) {
                if (param == createButtonType) {
                    Student student = new Student();
                    student.setJmeno(jmenoTextField.getText());
                    student.setPrijmeni(prijmeniTextField.getText());
                    student.setEmail(emailTextField.getText());
                    student.setRokNastupu(rokNastupuTextField.getText());
                    return student;
                }
                return null;
            }
        });
    }

    public void handleSmazButton(){

        Student item = (Student) tableView.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + item);
        studenti.remove(item);
        studentiDao.delete(item);
        tableView.refresh();
    }

    public void handleUpravButton(){
        try {

            Student item = (Student) tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/UpravaStudenta.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            UpravaStudentaController controller = (UpravaStudentaController) loader.getController();
            controller.setStudent(item);
            controller.setStudentiScene(tableView.getScene());
            controller.setStudentController(this);
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
