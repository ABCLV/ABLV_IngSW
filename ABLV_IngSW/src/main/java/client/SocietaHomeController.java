package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import applicazione.Concorrente;
import database.Consultazioni;

public class SocietaHomeController {

	@FXML
	private Label welcomeLabel;

	/* --- tabella pescatori --- */
	@FXML
	private TableView<ConcorrenteFxWrapper> pescatoriTable;
	@FXML
	private TableColumn<ConcorrenteFxWrapper, String> colCF;
	@FXML
	private TableColumn<ConcorrenteFxWrapper, String> colNome;
	@FXML
	private TableColumn<ConcorrenteFxWrapper, String> colCognome;
	@FXML
	private TableColumn<ConcorrenteFxWrapper, String> colEmail;
	@FXML
	private TableColumn<ConcorrenteFxWrapper, String> colNascita;

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

	private final ObservableList<ConcorrenteFxWrapper> pescatoriObs = FXCollections.observableArrayList();

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
			List<Concorrente> lista = Consultazioni.getConcorrentiPerSocieta(Session.userName);
			pescatoriObs.setAll(lista.stream().map(ConcorrenteFxWrapper::new).toList());
			pescatoriTable.setItems(pescatoriObs);

			colCF.setCellValueFactory(d -> d.getValue().cfProperty());
			colNome.setCellValueFactory(d -> d.getValue().nomeProperty());
			colCognome.setCellValueFactory(d -> d.getValue().cognomeProperty());
			colEmail.setCellValueFactory(d -> d.getValue().emailProperty());
			colNascita.setCellValueFactory(d -> d.getValue().nascitaProperty());

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
			pescatoriObs.setAll(
				Consultazioni.getConcorrentiPerSocieta(Session.userName)
				             .stream()
				             .map(ConcorrenteFxWrapper::new)
				             .toList()
			);
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