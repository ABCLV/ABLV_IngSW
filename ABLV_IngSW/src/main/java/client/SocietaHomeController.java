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

import database.Consultazioni;

public class SocietaHomeController {

    @FXML private Label welcomeLabel;

    /* --- tabella pescatori --- */
    @FXML private TableView<ConcorrenteRow> pescatoriTable;
    @FXML private TableColumn<ConcorrenteRow, String> colCF;
    @FXML private TableColumn<ConcorrenteRow, String> colNome;
    @FXML private TableColumn<ConcorrenteRow, String> colCognome;
    @FXML private TableColumn<ConcorrenteRow, String> colEmail;
    @FXML private TableColumn<ConcorrenteRow, String> colNascita;

    /* --- dati società --- */
    @FXML private Label lblSocNome;
    @FXML private Label lblSocIndirizzo;
    @FXML private Label lblSocCitta;
    @FXML private Label lblSocCap;
    @FXML private Label lblSocEmail;

    private final ObservableList<ConcorrenteRow> pescatoriObs = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        welcomeLabel.setText("Benvenuta Società " + Session.userName);

        /* --- carica dati società --- */
        try {
            var soc = Consultazioni.getSocieta(Session.userName);
            lblSocNome.setText("Nome: " + soc.nome());
            lblSocIndirizzo.setText("Indirizzo: " + soc.indirizzo());
            lblSocCitta.setText("Città: " + soc.citta());
            lblSocCap.setText("CAP: " + soc.cap());
            lblSocEmail.setText("Email: " + soc.email());

            /* --- carica pescatori iscritti alla società --- */
            pescatoriObs.setAll(Consultazioni.getConcorrentiPerSocieta(Session.userName));
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
        stage.showAndWait();           // aspetta che chiuda
        ricaricaTabella();             // aggiorna lista
    }

    private void ricaricaTabella() {
        try {
            pescatoriObs.setAll(Consultazioni.getConcorrentiPerSocieta(Session.userName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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