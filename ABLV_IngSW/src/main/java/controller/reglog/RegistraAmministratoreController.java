package controller.reglog;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.RegistrazioneService;
import service.exception.RegistrazioneEccezione;
import utils.Alerter;

public class RegistraAmministratoreController {

	@FXML private TextField cfField;
	@FXML private TextField nomeField;
	@FXML private TextField cognomeField;
	@FXML private TextField emailField;
	@FXML private PasswordField pwdField;
	
	private final RegistrazioneService registrazioneService = new RegistrazioneService();

	@FXML
	private void registra() {
		String cf = cfField.getText().trim();
		String nome = nomeField.getText().trim();
		String cognome = cognomeField.getText().trim();
		String email = emailField.getText().trim();
		String pwd = pwdField.getText();

		if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
			Alerter.showError("Devi compilare tutti i campi obbligatori!");
		}

		try {
			this.registrazioneService.registraAmministratore(cf, nome, cognome, email, pwd);
			chiudi();
		} catch (RegistrazioneEccezione e) {
			Alerter.showError(e.getMessage());
		}
	}

	@FXML
	private void chiudi() {
		Stage stage = (Stage) cfField.getScene().getWindow();
		stage.close();
	}
}