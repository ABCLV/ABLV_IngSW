package client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegistrazioneController {

	/* ---------- componenti FXML ---------- */
	@FXML
	private ComboBox<String> tipoCombo;
	@FXML
	private GridPane campiGrid;
	@FXML
	private Button backBtn;

	/* ---------- elenco campi per ogni tipo ---------- */
	private final String[][] dati = { { "Pescatore", "CF", "Nome", "Cognome", "Email", "Data di nascita" },
			{ "Arbitro", "CF", "Nome", "Cognome", "Sezione" }, { "Società", "Nome", "Indirizzo", "Città", "CAP" } };

	/* ---------- inizializzazione: riempi combo e mostra campi ---------- */
	@FXML
	private void initialize() {
		for (String[] r : dati)
			tipoCombo.getItems().add(r[0]); // r[0] = nome tipo
		tipoCombo.getSelectionModel().selectFirst();
		tipoCombo.setOnAction(e -> mostraCampi()); // quando cambio tipo
		mostraCampi();
	}

	/* ---------- crea label + textfield in base al tipo scelto ---------- */
	private void mostraCampi() {
		campiGrid.getChildren().clear(); // svuota griglia
		String scelto = tipoCombo.getValue(); // tipo selezionato

		for (String[] r : dati) { // cerco la riga giusta
			if (r[0].equals(scelto)) {
				for (int i = 1; i < r.length; i++) { // salto la colonna 0 (nome tipo)
					campiGrid.add(new Label(r[i] + ":"), 0, i - 1);
					TextField tx = new TextField();
					tx.setId(r[i]); // id = nome campo
					campiGrid.add(tx, 1, i - 1);
				}
				break;
			}
		}
	}

	/* ---------- torna alla home ---------- */
	@FXML
	private void handleBack(ActionEvent event) throws Exception {
		Stage stage = (Stage) backBtn.getScene().getWindow();
		stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/client/Home.fxml"))));
		stage.setTitle("Orobic Fishing Race");
	}

	/* ---------- valida e stampa ---------- */
	@FXML
	private void handleRegistra(ActionEvent event) {
		StringBuilder out = new StringBuilder("Registrazione → Tipo: " + tipoCombo.getValue());

		for (var n : campiGrid.getChildren()) {
			if (n instanceof TextField tx) {
				if (tx.getText().isBlank()) {
					System.out.println("Compilare tutti i campi!");
					return;
				}
			}
		}
		System.out.println("TODO");
	}
}