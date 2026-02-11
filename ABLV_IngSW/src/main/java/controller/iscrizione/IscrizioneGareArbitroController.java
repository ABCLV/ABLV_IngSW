package controller.iscrizione;

import javafx.util.StringConverter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import service.exception.IscrizioneEccezione;
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
        setupGaraConverter();
        setupAutoCompleteGara();
        btnConferma.setDisable(false);
        nascondiDettagli(); // Inizialmente nascondi dettagli
    }

    /* ---------- CONVERTER ---------- */
    private void setupGaraConverter() {
        garaComboBox.setConverter(new StringConverter<>() {

            @Override
            public String toString(Gara gara) {
                if (gara == null) return "";
                return gara.getCodice() + " - " + (gara.getData() != null ? gara.getData() : "N/A");
            }

            @Override
            public Gara fromString(String string) {
                if (string == null || string.isBlank()) return null;

                for (Gara g : gareDisponibili) {
                    if (string.startsWith(String.valueOf(g.getCodice()))) {
                        return g;
                    }
                }
                return null;
            }

        });
    }

    /* ---------- CARICA GARE SENZA ARBITRO ---------- */
    private void caricaGareDisponibili() {
        try {
            List<Gara> gare = arbitroService.getGarePerArbitro();
            gareDisponibili = FXCollections.observableArrayList(gare);
            garaComboBox.setItems(gareDisponibili);
        } catch (RicercaEccezione e) {
            Alerter.showError("Errore nel recupero delle gare: " + e.getMessage());
        }
    }

    /* ---------- AUTOCOMPLETE ---------- */
    private void setupAutoCompleteGara() {

        // Lista filtrabile basata su TUTTE le gare
        FilteredList<Gara> filteredList =
                new FilteredList<>(gareDisponibili, g -> true);

        garaComboBox.setItems(filteredList);

        StringConverter<Gara> converter = garaComboBox.getConverter();

        garaComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {

            // Se l'utente ha appena selezionato un elemento, non rifiltrare
            Gara selected = garaComboBox.getValue();
            if (selected != null && newValue.equals(converter.toString(selected))) {
                return;
            }

            if (newValue == null || newValue.isBlank()) {
                filteredList.setPredicate(g -> true);
                garaComboBox.hide();
                return;
            }

            String filter = newValue.toLowerCase();

            filteredList.setPredicate(g ->
                converter.toString(g).toLowerCase().contains(filter)
            );

            if (!filteredList.isEmpty()) {
                garaComboBox.show();
            } else {
                garaComboBox.hide();
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
        lblCodiceGara.setText(String.valueOf(g.getCodice()));
        lblData.setText(g.getData() != null ? g.getData().toString() : "N/A");
        lblTecnica.setText(g.getTecnica() != null ? g.getTecnica().toString() : "N/A");
        lblTipoGara.setText(g.getTipoGara() != null ? g.getTipoGara().toString() : "N/A");
        lblCampoGara.setText(String.valueOf(g.getCampoGara() != null ? g.getCampoGara().getIdCampoGara() : "N/A"));
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

        } catch (IscrizioneEccezione e) {
            Alerter.showError("Errore durante l'iscrizione: " + e.getMessage());
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
            e.printStackTrace(); // Log per debug
            Alerter.showError("Errore nel ritorno alla home arbitro");
        }
    }
}
