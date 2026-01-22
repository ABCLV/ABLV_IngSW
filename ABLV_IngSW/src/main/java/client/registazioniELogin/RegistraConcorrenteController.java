package client.registazioniELogin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

import applicazione.service.RegistrazioneService;
import client.sessione.Session;

public class RegistraConcorrenteController {

    @FXML
    private TextField cfField;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker nascitaPicker; // modificato da TextField a DatePicker
    @FXML
    private PasswordField pwdField;

    @FXML
    private void registra() {
        String cf = cfField.getText().trim();
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        String email = emailField.getText().trim();
        LocalDate dataNascita = nascitaPicker.getValue(); // ora direttamente LocalDate
        String pwd = pwdField.getText();

        // Controllo campi vuoti
        if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty()
                || dataNascita == null || pwd.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Compilare tutti i campi!");
            alert.showAndWait();
            return;
        }

        try {
            System.out.println(cf + " " + nome + " " + cognome + " " + email + " " + dataNascita + " " + Session.userName + " " + pwd);
            // Registrazione
            RegistrazioneService.registraConcorrente(cf, nome, cognome, email, dataNascita, Session.userName, pwd);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Concorrente registrato!");
            alert.showAndWait();

            chiudi();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void chiudi() {
        Stage stage = (Stage) cfField.getScene().getWindow();
        stage.close();
    }
}
