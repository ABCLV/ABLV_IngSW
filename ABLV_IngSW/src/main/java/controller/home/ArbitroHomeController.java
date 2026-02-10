package controller.home;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import utils.Alerter;

public class ArbitroHomeController {

    /* ---------- INIZIO GARA ---------- */
    @FXML
    private void handleInizioGara(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/fxml/InizioGaraArbitro.fxml")
            );
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Inizio Gara");

        } catch (IOException e) {
            Alerter.showError("Errore apertura inizio gara");
        }
    }

    /* ---------- ISCRIZIONE A NUOVE GARE ---------- */
    @FXML
    private void handleIscrizioneGare(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/fxml/IscrizioneGareArbitro.fxml")
            );
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Iscrizione Gare");

        } catch (IOException e) {
            Alerter.showError("Errore apertura iscrizione gare");
        }
    }

    /* ---------- VISUALIZZA GARE ISCRITTE ---------- */
    @FXML
    private void handleVisualizzaGare(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/fxml/GareIscritteArbitro.fxml")
            );
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Gare Iscritte");

        } catch (IOException e) {
            Alerter.showError("Errore apertura elenco gare");
        }
    }

    /* ---------- RINVIO GARA ---------- */
    @FXML
    private void handleRinvioGara(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/fxml/RinvioGara.fxml")
            );
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Rinvio Gara");

        } catch (IOException e) {
            Alerter.showError("Errore apertura rinvio gara");
        }
    }
    
    @FXML
	private void handleModificaDati(ActionEvent event) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/ModificaDati.fxml"));
	        Parent root = loader.load();
	        
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	        Alerter.showError("Errore nel caricamento della pagina di modifica dei dati");
	    }
	}

    /* ---------- TORNA ALLA HOME ---------- */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/fxml/Home.fxml")
            );
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Orobic Fishing Race");

        } catch (IOException e) {
            Alerter.showError("Errore ritorno alla home");
        }
    }
}
