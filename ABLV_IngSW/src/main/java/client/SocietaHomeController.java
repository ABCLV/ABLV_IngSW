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
import java.time.format.DateTimeFormatter;

import applicazione.Concorrente;
import applicazione.Gara;
import applicazione.Societa;

public class SocietaHomeController {

    /* ================= DATI SOCIETÀ ================= */
    private Societa societaCorrente;
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
    @FXML
    private void initialize() {
    	societaCorrente = new Societa();
    	this.societaCorrente.setNome(Session.userName);
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
    }

    /* ================= CARICAMENTO DATI ================= */
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

        /* ==== CONCORRENTI (OK) ==== */
        pescatoriObs.setAll(this.societaCorrente.getConcorrenti());

        /* ==== GARE PROPOSTE ==== */
        gareProposteObs.setAll(this.societaCorrente.getGareProposte());
        
    }

    /* ================= EVENTI ================= */
    @FXML
    private void apriRegistraConcorrente(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/RegistraConcorrente.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Registra Concorrente");
        stage.setScene(scene);
        stage.showAndWait();
        ricaricaTabelle();
    }

    @FXML
    private void apriProponiGara(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/ProponiGara.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Proponi gara");
        stage.setScene(scene);
        stage.showAndWait();
        ricaricaTabelle();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Home.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Orobic Fishing Race");
    }

    /* ================= UTILITY ================= */
    private void ricaricaTabelle() throws SQLException {
        pescatoriObs.setAll(this.societaCorrente.getConcorrenti());
        gareProposteObs.setAll(this.societaCorrente.getGareProposte());
    }
}