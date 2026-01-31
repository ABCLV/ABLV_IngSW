package controller.home;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import service.ArbitroService;
import javafx.scene.Node;
import javafx.scene.Parent;
import utils.Alerter;

public class ArbitroHomeController {
	
	private final ArbitroService arbitroService = new ArbitroService();

	@FXML
	private void aggiornaStatoGara(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/AggiornaStatoGara.fxml"));
	        Parent root = loader.load();
	        
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	        Alerter.showError("Errore nel caricamento della pagina");
	    }
	}
	
	@FXML
	private void handleBack(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Home.fxml"));
			Scene homeScene = new Scene(loader.load());
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(homeScene);
			stage.setTitle("Orobic Fishing Race");
		} catch(IOException e) {
			Alerter.showError(e.getMessage());
		}
	}
}