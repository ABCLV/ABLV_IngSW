package controller.classifiche;

import java.io.IOException;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Gara;
import service.PunteggioService;
import utils.Alerter;
import model.ClassificaRiga; // ipotetico DTO per la tabella

public class ClassificaController {

    // Info gara
    @FXML private Label idLbl;
    @FXML private Label numProvaLbl;
    @FXML private Label tecnicaLbl;
    @FXML private Label criterioPuntiLbl;
    @FXML private Label tipoGaraLbl;
    @FXML private Label dataLbl;
    @FXML private Label campoGaraLbl;
    @FXML private Button backBtn;

    // Tabella
    @FXML private TableView<ClassificaRiga> classificaTable;
    @FXML private TableColumn<ClassificaRiga, Integer> posizioneCol;
    @FXML private TableColumn<ClassificaRiga, String> cfCol;
    @FXML private TableColumn<ClassificaRiga, String> societaCol;
    @FXML private TableColumn<ClassificaRiga, String> sponsorCol;
    @FXML private TableColumn<ClassificaRiga, Double> penalitaCol;
    @FXML private TableColumn<ClassificaRiga, Double> piazzamentoCol;
    @FXML private TableColumn<ClassificaRiga, String> turniCol;

    private Gara gara;
    private PunteggioService punteggioService = new PunteggioService();

    @FXML
    private void initialize() {
        posizioneCol.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getPosizione()).asObject());

        cfCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCfConcorrente()));

        societaCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getSocieta()));

        sponsorCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getSponsor()));

        penalitaCol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getPenalita()).asObject());

        piazzamentoCol.setCellValueFactory(c ->
        	new SimpleDoubleProperty(c.getValue().getPiazzamento()).asObject());

        turniCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getPiazzamentiTurni()));
    }

    public void setGara(Gara gara) {
        this.gara = gara;
        caricaInfoGara();
        caricaClassifica();
    }

    private void caricaInfoGara() {
        idLbl.setText(String.valueOf(gara.getCodice()));
        numProvaLbl.setText(String.valueOf(gara.getNumProva()));
        tecnicaLbl.setText(String.valueOf(gara.getTecnica()));
        criterioPuntiLbl.setText(String.valueOf(gara.getCriterioPunti()));
        tipoGaraLbl.setText(String.valueOf(gara.getTipoGara()));
        dataLbl.setText(gara.getData() != null ? gara.getData().toString() : "");
        campoGaraLbl.setText(String.valueOf(gara.getCampoGara()));
    }

    private void caricaClassifica() {
        // Qui poi userai il service vero
    	
        ObservableList<ClassificaRiga> dati = punteggioService.calcolaClassifica(this.gara.getCodice());
                

        classificaTable.setItems(dati);
    }
    
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/fxml/RicercaGare.fxml")
            );
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Ricerca Gare");
        } catch (IOException e) {
            Alerter.showError(e.getMessage());
        }
    }

}


