package client.registazioniELogin;

import applicazione.service.RegistrazioneService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistraAmministratoreController {

	@FXML
	private TextField cfField;
	@FXML
	private TextField nomeField;
	@FXML
	private TextField cognomeField;
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField pwdField;

	@FXML
	private void registra() {
		String cf = cfField.getText().trim();
		String nome = nomeField.getText().trim();
		String cognome = cognomeField.getText().trim();
		String email = emailField.getText().trim();
		String pwd = pwdField.getText();

		if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Compilare tutti i campi!");
			alert.showAndWait();
			return;
		}

		try {
			RegistrazioneService.registraAmministratore(cf, nome, cognome, email, pwd);
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Amministratore registrato!");
			alert.showAndWait();
			chiudi();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Errore: " + e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void chiudi() {
		Stage stage = (Stage) cfField.getScene().getWindow();
		stage.close();
	}
}