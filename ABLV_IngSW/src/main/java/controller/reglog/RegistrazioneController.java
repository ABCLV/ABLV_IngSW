package controller.reglog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import service.RegistrazioneService;
import service.exception.RegistrazioneEccezione;
import utils.Alerter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrazioneController {

	/* ---------- componenti FXML ---------- */
	@FXML private ComboBox<String> tipoCombo;
	@FXML private GridPane campiGrid;
	@FXML private Button backBtn;
	@FXML private Button regBtn;
	@FXML private Label errLabel;

	/* ---------- elenco campi per ogni tipo ---------- */
	private final String[][] dati = {
			{ "Arbitro", "CF", "Nome", "Cognome", "Sezione", "Password" },
			{ "Societa", "Nome", "Indirizzo", "Città", "CAP", "Email", "Password" }
	};
	
	private final RegistrazioneService registrazioneService = new RegistrazioneService();
	
	/* ---------- lista per tenere traccia dei campi creati ---------- */
	private final List<TextField> campiDinamici = new ArrayList<>();

	/* ---------- inizializzazione ---------- */
	@FXML
	private void initialize() {
		for(String[] r : dati) {
			tipoCombo.getItems().add(r[0]);
		}
		tipoCombo.getSelectionModel().selectFirst();
		tipoCombo.setOnAction(e -> mostraCampi());
		mostraCampi();
		errLabel.setVisible(false);
	}

	/* ---------- crea campi in base al tipo ---------- */
	private void mostraCampi() {
		errLabel.setVisible(false);
		campiGrid.getChildren().clear();
		campiDinamici.clear(); // svuota la lista
		
		String scelto = tipoCombo.getValue();
		for (String[] r : dati) {
			if (r[0].equals(scelto)) {
				for (int i = 1; i < r.length; i++) {
					campiGrid.add(new Label(r[i] + ":"), 0, i - 1);
					
					TextField tx;
					// Se è Password usa PasswordField (mostra asterischi)
					if (r[i].equalsIgnoreCase("Password")) {
						tx = new PasswordField();
					} else {
						tx = new TextField();
					}
					
					tx.setId(r[i]);
					tx.setPrefWidth(200);
					campiDinamici.add(tx);
					campiGrid.add(tx, 1, i - 1);
				}
				break;
			}
		}
	}

	/* ---------- registrazione ---------- */
	@FXML
	private void handleRegistra(ActionEvent event) {
		errLabel.setVisible(false);
		String tipo = tipoCombo.getValue();
		
		// Raccoglie i valori in ordine
		String[] v = new String[campiDinamici.size()];
		int i = 0;
		for (TextField tx : campiDinamici) {
			if (tx.getText().isBlank()) {
				errLabel.setTextFill(Color.RED);
				errLabel.setText("Compilare tutti i campi!");
				errLabel.setVisible(true);
				return;
			}
			v[i++] = tx.getText();
		}

		// Validazioni specifiche
		if (tipo.equals("Arbitro")) {
			String cf = v[0];
			String nome = v[1];
			String cognome = v[2];
			
			// Regex CF italiano: 6 lettere, 2 numeri, 1 lettera, 2 numeri, 1 lettera, 3 numeri, 1 lettera (case insensitive)
			if (!cf.matches("^[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]$")) {
				errLabel.setTextFill(Color.RED);
				errLabel.setText("Codice Fiscale non valido! Deve essere 6 lettere, 2 numeri, 1 lettera, 2 numeri, 1 lettera, 3 numeri, 1 lettera");
				errLabel.setVisible(true);
				return;
			}
			
			// Nome e Cognome: solo lettere e spazi (no numeri)
			if (!nome.matches("^[A-Za-z\\s]+$") || !cognome.matches("^[A-Za-z\\s]+$")) {
				errLabel.setTextFill(Color.RED);
				errLabel.setText("Nome e Cognome possono contenere solo lettere!");
				errLabel.setVisible(true);
				return;
			}
			
		} else if (tipo.equals("Societa")) {
			String cap = v[3];  // CAP è il 4° campo (indice 3)
			String email = v[4]; // Email è il 5° campo (indice 4)
			
			// CAP: esattamente 5 numeri
			if (!cap.matches("^[0-9]{5}$")) {
				errLabel.setTextFill(Color.RED);
				errLabel.setText("Il CAP deve essere composto da esattamente 5 numeri!");
				errLabel.setVisible(true);
				return;
			}
			
			// Email: regex semplice ma efficace
			if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				errLabel.setTextFill(Color.RED);
				errLabel.setText("Formato email non valido!");
				errLabel.setVisible(true);
				return;
			}
		}

		// Procedura di registrazione
		try {
			if (tipo.equals("Arbitro")) {
				this.registrazioneService.esisteArbitro(v[0]);
				this.registrazioneService.registraArbitro(v[0], v[1], v[2], v[3], v[4]);
			} else if (tipo.equals("Societa")) {
				this.registrazioneService.esisteSocieta(v[0]);
				registrazioneService.registraSocieta(v[0], v[1], v[2], v[3], v[4], v[5]);
			}
			errLabel.setTextFill(Color.GREEN);
			errLabel.setText("Registrazione avvenuta con successo!");
			errLabel.setVisible(true);

		} catch (RegistrazioneEccezione e) {
			errLabel.setTextFill(Color.RED);
			errLabel.setText("Errore: " + e.getMessage());
			errLabel.setVisible(true);
		}
	}

	/* ---------- torna alla home ---------- */
	@FXML
	private void handleBack(ActionEvent event) {
		try {
			Stage stage = (Stage) backBtn.getScene().getWindow();
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/fxml/Home.fxml"))));
			stage.setTitle("Orobic Fishing Race");
		} catch(IOException e) {
			Alerter.showError(e.getMessage());
		}
	}
}