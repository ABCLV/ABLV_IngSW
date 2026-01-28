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

       
        if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || email.isEmpty()
                || dataNascita == null || pwd.isEmpty()) {
            Alerter.showError("Devi compilare tutti i campi obbligatori!");
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
