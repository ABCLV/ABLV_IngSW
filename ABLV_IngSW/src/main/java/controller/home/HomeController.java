package controller.home;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import service.HomeService;
import service.exception.HomeEccezione;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import utils.Alerter;

public class HomeController {

	@FXML private Button loginButton;
	@FXML private Button exitButton;
	@FXML private Label pescatoriLabel;
	
	private final HomeService homeService = new HomeService();
	
	@FXML
	private void initialize() {
		long totPescatori = -1;
		try {
			totPescatori = homeService.getTotalePescatori();
			if (totPescatori >= 0) {
				pescatoriLabel.setText("La nostra app è già usata da " + totPescatori + " pescatori");
			} else {
				pescatoriLabel.setText("Impossibile leggere il numero dei pescatori");
			}
		} catch(HomeEccezione e) {
			Alerter.showError(e.getMessage());
		}
	}

	@FXML
	private void handleExit(ActionEvent event) {
		// Chiude l'applicazione
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleLogin(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Login.fxml"));
			Scene loginScene = new Scene(loader.load());
			Stage stage = (Stage) loginButton.getScene().getWindow();
			stage.setScene(loginScene);
			stage.setTitle("Login - Orobic Fishing Race");
		} catch(IOException e) {
			Alerter.showError(e.getMessage());
		}
	}

	@FXML
	private void handleRegistra(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Registrazione.fxml"));
			Scene regScene = new Scene(loader.load());
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.setScene(regScene);
			stage.setTitle("Registrazione - Orobic Fishing Race");
		} catch(IOException e) {
			Alerter.showError(e.getMessage());
		}
		
	}
}
