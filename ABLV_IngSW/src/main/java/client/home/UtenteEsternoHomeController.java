package client.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UtenteEsternoHomeController {

    @FXML
    private void handleGare(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/RicercaGare.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ricerche Gare");
    }

    @FXML
    private void handleConcorrenti(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/RicercaConcorrenti.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ricerche Concorrenti");
    }

    @FXML
    private void handleCampoGara(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/RicercaCampoGara.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ricerche Campo Gara");
    }

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/Home.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Orobic Fishing Race");
    }
}
