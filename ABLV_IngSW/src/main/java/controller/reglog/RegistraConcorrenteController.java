package controller.reglog;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.RegistrazioneService;
import state.Session;
import utils.Alerter;

import java.time.LocalDate;

public class RegistraConcorrenteController {

    @FXML private TextField cfField;
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private TextField emailField;
    @FXML private DatePicker nascitaPicker;
    @FXML private PasswordField pwdField;
    
    private final RegistrazioneService registrazioneService = new RegistrazioneService();

    @FXML
    private void registra() {
        String cf = cfField.getText().trim();
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        String email = emailField.getText().trim();
        LocalDate dataNascita = nascitaPicker.getValue();
        String pwd = pwdField.getText();

        // Controllo campi vuoti
        if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty()
                || dataNascita == null || pwd.isEmpty()) {
            Alerter.showError("Devi compilare tutti i campi obbligatori!");
            return;
        }

        // Controllo data valida: non può essere nel futuro
        if (dataNascita.isAfter(LocalDate.now())) {
            Alerter.showError("La data di nascita non può essere successiva a oggi!");
            return;
        }
        /*
        // Regex CF italiano: 6 lettere, 2 numeri, 1 lettera, 2 numeri, 1 lettera, 3 numeri, 1 lettera
        if (!cf.matches("^[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]$")) {
            Alerter.showError("Codice Fiscale non valido! Deve essere 6 lettere, 2 numeri, 1 lettera, 2 numeri, 1 lettera, 3 numeri, 1 lettera");
            return;
        }*/

        // Nome e Cognome: solo lettere e spazi (no numeri)
        if (!nome.matches("^[A-Za-z\\s]+$")) {
            Alerter.showError("Il Nome può contenere solo lettere!");
            return;
        }
        if (!cognome.matches("^[A-Za-z\\s]+$")) {
            Alerter.showError("Il Cognome può contenere solo lettere!");
            return;
        }

        // Regex Email: formato standard
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            Alerter.showError("Formato email non valido!");
            return;
        }

        try {
            this.registrazioneService.registraConcorrente(cf, nome, cognome, email, dataNascita, 
            		Session.getUserName(), pwd);
            Alerter.showSuccess("Concorrente registrato correttamente!");
            chiudi();
        } catch (Exception e) {
            Alerter.showError(e.getMessage());
        }
    }

    @FXML
    private void chiudi() {
        Stage stage = (Stage) cfField.getScene().getWindow();
        stage.close();
    }
}