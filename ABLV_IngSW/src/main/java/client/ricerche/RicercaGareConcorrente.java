package client.ricerche;

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
import java.util.List;

import applicazione.entita.Concorrente;
import applicazione.entita.Gara;
import database.Consultazioni;

public class RicercaGareConcorrente {

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
	private TableView<Gara> gareTable;
	@FXML
	private TableColumn<Gara, String> colCodice;
	@FXML
	private TableColumn<Gara, String> colData;
	@FXML
	private TableColumn<Gara, String> colTecnica;
	@FXML
	private TableColumn<Gara, String> colCampo;

	private final ObservableList<Gara> gareObs = FXCollections.observableArrayList();

	@FXML
	private void initialize() {

		/* --- carica dati personali --- */
		try {
			Concorrente selezionato = RicercaConcorrentiController.getSelezionato();
			lblCF.setText("CF: " + selezionato.cf);
			lblNome.setText("Nome: " + selezionato.getNome());
			lblCognome.setText("Cognome: " + selezionato.getCognome());
			lblEmail.setText("Email: " + selezionato.getEmail());
			lblNascita.setText("Nascita: " + selezionato.getNascita());
			lblSocieta.setText("Società: " + selezionato.getSocieta());

			/* --- dati società --- */
			var soc = Consultazioni.getSocieta(selezionato.getSocieta());
			lblSocNome.setText("Nome: " + soc.getNome());
			lblSocIndirizzo.setText("Indirizzo: " + soc.getIndirizzo());
			lblSocCitta.setText("Città: " + soc.getCitta());
			lblSocCap.setText("CAP: " + soc.getCap());
			lblSocEmail.setText("Email: " + soc.getEmail());

			/* --- elenco gare --- */
			List<Gara> gare = Consultazioni.getGareConcorrente(selezionato.cf);
			System.out.println(gare.toString());
			gareObs.setAll(gare);
			gareTable.setItems(gareObs);

			colCodice.setCellValueFactory(new PropertyValueFactory<>("codice"));
			colData.setCellValueFactory(new PropertyValueFactory<>("data"));
			colTecnica.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
					cellData.getValue().getTecnica().toString()));
			colCampo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
					cellData.getValue().getCampoGara().getIdCampoGara()));

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