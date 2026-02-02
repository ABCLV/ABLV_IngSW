package controller.home;

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
import model.Concorrente;
import model.Gara;
import model.Societa;
import service.ConcorrenteService;
import service.exception.ConcorrenteHomeEccezione;
import state.Session;
import utils.Alerter;

import java.io.IOException;
import java.util.List;
public class ConcorrenteHomeController {

	@FXML
	private Label welcomeLabel;

	/* --- dati personali --- */
	@FXML private Label lblCF;
	@FXML private Label lblNome;
	@FXML private Label lblCognome;
	@FXML private Label lblEmail;
	@FXML private Label lblNascita;
	@FXML private Label lblSocieta;

	/* --- dati società --- */
	@FXML private Label lblSocNome;
	@FXML private Label lblSocIndirizzo;
	@FXML private Label lblSocCitta;
	@FXML private Label lblSocCap;
	@FXML private Label lblSocEmail;

	/* --- tabella gare --- */
	@FXML private TableView<Gara> gareTable;
	@FXML private TableColumn<Gara, String> colCodice;
	@FXML private TableColumn<Gara, String> colData;
	@FXML private TableColumn<Gara, String> colTecnica;
	@FXML private TableColumn<Gara, String> colCampo;
	
	private final ObservableList<Gara> gareObs = FXCollections.observableArrayList();
	
	private final ConcorrenteService concorrenteService = new ConcorrenteService();
	
	private void caricaDati() {
		try {
			Concorrente concorrente = concorrenteService.getConcorrente(Session.getUserName());
			
			welcomeLabel.setText("Benvenuto Concorrente " + concorrente.getNome());

		    lblCF.setText("CF: " + concorrente.getCf());
		    lblNome.setText("Nome: " + concorrente.getNome());
		    lblCognome.setText("Cognome: " + concorrente.getCognome());
		    lblEmail.setText("Email: " + concorrente.getEmail());
		    lblNascita.setText("Nascita: " + concorrente.getNascita());
		    lblSocieta.setText("Società: " + concorrente.getSocieta());

		    Societa s = concorrenteService.getDettagliSocieta(concorrente.getSocieta());
		    lblSocNome.setText("Nome: " + s.getNome());
		    lblSocIndirizzo.setText("Indirizzo: " + s.getIndirizzo());
		    lblSocCitta.setText("Città: " + s.getCitta());
		    lblSocCap.setText("CAP: " + s.getCap());
		    lblSocEmail.setText("Email: " + s.getEmail());

		    List<Gara> gare = concorrenteService.getGareIscritte(Session.getUserName());
		    gareObs.setAll(gare);
		    gareTable.setItems(gareObs);
		} catch(ConcorrenteHomeEccezione e) {
			Alerter.showError(e.getMessage());
		}
	}
	    
	
	@FXML
	private void initialize() {
		colCodice.setCellValueFactory(new PropertyValueFactory<>("codice"));
		colData.setCellValueFactory(new PropertyValueFactory<>("data"));
		colTecnica.setCellValueFactory(
		    cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTecnica().name()));
		colCampo.setCellValueFactory(
		    cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCampoGara().getIdCampoGara()));
		
		caricaDati();
	}
	
	@FXML
	private void apriIscrizioneGara(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/IscrizioneGara.fxml"));
	        Parent root = loader.load();
	        
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	        Alerter.showError("Errore nel caricamento della pagina di iscrizione");
	    }
	}
	
	@FXML
	private void apriModificaDati(ActionEvent event) {
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
			Scene scene = new Scene(loader.load());
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Orobic Fishing Race");
		} catch(IOException e) {
			Alerter.showError(e.getMessage());
		}
	}
}