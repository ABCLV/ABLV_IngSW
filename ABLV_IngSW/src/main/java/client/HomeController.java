package client;

import java.io.IOException;

import applicazione.service.Statistiche;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class HomeController {

	@FXML
	private Button loginButton;

	@FXML
	private Button exitButton;

	@FXML
	private Label pescatoriLabel; // match con fx:id

	@FXML
	private void initialize() { // viene chiamato automaticamente
		long tot = Statistiche.getTotalePescatori();
		if (tot >= 0)
			pescatoriLabel.setText("La nostra app è già usata da " + tot + " pescatori");
		else
			pescatoriLabel.setText("Impossibile leggere il numero dei pescatori");
	}

	@FXML
	private void handleExit(ActionEvent event) {
		// Chiude l'applicazione
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleLogin(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Login.fxml"));
		Scene loginScene = new Scene(loader.load());
		Stage stage = (Stage) loginButton.getScene().getWindow();
		stage.setScene(loginScene);
		stage.setTitle("Login - Orobic Fishing Race");

	}

	@FXML
	private void handleRegistra(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Registrazione.fxml"));
		Scene regScene = new Scene(loader.load());
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setScene(regScene);
		stage.setTitle("Registrazione - Orobic Fishing Race");
	}

}
