package client.registazioniELogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import applicazione.service.RegistrazioneService;

public class RegistrazioneController {

	/* ---------- componenti FXML ---------- */
	@FXML
	private ComboBox<String> tipoCombo;
	@FXML
	private GridPane campiGrid;
	@FXML
	private Button backBtn;
	@FXML
	private Button regBtn;
	@FXML
	private Label errLabel; // label errore

	/* ---------- elenco campi per ogni tipo ---------- */
	private final String[][] dati = { { "Arbitro", "CF", "Nome", "Cognome", "Sezione", "Password" },
			{ "Società", "Nome", "Indirizzo", "Città", "CAP", "Email", "Password" } };

	/* ---------- inizializzazione ---------- */
	@FXML
	private void initialize() {
		for (String[] r : dati)
			tipoCombo.getItems().add(r[0]);
		tipoCombo.getSelectionModel().selectFirst();
		tipoCombo.setOnAction(e -> mostraCampi());
		mostraCampi();
		errLabel.setVisible(false); // nascondi errore
	}

	/* ---------- crea campi in base al tipo ---------- */
	private void mostraCampi() {
		errLabel.setVisible(false); // reset errore
		campiGrid.getChildren().clear();
		String scelto = tipoCombo.getValue();
		for (String[] r : dati) {
			if (r[0].equals(scelto)) {
				for (int i = 1; i < r.length; i++) {
					campiGrid.add(new Label(r[i] + ":"), 0, i - 1);
					TextField tx = new TextField();
					tx.setId(r[i]);
					campiGrid.add(tx, 1, i - 1);
				}
				break;
			}
		}
	}

	/* ---------- registrazione ---------- */
	@FXML
	private void handleRegistra(ActionEvent event) {
		errLabel.setVisible(false); // reset errore
		String tipo = tipoCombo.getValue();
		String[] v = new String[6];
		int i = 0;
		for (var n : campiGrid.getChildren()) {
			if (n instanceof TextField tx) {
				if (tx.getText().isBlank()) {
					errLabel.setText("Compilare tutti i campi!");
					errLabel.setVisible(true);
					return;
				}
				v[i++] = tx.getText();
			}
		}

		try {
			if ("Arbitro".equals(tipo)) {
				if (RegistrazioneService.esisteArbitro(v[0])) {
					errLabel.setText("Arbitro con questo CF già presente.");
					errLabel.setVisible(true);
					return;
				}
				RegistrazioneService.registraArbitro(v[0], v[1], v[2], v[3], v[4]);
			} else if ("Società".equals(tipo)) {
				if (RegistrazioneService.esisteSocieta(v[0])) {
					errLabel.setText("Società con questo nome già presente.");
					errLabel.setVisible(true);
					return;
				}
				RegistrazioneService.registraSocieta(v[0], v[1], v[2], v[3], v[4], v[5]);
			}
			errLabel.setTextFill(javafx.scene.paint.Color.GREEN);
			errLabel.setText("Registrazione avvenuta!");
			errLabel.setVisible(true);

		} catch (Exception e) {
			errLabel.setTextFill(javafx.scene.paint.Color.RED);
			errLabel.setText("Errore: " + e.getMessage());
			errLabel.setVisible(true);
		}
	}

	/* ---------- torna alla home ---------- */
	@FXML
	private void handleBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) backBtn.getScene().getWindow();
		stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/client/Home.fxml"))));
		stage.setTitle("Orobic Fishing Race");
	}
}