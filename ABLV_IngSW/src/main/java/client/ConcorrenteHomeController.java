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
import java.util.List;

import database.Consultazioni;

public class ConcorrenteHomeController {

	@FXML
	private Label welcomeLabel;

	/* --- dati personali --- */
	@FXML
	private Label lblCF;
	@FXML
	private Label lblNome;
	@FXML
	private Label lblCognome;
	@FXML
	private Label lblEmail;
	@FXML
	private Label lblNascita;
	@FXML
	private Label lblSocieta;

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

	/* --- tabella gare --- */
	@FXML
	private TableView<GaraRow> gareTable;
	@FXML
	private TableColumn<GaraRow, String> colCodice;
	@FXML
	private TableColumn<GaraRow, String> colData;
	@FXML
	private TableColumn<GaraRow, String> colTecnica;
	@FXML
	private TableColumn<GaraRow, String> colCampo;

	private final ObservableList<GaraRow> gareObs = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		welcomeLabel.setText("Benvenuto Concorrente " + Session.userName);

		/* --- carica dati personali --- */
		try {
			var me = Consultazioni.getConcorrente(Session.userName);
			lblCF.setText("CF: " + me.cf());
			lblNome.setText("Nome: " + me.nome());
			lblCognome.setText("Cognome: " + me.cognome());
			lblEmail.setText("Email: " + me.email());
			lblNascita.setText("Nascita: " + me.nascita());
			lblSocieta.setText("Società: " + me.societa());

			/* --- dati società --- */
			var soc = Consultazioni.getSocieta(me.societa());
			lblSocNome.setText("Nome: " + soc.getNome());
			lblSocIndirizzo.setText("Indirizzo: " + soc.getIndirizzo());
			lblSocCitta.setText("Città: " + soc.getCitta());
			lblSocCap.setText("CAP: " + soc.getCitta());
			lblSocEmail.setText("Email: " + soc.getEmail());

			/* --- elenco gare --- */
			List<GaraRow> gare = Consultazioni.getGareConcorrente(me.cf());
			gareObs.setAll(gare);
			gareTable.setItems(gareObs);

			colCodice.setCellValueFactory(d -> d.getValue().codiceProperty());
			colData.setCellValueFactory(d -> d.getValue().dataProperty());
			colTecnica.setCellValueFactory(d -> d.getValue().tecnicaProperty());
			colCampo.setCellValueFactory(d -> d.getValue().campoProperty());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleBack(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Home.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Orobic Fishing Race");
	}
}