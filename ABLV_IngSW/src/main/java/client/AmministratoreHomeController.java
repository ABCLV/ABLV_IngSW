package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AmministratoreHomeController {

	@FXML
	private Label welcomeLabel;

	@FXML
	private void initialize() {
		welcomeLabel.setText("Benvenuto Amministratore " + Session.userName);
	}

	/* ---------- REGISTRA NUOVO AMMINISTRATORE ---------- */
	@FXML
	private void apriRegistraAmministratore(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/RegistraAmministratore.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = new Stage();
		stage.setTitle("Registra Amministratore");
		stage.setScene(scene);
		stage.show();
	}

	/* ---------- TORNA HOME ---------- */
	@FXML
	private void handleBack(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Home.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Orobic Fishing Race");
	}
}