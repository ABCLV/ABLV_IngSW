package controller.iscrizione;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.Gara;
import service.ArbitroService;
import service.exception.RicercaEccezione;

import state.Session;
import utils.Alerter;

import java.io.IOException;
import java.util.List;

import db.exception.GaraEccezione;

public class IscrizioneGareArbitroController {

    @FXML private ComboBox<Gara> garaComboBox;
    @FXML private VBox dettagliGaraSection;
    @FXML private Button btnConferma;

    @FXML private Label lblCodiceGara;
    @FXML private Label lblData;
    @FXML private Label lblTecnica;
    @FXML private Label lblTipoGara;
    @FXML private Label lblCampoGara;
    @FXML private Label lblStatoGara;

    private final ArbitroService arbitroService = new ArbitroService();

    private ObservableList<Gara> gareDisponibili;

    @FXML
    public void initialize() {
        caricaGareDisponibili();
        setupAutoCompleteGara();
        btnConferma.setDisable(false);
    }

    /* ---------- CARICA GARE SENZA ARBITRO ---------- */
    private void caricaGareDisponibili() {
        try {
        	
            List<Gara> gare = arbitroService.getGarePerArbitro();
            gareDisponibili = FXCollections.observableArrayList(gare);
            garaComboBox.setItems(gareDisponibili);
        } catch (RicercaEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }

    /* ---------- AUTOCOMPLETE ---------- */
    private void setupAutoCompleteGara() {
        garaComboBox.getEditor().textProperty().addListener((obs, oldV, newV) -> {
            if (garaComboBox.getValue() != null &&
                newV.equals(garaComboBox.getValue().toString())) return;

            if (newV == null || newV.isEmpty()) {
                garaComboBox.setItems(gareDisponibili);
                garaComboBox.hide();
            } else {
                ObservableList<Gara> filtrate = FXCollections.observableArrayList();
                String filter = newV.toLowerCase();

                for (Gara g : gareDisponibili) {
                    if (g.toString().toLowerCase().contains(filter)) {
                        filtrate.add(g);
                    }
                }

                garaComboBox.setItems(filtrate);
                if (!filtrate.isEmpty()) garaComboBox.show();
                else garaComboBox.hide();
            }
        });
    }

    /* ---------- SELEZIONE GARA ---------- */
    @FXML
    private void onGaraSelected(ActionEvent event) {
        Gara gara = garaComboBox.getValue();
        if (gara != null) mostraDettagliGara(gara);
        else nascondiDettagli();
    }

    private void mostraDettagliGara(Gara g) {
        lblCodiceGara.setText(g.getCodice());
        lblData.setText(g.getData() != null ? g.getData().toString() : "N/A");
        lblTecnica.setText(g.getTecnica() != null ? g.getTecnica().toString() : "N/A");
        lblTipoGara.setText(g.getTipoGara() != null ? g.getTipoGara().toString() : "N/A");
        lblCampoGara.setText(g.getCampoGara() != null ? g.getCampoGara().getIdCampoGara() : "N/A");
        lblStatoGara.setText(g.getStatoGara() != null ? g.getStatoGara().toString() : "N/A");

        dettagliGaraSection.setVisible(true);
        dettagliGaraSection.setManaged(true);
    }

    private void nascondiDettagli() {
        dettagliGaraSection.setVisible(false);
        dettagliGaraSection.setManaged(false);
    }

    /* ---------- CONFERMA ---------- */
    @FXML
    private void handleConferma(ActionEvent event) {
        Gara gara = garaComboBox.getValue();
        if (gara == null) {
            Alerter.showError("Seleziona una gara prima di confermare.");
            return;
        }

        try {
            String cfArbitro = Session.getUserName();
            arbitroService.assegnaArbitroAGara(gara.getCodice(), cfArbitro);

            Alerter.showSuccess("Iscrizione come arbitro completata!");
            handleBack(event);

        } catch (GaraEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }

    /* ---------- BACK ---------- */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/fxml/arbitroHome.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            Alerter.showError("Errore nel ritorno alla home arbitro");
        }
    }
}
