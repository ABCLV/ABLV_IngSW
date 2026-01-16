package client;

import applicazione.Concorrente;
import database.Consultazioni;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

public class RicercaConcorrentiController {

    @FXML
    private TableView<Concorrente> table;

    @FXML
    private TableColumn<Concorrente, String> cfCol;

    @FXML
    private TableColumn<Concorrente, String> nomeCol;

    @FXML
    private TableColumn<Concorrente, String> cognomeCol;

    @FXML
    private TableColumn<Concorrente, String> emailCol;

    @FXML
    private TableColumn<Concorrente, String> nascitaCol;

    @FXML
    private TableColumn<Concorrente, String> societa;
    
    @FXML
    private Button backBtn;

    
    @FXML
    private void initialize() {
        // Associa le colonne ai getter della classe Concorrente
        cfCol.setCellValueFactory(new PropertyValueFactory<>("cf"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeCol.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nascitaCol.setCellValueFactory(new PropertyValueFactory<>("nascita"));
        societa.setCellValueFactory(new PropertyValueFactory<>("societa"));

        // Carica dati dal DB
        ObservableList<Concorrente> dati = FXCollections.observableArrayList(Consultazioni.getConcorrenti());
        table.setItems(dati);
        
    }
    
    
    private void apriGareConcorrente(Concorrente concorrente) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/client/concorrenteHome.fxml")
            );

            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/UtenteEsternoHome.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ricerche");
    }
}
