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
import service.SocietaService;
import service.exception.SocietaHomeEccezione;
import state.Session;
import utils.Alerter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class SocietaHomeController {

    /* ================= DATI SOCIETÀ ================= */
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /* ================= WIDGETS ================= */
    @FXML private Label welcomeLabel;

    /* --- tabella concorrenti --- */
    @FXML private TableView<Concorrente> pescatoriTable = new TableView<>();
    @FXML private TableColumn<Concorrente, String> colCF;
    @FXML private TableColumn<Concorrente, String> colNome;
    @FXML private TableColumn<Concorrente, String> colCognome;
    @FXML private TableColumn<Concorrente, String> colEmail;
    @FXML private TableColumn<Concorrente, String> colNascita;

    /* --- tabella gare proposte --- */
    @FXML private TableView<Gara> gareProposteTable = new TableView<>();
    @FXML private TableColumn<Gara, String> colCodiceProposte;
    @FXML private TableColumn<Gara, Integer> colNumProvaProposte;
    @FXML private TableColumn<Gara, String> colTecnicaProposte;
    @FXML private TableColumn<Gara, String> colDataProposte;
    @FXML private TableColumn<Gara, String> colCampionatoProposte;
    @FXML private TableColumn<Gara, String> colStatoConfermaProposte;
    @FXML private TableColumn<Gara, String> colCampoGaraProposte;

    /* --- riepilogo società (right) --- */
    @FXML private Label lblSocNome;
    @FXML private Label lblSocIndirizzo;
    @FXML private Label lblSocCitta;
    @FXML private Label lblSocCap;
    @FXML private Label lblSocEmail;

    /* ================= OBSERVABLE LISTS ================= */
    private final ObservableList<Concorrente> pescatoriObs = FXCollections.observableArrayList();
    private final ObservableList<Gara> gareProposteObs = FXCollections.observableArrayList();

    /* ================= INIZIALIZZAZIONE ================= */
    private final SocietaService societaService = new SocietaService();
    
    @FXML
    private void initialize() {
        /* colonne concorrenti */
        colCF.setCellValueFactory(new PropertyValueFactory<>("cf"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNascita.setCellValueFactory(cd -> {
            /* Concorrente ha LocalDate -> stringa formattata */
            var data = cd.getValue().getNascita();
            return new javafx.beans.property.SimpleStringProperty(data == null ? "" : data.format(DATE_FMT));
        });

        /* colonne gare proposte */
        colCodiceProposte.setCellValueFactory(new PropertyValueFactory<>("codice"));
        colNumProvaProposte.setCellValueFactory(new PropertyValueFactory<>("numProva"));
        colTecnicaProposte.setCellValueFactory(g -> new javafx.beans.property.SimpleStringProperty(
                g.getValue().getTecnica() == null ? "" : g.getValue().getTecnica().name()));
        colDataProposte.setCellValueFactory(g -> {
            var data = g.getValue().getData();
            return new javafx.beans.property.SimpleStringProperty(data == null ? "" : data.format(DATE_FMT));
        });
        colCampionatoProposte.setCellValueFactory(g -> {
            var camp = g.getValue().getCampionato();
            return new javafx.beans.property.SimpleStringProperty(
                    camp == null || camp.getTitolo() == null ? "" : camp.getTitolo());
        });
        colStatoConfermaProposte.setCellValueFactory(g -> new javafx.beans.property.SimpleStringProperty(
                g.getValue().getStatoConferma() == null ? "" : g.getValue().getStatoConferma().name()));
        colCampoGaraProposte.setCellValueFactory(g -> {
            var cg = g.getValue().getCampoGara();
            return new javafx.beans.property.SimpleStringProperty(
                    cg == null || cg.getDescrizione() == null ? "" : cg.getDescrizione());
        });

        /* aggancia observable lists */
        pescatoriTable.setItems(pescatoriObs);
        gareProposteTable.setItems(gareProposteObs);
        
        caricaDati();
    }

    /* ================= CARICAMENTO DATI ================= */

    private void caricaDati() {
    	try {
    		Societa societa = societaService.getSocieta(Session.getUserName());
    		
    		welcomeLabel.setText("Benvenuta Società " + societa.getNome());

            lblSocNome.setText("Nome: " + societa.getNome());
            lblSocIndirizzo.setText("Indirizzo: " + societa.getIndirizzo());
            lblSocCitta.setText("Città: " + societa.getCitta());
            lblSocCap.setText("CAP: " + societa.getCap());
            lblSocEmail.setText("Email: " + societa.getEmail());

            /* ==== CONCORRENTI (OK) ==== */
            pescatoriObs.setAll(this.societaService.getConcorrenti(societa.getNome()));

            /* ==== GARE PROPOSTE ==== */
            gareProposteObs.setAll(this.societaService.getGareProposte(societa.getNome()));
    	} catch(SocietaHomeEccezione e) {
    		Alerter.showError(e.getMessage());
    	}
    }

    /* ================= EVENTI ================= */
    @FXML
    private void apriRegistraConcorrente(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegistraConcorrente.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Registra Concorrente");
            stage.setScene(scene);
            stage.showAndWait();
            ricaricaTabelle();
    	} catch(IOException e) {
    		Alerter.showError(e.getMessage());
    	}
        
    }

    @FXML
    private void apriProponiGara(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/ProponiGara.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Proponi gara");
            stage.setScene(scene);
            stage.showAndWait();
            ricaricaTabelle();
    	} catch(IOException e) {
    		Alerter.showError(e.getMessage());
    	}
        
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
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Home.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Orobic Fishing Race");
    }

    /* ================= UTILITY ================= */
    private void ricaricaTabelle() throws IOException {
    	try {
    		Societa societa = societaService.getSocieta(Session.getUserName());
            pescatoriObs.setAll(this.societaService.getConcorrenti(societa.getNome()));
            gareProposteObs.setAll(this.societaService.getGareProposte(societa.getNome()));
    	} catch(SocietaHomeEccezione e) {
    		throw new IOException(e.getMessage());
    	}
    }
}