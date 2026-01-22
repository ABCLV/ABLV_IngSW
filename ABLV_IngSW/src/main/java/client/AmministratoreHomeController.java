package client;

import applicazione.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AmministratoreHomeController {

	@FXML
	private Label welcomeLabel;

	// Tabella gare proposte da me
	@FXML private TableView<Gara> gareProposteTable = new TableView<Gara>();
	@FXML private TableColumn<Gara, String> colCodiceProposte;
	@FXML private TableColumn<Gara, Integer> colNumProvaProposte;
	@FXML private TableColumn<Gara, String> colTecnicaProposte;
	@FXML private TableColumn<Gara, LocalDate> colDataProposte;
	@FXML private TableColumn<Gara, String> colCampionatoProposte;
	@FXML private TableColumn<Gara, String> colCampoGaraProposte;
	@FXML private TableColumn<Gara, String> colStatoConfermaProposte;

	// Tabella gare da confermare
	@FXML private TableView<Gara> gareDaConfermare = new TableView<Gara>();
	@FXML private TableColumn<Gara, String> colCodiceConferma;
	@FXML private TableColumn<Gara, Integer> colNumProvaConferma;
	@FXML private TableColumn<Gara, String> colTecnicaConferma;
	@FXML private TableColumn<Gara, LocalDate> colDataConferma;
	@FXML private TableColumn<Gara, String> colCampionatoConferma;
	@FXML private TableColumn<Gara, String> colPropositoreConferma;
	@FXML private TableColumn<Gara, String> colCampoGaraConferma;

	private Amministratore amministratoreCorrente;

	@FXML
	public void initialize() {
		
		
		// Imposta il messaggio di benvenuto
		welcomeLabel.setText("Benvenuto, " + Session.userName + "!");

		// Configura colonne tabella gare proposte
		setupColonneGareProposte();

		// Configura colonne tabella gare da confermare
		setupColonneGareDaConfermare();

	}

	public void setAmministratore(Amministratore amministratore) {
	    this.amministratoreCorrente = amministratore;
	    caricaDati(); 
	}
	
	private void setupColonneGareProposte() {
		colCodiceProposte.setCellValueFactory(new PropertyValueFactory<>("codice"));
		colNumProvaProposte.setCellValueFactory(new PropertyValueFactory<>("numProva"));
		colTecnicaProposte.setCellValueFactory(
				cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTecnica().name()));
		colDataProposte.setCellValueFactory(new PropertyValueFactory<>("data"));
		colCampionatoProposte.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
				cellData.getValue().getCampionato() != null ? cellData.getValue().getCampionato().getTitolo() : "N/A"));
		colCampoGaraProposte.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
				cellData.getValue().getCampoGara().getIdCampoGara()));
		colStatoConfermaProposte.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
				cellData.getValue().getStatoConferma().name()));
	}

	private void setupColonneGareDaConfermare() {
		colCodiceConferma.setCellValueFactory(new PropertyValueFactory<>("codice"));
		colNumProvaConferma.setCellValueFactory(new PropertyValueFactory<>("numProva"));
		colTecnicaConferma.setCellValueFactory(
				cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTecnica().name()));
		colDataConferma.setCellValueFactory(new PropertyValueFactory<>("data"));
		colCampionatoConferma.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
				cellData.getValue().getCampionato() != null ? cellData.getValue().getCampionato().getTitolo() : "N/A"));
		colPropositoreConferma.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
				cellData.getValue().getPropositore().getIdentificatore()));
		colCampoGaraConferma.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
				cellData.getValue().getCampoGara().getIdCampoGara()));
	}

	private void caricaDati() {
	    try {
	        ObservableList<Gara> mieProposte = 
	            FXCollections.observableArrayList(amministratoreCorrente.mieProposte());
	        gareProposteTable.setItems(mieProposte);

	        ObservableList<Gara> gareDaConf = 
	            FXCollections.observableArrayList(amministratoreCorrente.gareDaConfermare());
	        gareDaConfermare.setItems(gareDaConf);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
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

	@FXML
	private void apriProponiGara() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/ProponiGara.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Proponi Gara");
			stage.setScene(new Scene(root));
			stage.showAndWait();

			// Ricarica i dati dopo la chiusura
			caricaDati();

		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Errore nell'apertura: " + e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void accettaGara() {
		Gara garaSelezionata = gareDaConfermare.getSelectionModel().getSelectedItem();

		if (garaSelezionata == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Seleziona una gara da accettare!");
			alert.showAndWait();
			return;
		}

		try {
			boolean esito = amministratoreCorrente.confermaProposta(garaSelezionata.getCodice());
			if (esito) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Gara accettata con successo!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Errore nell'accettazione della gara!");
				alert.showAndWait();
			}
			caricaDati(); // Ricarica le tabelle
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Errore: " + e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void rifiutaGara() {
		Gara garaSelezionata = gareDaConfermare.getSelectionModel().getSelectedItem();

		if (garaSelezionata == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Seleziona una gara da rifiutare!");
			alert.showAndWait();
			return;
		}

		try {
			boolean esito = amministratoreCorrente.negaProposta(garaSelezionata.getCodice());
			if (esito) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Gara rifiutata!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Errore nel rifiutare la gara!");
				alert.showAndWait();
			}
			caricaDati(); // Ricarica le tabelle
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Errore: " + e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void handleBack(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Home.fxml"));
		Scene homeScene = new Scene(loader.load());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(homeScene);
		stage.setTitle("Orobic Fishing Race");
	}
}