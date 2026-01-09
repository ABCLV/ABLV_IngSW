package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Label errLabel;
    @FXML private Button backBtn;
    @FXML private Button guestBtn;

    /* ==================  LOGIN NORMALE  ================== */
    @FXML
    private void handleLogin(ActionEvent event) {
        boolean compilato = !userField.getText().isBlank() && !passField.getText().isBlank();
        if (!compilato) {
            errLabel.setVisible(false);
            return;
        }
        // per ora sempre errore
        errLabel.setVisible(true);
    }

    /* ==================  LOGIN ESTENSO  ================== */
    @FXML
    private void handleGuest(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/UtenteEsterno.fxml"));
        Scene guestScene = new Scene(loader.load());
        Stage stage = (Stage) guestBtn.getScene().getWindow();
        stage.setScene(guestScene);
        stage.setTitle("Utente Esterno");
    }

    /* ==================  TORNA HOME  ================== */
    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Home.fxml"));
        Scene homeScene = new Scene(loader.load());
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(homeScene);
        stage.setTitle("Orobic Fishing Race");
    }
}