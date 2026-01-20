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
import applicazione.Societa;

public class SocietaHomeController {

	private Societa societaCorrente;
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

	public void setSocieta(Societa s) throws SQLException {
		this.societaCorrente = s;
		caricaDati();
	}

	private void caricaDati() throws SQLException {
		welcomeLabel.setText("Benvenuta Società " + societaCorrente.getNome());

		lblSocNome.setText("Nome: " + societaCorrente.getNome());
		lblSocIndirizzo.setText("Indirizzo: " + societaCorrente.getIndirizzo());
		lblSocCitta.setText("Città: " + societaCorrente.getCitta());
		lblSocCap.setText("CAP: " + societaCorrente.getCap());
		lblSocEmail.setText("Email: " + societaCorrente.getEmail());

		pescatoriObs.setAll(societaCorrente.getConcorrentiIscritti());
		pescatoriTable.setItems(pescatoriObs);
	}

	@FXML
	private void initialize() {

		colCF.setCellValueFactory(new PropertyValueFactory<>("cf"));
		colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colNascita.setCellValueFactory(new PropertyValueFactory<>("nascita"));
	}

	/* ---------- REGISTRA CONCORRENTE ---------- */
	@FXML
	private void apriRegistraConcorrente(ActionEvent event) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/RegistraConcorrente.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = new Stage();
		stage.setTitle("Registra Concorrente");
		stage.setScene(scene);
		stage.showAndWait(); // aspetta che chiuda
		ricaricaTabella(); // aggiorna lista
	}

	private void ricaricaTabella() throws SQLException {
	    pescatoriObs.setAll(societaCorrente.getConcorrentiIscritti());
	}

	/* ---------- PROPONI GARA ---------- */
	@FXML
	private void apriProponiGara(ActionEvent event) throws IOException, SQLException {
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