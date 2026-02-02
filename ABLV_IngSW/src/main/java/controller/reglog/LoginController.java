package controller.reglog;

import java.io.IOException;

import controller.home.AmministratoreHomeController;
import controller.home.ArbitroHomeController;
import controller.home.ConcorrenteHomeController;
import controller.home.SocietaHomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.LoginService;
import state.Session;
import utils.Alerter;

public class LoginController {

	@FXML private ComboBox<String> tipoCombo;
	@FXML private TextField idField;
	@FXML private PasswordField passField;
	@FXML private Label idLabel;
	@FXML private Label errLabel;
	@FXML private Button backBtn;
	@FXML private Button guestBtn;

	private final LoginService loginService = new LoginService();
	
	/* ---------- inizializza ---------- */
	@FXML
	private void initialize() {
		tipoCombo.getItems().addAll("Concorrente", "Societa", "Amministratore", "Arbitro");
		tipoCombo.getSelectionModel().selectFirst();
		mostraCampi();
	}

	/* ---------- mostra/nasconde campo identificativo ---------- */
	@FXML
	private void mostraCampi() {
		String tipo = tipoCombo.getValue();
		if (tipo == null)
			return;

		idField.setVisible(true);
		idLabel.setVisible(true);
		switch (tipo) {
		case "Concorrente" -> idLabel.setText("Codice Fiscale:");
		case "Societa" -> idLabel.setText("Nome:");
		case "Amministratore" -> idLabel.setText("Codice Fiscale:");
		case "Arbitro" -> idLabel.setText("Codice Fiscale:");
		}
		idField.clear();
		passField.clear();
		errLabel.setVisible(false);
	}

	/* ---------- LOGIN ---------- */
	@FXML
	private void handleLogin(ActionEvent event) {
		try {
			String tipo = tipoCombo.getValue();
			String id = idField.getText().trim();
			String pwd = passField.getText();

			if (id.isEmpty() || pwd.isEmpty()) {
				errLabel.setVisible(false);
				return;
			}
			
			this.loginService.login(tipo, id, pwd);

			/* --- salva sessione --- */
			Session.setUserName(id);
			Session.setUserType(tipo);
			
			/* --- carica pagina dedicata --- */
			String fxml = "/view/fxml/" + tipo + "Home.fxml";
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			Scene scene = new Scene(loader.load());
			if (tipo.equals("Amministratore")) {
			    AmministratoreHomeController ctrl = loader.getController();
			}
			else if (tipo.equals("Concorrente")) {
			    ConcorrenteHomeController ctrl = loader.getController();
			}
			else if (tipo.equals("Societa")) {
				SocietaHomeController ctrl = loader.getController();
			}
			else if (tipo.equals("Arbtiro")) {
				ArbitroHomeController ctrl = loader.getController();
			}
			
			Stage stage = (Stage) tipoCombo.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle(tipo + " Home");
		} catch(Exception e) {
			errLabel.setVisible(true);
			Alerter.showError(e.getMessage());
		}
		
	}

	/* ---------- GUEST ---------- */
	@FXML
	private void handleGuest(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/UtenteEsternoHome.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage = (Stage) guestBtn.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Utente Esterno");
		} catch(IOException e) {
			Alerter.showError(e.getMessage());
		}
		
	}

	/* ---------- HOME ---------- */
	@FXML
	private void handleBack(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Home.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage = (Stage) backBtn.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Orobic Fishing Race");
		} catch(IOException e) {
			Alerter.showError(e.getMessage());
		}
		
	}
}