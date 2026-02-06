package controller.ricerche;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.CampoGara;
import model.Gara;
import model.Settore;
import service.RicercaService;
import utils.Alerter;

public class RicercaCampoGaraController {

    @FXML private ComboBox<CampoGara> campoCombo;
    @FXML private TableView<Settore> settoriTable;
    @FXML private TableColumn<Settore, String> idSettoreCol;
    @FXML private TableColumn<Settore, String> nomeSettoreCol;
    @FXML private TableView<Gara> gareTable;
    @FXML private TableColumn<Gara, String> idGaraCol;
    @FXML private TableColumn<Gara, String> nomeGaraCol;
    @FXML private Button backBtn;

    private final RicercaService ricercaService = new RicercaService();
    
    @FXML
    private void initialize() {

        ObservableList<CampoGara> campi =
                FXCollections.observableArrayList(this.ricercaService.getCampiGara());
        campoCombo.setItems(campi);

        // --- Colonne Settori ---
        idSettoreCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdSettore())
        );

        nomeSettoreCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescrizione())
        );

        // --- Colonne Gare ---
        idGaraCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCodice())
        );

        nomeGaraCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getTipoGara() != null
                                ? cellData.getValue().getTipoGara().toString()
                                : ""
                )
        );

        campoCombo.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                caricaSettori(newValue);
                caricaGare(newValue);
            }
        });
    }

    private void caricaSettori(CampoGara c) {
        ObservableList<Settore> settori =
                FXCollections.observableArrayList(this.ricercaService.esploraSettori(c));
        settoriTable.setItems(settori);
    }

    private void caricaGare(CampoGara c) {
        ObservableList<Gara> gare =
                FXCollections.observableArrayList(this.ricercaService.esploraGare(c));
        gareTable.setItems(gare);
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
