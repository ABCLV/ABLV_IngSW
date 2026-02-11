package controller.home;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import utils.Alerter;

public class UtenteEsternoHomeController {

    @FXML
    private void handleGare(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RicercaGare.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Ricerche Gare");
    	} catch(IOException e) {
    		e.printStackTrace();
    		Alerter.showError(e.getMessage());
    	}
        
    }

    @FXML
    private void handleConcorrenti(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RicercaConcorrenti.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Ricerche Concorrenti");
    	} catch(IOException e) {
    		Alerter.showError(e.getMessage());
    	}
        
    }

    @FXML
    private void handleCampoGara(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RicercaCampoGara.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Ricerche Campo Gara");
    	} catch(IOException e) {
    		Alerter.showError(e.getMessage());
    	}
        
    }

    @FXML
    private void handleBack(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Home.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Orobic Fishing Race");
    	} catch(IOException e) {
    		Alerter.showError(e.getMessage());
    	}
        
    }
}
