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
import java.util.List;

import applicazione.Concorrente;
import applicazione.Gara;
import applicazione.Societa;
public class ConcorrenteHomeController {

	private Concorrente concorrenteCorrente;
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

	public void setConcorrente(Concorrente c) {
	    this.concorrenteCorrente = c;
	    try {
			caricaDati();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void caricaDati() throws SQLException {
	    welcomeLabel.setText("Benvenuto Concorrente " + concorrenteCorrente.getNome());

	    lblCF.setText("CF: " + concorrenteCorrente.getCf());
	    lblNome.setText("Nome: " + concorrenteCorrente.getNome());
	    lblCognome.setText("Cognome: " + concorrenteCorrente.getCognome());
	    lblEmail.setText("Email: " + concorrenteCorrente.getEmail());
	    lblNascita.setText("Nascita: " + concorrenteCorrente.getNascita());
	    lblSocieta.setText("Società: " + concorrenteCorrente.getSocieta());

	    Societa s = concorrenteCorrente.getDettagliSocieta();
	    lblSocNome.setText("Nome: " + s.getNome());
	    lblSocIndirizzo.setText("Indirizzo: " + s.getIndirizzo());
	    lblSocCitta.setText("Città: " + s.getCitta());
	    lblSocCap.setText("CAP: " + s.getCap());
	    lblSocEmail.setText("Email: " + s.getEmail());

	    List<Gara> gare = concorrenteCorrente.getGareIscritte();
	    gareObs.setAll(gare);
	    gareTable.setItems(gareObs);
	}
	
	@FXML
	private void initialize() {
	

			colCodice.setCellValueFactory(new PropertyValueFactory<>("codice"));
			colData.setCellValueFactory(new PropertyValueFactory<>("data"));
			colTecnica.setCellValueFactory(
			    cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTecnica().name()));
			colCampo.setCellValueFactory(
			    cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCampoGara().getIdCampoGara()));

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