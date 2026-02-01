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

public class AnnullaIscrizioneArbitroController {

    @FXML private ListView<Gara> listaGare;
    @FXML private VBox dettagliGaraSection;
    @FXML private Button btnDisiscrivi;

    @FXML private Label lblCodiceGara;
    @FXML private Label lblData;
    @FXML private Label lblTecnica;
    @FXML private Label lblTipoGara;
    @FXML private Label lblCampoGara;
    @FXML private Label lblStatoGara;

    private final ArbitroService arbitroService = new ArbitroService();
    private ObservableList<Gara> gareArbitro;

    @FXML
    public void initialize() {
        caricaGareArbitro();
        setupListView();
        nascondiDettagli();
    }

    /* ---------- CARICAMENTO GARE ---------- */
    private void caricaGareArbitro() {
        try {
            String cfArbitro = Session.getUserName();
            List<Gara> gare = arbitroService.getGareDiArbitro(cfArbitro);
            gareArbitro = FXCollections.observableArrayList(gare);
            listaGare.setItems(gareArbitro);
        } catch (RicercaEccezione e) {
            Alerter.showError("Errore nel recupero delle gare: " + e.getMessage());
        }
    }

    /* ---------- LIST VIEW ---------- */
    private void setupListView() {
        listaGare.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Gara gara, boolean empty) {
                super.updateItem(gara, empty);
                if (empty || gara == null) {
                    setText(null);
                } else {
                    setText(gara.getCodice() + " - " +
                            (gara.getData() != null ? gara.getData() : "N/A"));
                }
            }
        });

        listaGare.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldG, newG) -> {
                if (newG != null) mostraDettagliGara(newG);
                else nascondiDettagli();
            }
        );
    }

    /* ---------- DETTAGLI ---------- */
    private void mostraDettagliGara(Gara g) {
        lblCodiceGara.setText(g.getCodice());
        lblData.setText(g.getData() != null ? g.getData().toString() : "N/A");
        lblTecnica.setText(g.getTecnica() != null ? g.getTecnica().toString() : "N/A");
        lblTipoGara.setText(g.getTipoGara() != null ? g.getTipoGara().toString() : "N/A");
        lblCampoGara.setText(
                g.getCampoGara() != null ? g.getCampoGara().getIdCampoGara() : "N/A");
        lblStatoGara.setText(
                g.getStatoGara() != null ? g.getStatoGara().toString() : "N/A");

        dettagliGaraSection.setVisible(true);
        dettagliGaraSection.setManaged(true);
        btnDisiscrivi.setDisable(false);
    }

    private void nascondiDettagli() {
        dettagliGaraSection.setVisible(false);
        dettagliGaraSection.setManaged(false);
        btnDisiscrivi.setDisable(true);
    }

    /* ---------- DISISCRIZIONE ---------- */
    @FXML
    private void handleDisiscrivi(ActionEvent event) {
        Gara gara = listaGare.getSelectionModel().getSelectedItem();
        if (gara == null) {
            Alerter.showError("Seleziona una gara.");
            return;
        }

        try {
            String cfArbitro = Session.getUserName();
            arbitroService.rimuoviArbitroDaGara(gara.getCodice(), cfArbitro);

            Alerter.showSuccess("Disiscrizione completata!");
            gareArbitro.remove(gara);
            nascondiDettagli();

        } catch (GaraEccezione e) {
            Alerter.showError("Errore durante la disiscrizione: " + e.getMessage());
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
            e.printStackTrace();
            Alerter.showError("Errore nel ritorno alla home arbitro");
        }
    }
}
