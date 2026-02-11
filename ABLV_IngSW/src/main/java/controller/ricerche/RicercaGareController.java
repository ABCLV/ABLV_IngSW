package controller.ricerche;

import java.io.IOException;

import controller.classifiche.ClassificaController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Gara;
import service.RicercaService;
import service.exception.RicercaEccezione;
import utils.Alerter;
import javafx.event.ActionEvent;

public class RicercaGareController {

    @FXML private TableView<Gara> table;
    @FXML private TableColumn<Gara, String> dataCol;
    @FXML private TableColumn<Gara, Integer> codiceCol;
    @FXML private TableColumn<Gara, String> numProvaCol;
    @FXML private TableColumn<Gara, String> tipoGaraCol;
    @FXML private TableColumn<Gara, String> tecnicaCol;
    @FXML private TableColumn<Gara, String> puntiCol;
    @FXML private Button backBtn;
    
    private final RicercaService ricercaService = new RicercaService();
    
    @FXML
    private void initialize() {
    	try {
    		// Codice
            codiceCol.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleIntegerProperty(
                    cellData.getValue().getCodice()
                ).asObject());
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
            ObservableList<Gara> dati = FXCollections.observableArrayList(this.ricercaService.getGare());
            table.setItems(dati);
            
            
            table.setRowFactory(tv -> {
                TableRow<Gara> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        Gara garaSelezionata = row.getItem();
                        apriClassifica(garaSelezionata);
                    }
                });
                return row;
            });
    	} catch(RicercaEccezione e) {
    		Alerter.showError(e.getMessage());
    	}
        
    }
    
    
    private void apriClassifica(Gara gara) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/fxml/Classifica.fxml")
            );
            Scene scene = new Scene(loader.load());

            // Se vuoi passare la gara al controller della classifica
            ClassificaController controller = loader.getController();
            controller.setGara(gara);

            Stage stage = (Stage) table.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Classifica");
        } catch (IOException e) {
            Alerter.showError(e.getMessage());
        }
    }



    @FXML
    private void handleBack(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/UtenteEsternoHome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Ricerche");
    	} catch(IOException e) {
    		Alerter.showError(e.getMessage());
    	}
        
    }
}
