package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import applicazione.Concorrente;
import database.Consultazioni;

public class SocietaHomeController {

	@FXML
	private Label welcomeLabel;

	/* --- tabella pescatori --- */
	@FXML
	private TableView<Concorrente> pescatoriTable;
	@FXML
	private TableColumn<Concorrente, String> colCF;
	@FXML
	private TableColumn<Concorrente, String> colNome;
	@FXML
	private TableColumn<Concorrente, String> colCognome;
	@FXML
	private TableColumn<Concorrente, String> colEmail;
	@FXML
	private TableColumn<Concorrente, String> colNascita;

	/* --- dati società --- */
	@FXML
	private Label lblSocNome;
	@FXML
	private Label lblSocIndirizzo;
	@FXML
	private Label lblSocCitta;
	@FXML
	private Label lblSocCap;
	@FXML
	private Label lblSocEmail;

	private final ObservableList<Concorrente> pescatoriObs = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		welcomeLabel.setText("Benvenuta Società " + Session.userName);

		/* --- carica dati società --- */
		try {
			var soc = Consultazioni.getSocieta(Session.userName);
			lblSocNome.setText("Nome: " + soc.getNome());
			lblSocIndirizzo.setText("Indirizzo: " + soc.getIndirizzo());
			lblSocCitta.setText("Città: " + soc.getCitta());
			lblSocCap.setText("CAP: " + soc.getCap());
			lblSocEmail.setText("Email: " + soc.getEmail());

			/* --- carica pescatori iscritti alla società --- */
			pescatoriObs.setAll(Consultazioni.getConcorrentiPerSocieta(Session.userName));
			pescatoriTable.setItems(pescatoriObs);

			colCF.setCellValueFactory(new PropertyValueFactory<>("cf"));
			colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
			colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			colNascita.setCellValueFactory(new PropertyValueFactory<>("nascita"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ---------- REGISTRA CONCORRENTE ---------- */
	@FXML
	private void apriRegistraConcorrente(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/RegistraConcorrente.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = new Stage();
		stage.setTitle("Registra Concorrente");
		stage.setScene(scene);
		stage.showAndWait(); // aspetta che chiuda
		ricaricaTabella(); // aggiorna lista
	}

	private void ricaricaTabella() {
		try {
			pescatoriObs.setAll(Consultazioni.getConcorrentiPerSocieta(Session.userName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* ---------- PROPONI GARA ---------- */
	@FXML
	private void apriProponiGara(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/ProponiGara.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = new Stage();
		stage.setTitle("Proponi gara");
		stage.setScene(scene);
		stage.showAndWait(); // aspetta che chiuda
		ricaricaTabella(); // aggiorna lista
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