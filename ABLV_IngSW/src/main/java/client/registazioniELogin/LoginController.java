package client.registazioniELogin;

import applicazione.entita.Amministratore;
import applicazione.entita.Concorrente;
import applicazione.entita.Societa;
import applicazione.service.Autenticazione;
import client.home.AmministratoreHomeController;
import client.home.ConcorrenteHomeController;
import client.home.SocietaHomeController;
import client.sessione.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private ComboBox<String> tipoCombo;
	@FXML
	private TextField idField;
	@FXML
	private PasswordField passField;
	@FXML
	private Label idLabel;
	@FXML
	private Label errLabel;
	@FXML
	private Button backBtn;
	@FXML
	private Button guestBtn;

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
	private void handleLogin(ActionEvent event) throws Exception {
		String tipo = tipoCombo.getValue();
		String id = idField.getText().trim();
		String pwd = passField.getText();

		if (id.isEmpty() || pwd.isEmpty()) {
			errLabel.setVisible(false);
			return;
		}

		boolean ok = false;
		try {
			ok = Autenticazione.verifica(tipo, id, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!ok) {
			errLabel.setVisible(true);
			return;
		}

		/* --- salva sessione --- */
		Session.userName = id;
		Session.userType = tipo;
		
		
		

		/* --- carica pagina dedicata --- */
		String fxml = "/client/" + tipo + "Home.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		Scene scene = new Scene(loader.load());
		if (tipo.equals("Amministratore")) {
		    AmministratoreHomeController ctrl = loader.getController();
		    ctrl.setAmministratore(Amministratore.fromUsername(id));
		}
		else if (tipo.equals("Concorrente")) {
		    ConcorrenteHomeController ctrl = loader.getController();
		    Concorrente c = Concorrente.fromUsername(id);
		    ctrl.setConcorrente(c);
		}
		else if (tipo.equals("Societa")) {
			SocietaHomeController ctrl = loader.getController();
			ctrl.setSocieta(Societa.fromUsername(Session.userName));
				}
		
		Stage stage = (Stage) tipoCombo.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(tipo + " Home");
	}

	/* ---------- GUEST ---------- */
	@FXML
	private void handleGuest(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/UtenteEsternoHome.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = (Stage) guestBtn.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Utente Esterno");
	}

	/* ---------- HOME ---------- */
	@FXML
	private void handleBack(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Home.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = (Stage) backBtn.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Orobic Fishing Race");
	}
}