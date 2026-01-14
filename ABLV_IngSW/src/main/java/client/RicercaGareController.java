package client;

import applicazione.Gara;
import database.Consultazioni;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class RicercaGareController {

    @FXML
    private TableView<Gara> table;

    @FXML
    private TableColumn<Gara, String> idCol;

    @FXML
    private TableColumn<Gara, String> nomeCol;

    @FXML
    private TableColumn<Gara, String> dataCol;

    @FXML
    private Button backBtn;

    /*
    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data")); // assumiamo campo "data" tipo String

        ObservableList<Gara> dati;
        table.setItems(dati);
    }*/

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Ricerche.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ricerche");
    }
}
