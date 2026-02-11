package controller.home;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.Stage;
import model.*;
import service.AmministratoreService;
import service.exception.PropostaEccezione;
import utils.Alerter;

import java.io.IOException;
import java.time.LocalDate;
import state.Session;


public class AmministratoreHomeController {

	@FXML
	private Label welcomeLabel;

	// Tabella gare proposte da me
	@FXML private TableView<Gara> gareProposteTable = new TableView<>();
	@FXML private TableColumn<Gara, String> colCodiceProposte;
	@FXML private TableColumn<Gara, Integer> colNumProvaProposte;
	@FXML private TableColumn<Gara, String> colTecnicaProposte;
	@FXML private TableColumn<Gara, LocalDate> colDataProposte;
	@FXML private TableColumn<Gara, String> colCampionatoProposte;
	@FXML private TableColumn<Gara, Integer> colCampoGaraProposte;
	@FXML private TableColumn<Gara, String> colStatoConfermaProposte;

	// Tabella gare da confermare
	@FXML private TableView<Gara> gareDaConfermare = new TableView<>();
	@FXML private TableColumn<Gara, String> colCodiceConferma;
	@FXML private TableColumn<Gara, Integer> colNumProvaConferma;
	@FXML private TableColumn<Gara, String> colTecnicaConferma;
	@FXML private TableColumn<Gara, LocalDate> colDataConferma;
	@FXML private TableColumn<Gara, String> colCampionatoConferma;
	@FXML private TableColumn<Gara, String> colPropositoreConferma;
	@FXML private TableColumn<Gara, Integer> colCampoGaraConferma;

	private final AmministratoreService amministratoreService = new AmministratoreService();

	@FXML
	public void initialize() {
		
		// Imposta il messaggio di benvenuto
		welcomeLabel.setText("Benvenuto, " + Session.getUserName() + "!");

		// Configura colonne tabella gare proposte
		setupColonneGareProposte();

		// Configura colonne tabella gare da confermare
		setupColonneGareDaConfermare();
		
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
		colCampoGaraProposte.setCellValueFactory(cellData ->
	    new javafx.beans.property.SimpleIntegerProperty(
	        cellData.getValue().getCampoGara().getIdCampoGara()
	    ).asObject());
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
		colCampoGaraConferma.setCellValueFactory(cellData ->
	    new javafx.beans.property.SimpleIntegerProperty(
	        cellData.getValue().getCampoGara().getIdCampoGara()
	    ).asObject());

	}

	private void caricaDati() {
	    try {
	        ObservableList<Gara> mieProposte = 
	            FXCollections.observableArrayList(amministratoreService.mieProposte(Session.getUserName()));
	        gareProposteTable.setItems(mieProposte);

	        ObservableList<Gara> gareDaConf = 
	            FXCollections.observableArrayList(amministratoreService.gareDaConfermare(Session.getUserName()));
	        gareDaConfermare.setItems(gareDaConf);
	    } catch (PropostaEccezione e) {
	        new Alert(Alert.AlertType.ERROR, e.getMessage());
	    }
	}

	/* ---------- REGISTRA NUOVO AMMINISTRATORE ---------- */
	@FXML
	private void apriRegistraAmministratore(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegistraAmministratore.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Registra Amministratore");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			Alerter.showError("Errore nell'apertura: " + e.getMessage());
		}
		
	}

	@FXML
	private void apriProponiGara() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/ProponiGara.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Proponi Gara");
			stage.setScene(new Scene(root));
			stage.showAndWait();

			// Ricarica i dati dopo la chiusura
			caricaDati();

		} catch (Exception e) {
			Alerter.showError("Errore nell'apertura: " + e.getMessage());
		}
	}

	@FXML
	private void accettaGara() {
		Gara garaSelezionata = gareDaConfermare.getSelectionModel().getSelectedItem();
		
		if (garaSelezionata == null) {
			Alerter.showError("Seleziona una gara da accettare!");
			return;
		}

		try {
			amministratoreService.confermaProposta(garaSelezionata.getCodice(), Session.getUserName());
			caricaDati();
		} catch(PropostaEccezione e) {
			Alerter.showError(e.getMessage());
		}
	}

	@FXML
	private void rifiutaGara() {
		Gara garaSelezionata = gareDaConfermare.getSelectionModel().getSelectedItem();

		if (garaSelezionata == null) {
			Alerter.showError("Seleziona una gara da rifiutare!");
			return;
		}

		
		
		try {
			amministratoreService.negaProposta(garaSelezionata.getCodice(), Session.getUserName());
			caricaDati();
		} catch(PropostaEccezione e) {
			Alerter.showError(e.getMessage());
		}
	}
	
	@FXML
	private void handleModificaDati(ActionEvent event) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/ModificaDati.fxml"));
	        Parent root = loader.load();
	        
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	        Alerter.showError("Errore nel caricamento della pagina di modifica dei dati");
	    }
	}

	@FXML
	private void handleBack(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Home.fxml"));
			Scene homeScene = new Scene(loader.load());
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(homeScene);
			stage.setTitle("Orobic Fishing Race");
		} catch(Exception e) {
			Alerter.showError(e.getMessage());
		}
		
	}
}