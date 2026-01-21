package client;

import applicazione.Gara;
import database.Consultazioni;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<Gara, String> dataCol;
    @FXML
    private TableColumn<Gara, String> codiceCol;
    @FXML
    private TableColumn<Gara, String> numProvaCol;
    @FXML
    private TableColumn<Gara, String> tipoGaraCol;
    @FXML
    private TableColumn<Gara, String> tecnicaCol;
    @FXML
    private TableColumn<Gara, String> puntiCol;

    @FXML
    private Button backBtn;

    
    @FXML
    private void initialize() {
        // Codice
        codiceCol.setCellValueFactory(c -> 
            new SimpleStringProperty(c.getValue().getCodice())
        );
        // Numero prova
        numProvaCol.setCellValueFactory(c -> 
            new SimpleStringProperty(String.valueOf(c.getValue().getNumProva()))
        );
        // Data
        dataCol.setCellValueFactory(c -> 
            new SimpleStringProperty(c.getValue().getData() != null ? c.getValue().getData().toString() : "")
        );
        // Tipo gara
        tipoGaraCol.setCellValueFactory(c -> 
            new SimpleStringProperty(c.getValue().getTipoGara() != null ? c.getValue().getTipoGara().toString() : "")
        );
        // Tecnica
        tecnicaCol.setCellValueFactory(c -> 
            new SimpleStringProperty(c.getValue().getTecnica() != null ? c.getValue().getTecnica().toString() : "")
        );
        // Criterio punti
        puntiCol.setCellValueFactory(c -> 
            new SimpleStringProperty(c.getValue().getCriterioPunti() != null ? c.getValue().getCriterioPunti().toString() : "")
        );

        // Carica dati dal DB
        ObservableList<Gara> dati = FXCollections.observableArrayList(Consultazioni.getGare());
        table.setItems(dati);
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
