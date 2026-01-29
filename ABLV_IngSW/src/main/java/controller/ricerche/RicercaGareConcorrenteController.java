package controller.ricerche;

import javafx.beans.property.SimpleStringProperty;
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
import model.Concorrente;
import model.Gara;
import service.ConcorrenteService;
import service.SocietaService;
import utils.Alerter;

import java.io.IOException;
import java.util.List;

public class RicercaGareConcorrenteController {

	@FXML private Label welcomeLabel;

	/* --- dati personali --- */
	@FXML private Label lblCF;
	@FXML private Label lblNome;
	@FXML private Label lblCognome;
	@FXML private Label lblEmail;
	@FXML private Label lblNascita;
	@FXML private Label lblSocieta;

	/* --- dati societ√† --- */
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

	private Concorrente concorrente;
	private final SocietaService societaService = new SocietaService();
	private final ConcorrenteService concorrenteService = new ConcorrenteService();
	
	@FXML
	private void initialize() {

	    colCodice.setCellValueFactory(new PropertyValueFactory<>("codice"));
	    colData.setCellValueFactory(new PropertyValueFactory<>("data"));
	    colTecnica.setCellValueFactory(cellData ->
	        new SimpleStringProperty(cellData.getValue().getTecnica().toString()));
	    colCampo.setCellValueFactory(cellData ->
	        new SimpleStringProperty(cellData.getValue().getCampoGara().getIdCampoGara()));

	    gareTable.setItems(gareObs);
	}

	
	public void setConcorrente(Concorrente concorrente) {
		this.concorrente = concorrente;
		caricaDati();
	}
	
	private void caricaDati() {
	    try {
	        lblCF.setText("CF: " + concorrente.getCf());
	        lblNome.setText("Nome: " + concorrente.getNome());
	        lblCognome.setText("Cognome: " + concorrente.getCognome());
	        lblEmail.setText("Email: " + concorrente.getEmail());
	        lblNascita.setText("Nascita: " + concorrente.getNascita());
	        lblSocieta.setText("Societ‡: " + concorrente.getSocieta());

	        var soc = societaService.getSocieta(concorrente.getSocieta());

	        lblSocNome.setText("Nome: " + soc.getNome());
	        lblSocIndirizzo.setText("Indirizzo: " + soc.getIndirizzo());
	        lblSocCitta.setText("Citt‡: " + soc.getCitta());
	        lblSocCap.setText("CAP: " + soc.getCap());
	        lblSocEmail.setText("Email: " + soc.getEmail());

	        List<Gara> gare = concorrenteService.getGareIscritte(concorrente.getCf());
	        gareObs.setAll(gare);

	    } catch (Exception e) {
	        Alerter.showError(e.getMessage());
	    }
	}


	@FXML
	private void handleBack(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Home.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Orobic Fishing Race");
	}
}