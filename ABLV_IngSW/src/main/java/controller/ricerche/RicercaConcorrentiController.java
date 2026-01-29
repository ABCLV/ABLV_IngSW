package controller.ricerche;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Concorrente;
import service.RicercaService;
import utils.Alerter;

public class RicercaConcorrentiController {

    @FXML private TableView<Concorrente> table;
    @FXML private TableColumn<Concorrente, String> cfCol;
    @FXML private TableColumn<Concorrente, String> nomeCol;
    @FXML private TableColumn<Concorrente, String> cognomeCol;
    @FXML private TableColumn<Concorrente, String> emailCol;
    @FXML private TableColumn<Concorrente, String> nascitaCol;
    @FXML private TableColumn<Concorrente, String> societa;
    @FXML private Button backBtn;

    private final RicercaService ricercaService = new RicercaService();

	@FXML
    private void initialize() {
        // Associa le colonne ai getter della classe Concorrente
        cfCol.setCellValueFactory(new PropertyValueFactory<>("cf"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeCol.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nascitaCol.setCellValueFactory(new PropertyValueFactory<>("nascita"));
        societa.setCellValueFactory(new PropertyValueFactory<>("societa"));

        // Carica dati dal DB
        ObservableList<Concorrente> dati = FXCollections.observableArrayList(this.ricercaService.getConcorrenti());
        table.setItems(dati);
        
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Concorrente concorrente = table.getSelectionModel().getSelectedItem();
                if (concorrente != null) {
                    apriGareConcorrente();
                }
            }
        });
    }
    
    
    private void apriGareConcorrente() {
        try {
        	Concorrente concorrente = table.getSelectionModel().getSelectedItem();
        	if(concorrente != null) {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/ricercaConcorrenteGare.fxml"));
                Parent root = loader.load();
                RicercaGareConcorrenteController ctrl = loader.getController();
                ctrl.setConcorrente(concorrente);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Profilo concorrente");
                stage.show();
        	} else {
        		throw new IOException("Errore nel recuperare il concorrente selezionato!");
        	}
        } catch (IOException e) {
            Alerter.showError(e.getMessage());
            System.out.println(e.getMessage());
        }
    }



    @FXML
    private void handleBack(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/UtenteEsternoHome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Ricerche");
    	} catch(IOException e) {
    		Alerter.showError(e.getMessage());
    	}
    }
}
