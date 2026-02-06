package controller.update;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.Gara;
import model.enums.StatoGara;
import service.ArbitroService;
import service.exception.AggiornaEccezione;
import service.exception.RicercaEccezione;

import state.Session;

import utils.Alerter;

import java.io.IOException;

public class AggiornaStatoGaraController {

    private ArbitroService arbitroService = new ArbitroService();
	
    private ObservableList<Gara> gareList;
    @FXML private ComboBox<Gara> gareComboBox = new ComboBox<>();
    @FXML private ComboBox<StatoGara> statoGaraComboBox = new ComboBox<>(); 

    @FXML
    public void initialize() {
        caricaGare();
        
        statoGaraComboBox.getItems().setAll(StatoGara.values());
        
        setupAutoComplete(gareComboBox, gareList);
    }

    private void caricaGare() {
        try {
            this.gareList = FXCollections.observableArrayList(arbitroService.getGareAggiornabiliPerArbitro(Session.getUserName()));
            gareComboBox.setItems(this.gareList);
        } catch (RicercaEccezione e) {
            e.printStackTrace();
            Alerter.showError("Errore nel caricamento delle gare");
        }
    }
    
    private <T> void setupAutoComplete(ComboBox<T> comboBox, ObservableList<T> originalList) {
        comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            // Non filtrare se l'utente sta selezionando un valore dalla lista
            if (comboBox.getSelectionModel().getSelectedItem() != null && 
                newValue.equals(comboBox.getSelectionModel().getSelectedItem().toString())) {
                return;
            }
            
            if (newValue == null || newValue.isEmpty()) {
                comboBox.setItems(originalList);
                comboBox.hide();
            } else {
                ObservableList<T> filteredList = FXCollections.observableArrayList();
                String filter = newValue.toLowerCase();
                
                for (T item : originalList) {
                    if (item.toString().toLowerCase().contains(filter)) {
                        filteredList.add(item);
                    }
                }
                
                comboBox.setItems(filteredList);
                
                // Mostra la dropdown solo se ci sono risultati
                if (!filteredList.isEmpty()) {
                    if (!comboBox.isShowing()) {
                        comboBox.show();
                    }
                } else {
                    comboBox.hide();
                }
            }
        });
        
        // Quando viene selezionato un valore dalla lista, aggiorna l'editor
        comboBox.setOnAction(e -> {
            if (comboBox.getValue() != null) {
                comboBox.getEditor().setText(comboBox.getValue().toString());
            }
        });
        
        // Quando si clicca sulla ComboBox, mostra tutti gli elementi
        comboBox.setOnMouseClicked(e -> {
            if (!comboBox.isShowing()) {
                comboBox.setItems(originalList);
            }
        });
    }
    
    private Gara getSelectedGara() {
        Object selected = gareComboBox.getValue();
        
        if(selected != null) {
        	String text = selected.toString();
            String cod = text.split(",")[0].trim();
            
            for (Gara g : gareList) {
                if (g.getCodice().equals(cod)) {
                    return g;
                }
            }
        }
        
        return null;
    }

    @FXML
    private void confermaAzione(ActionEvent event) {
        Gara garaSelezionata = gareComboBox.getValue();
        StatoGara statoSelezionato = statoGaraComboBox.getValue();
        
        if (garaSelezionata == null) {
            Alerter.showError("Seleziona una gara prima di confermare");
            return;
        } else if(statoSelezionato == null) {
        	Alerter.showError("Seleziona uno stato gara prima di confermare");
        	return;
        } else if(statoSelezionato == garaSelezionata.getStatoGara()) {
        	Alerter.showError("Lo stato che si vuole impostare deve essere diverso dall'attuale");
        	return;
        }

        try {
        	//Passiamo solo il codice gara perchè non serve tutto il resto...
        	this.arbitroService.aggiornaStatoGara(garaSelezionata.getCodice(), statoSelezionato);
        	caricaGare();
        	for (Gara g : gareList) {
                if (g.getCodice().equals(garaSelezionata.getCodice())) {
                    gareComboBox.setValue(g);
                    break;
                }
            }
            
            // Reset della ComboBox dello stato
            statoGaraComboBox.setValue(null);
			Alerter.showSuccess("Stato della gara aggiornato correttamente!");
        } catch(AggiornaEccezione e) {
        	Alerter.showError(e.getMessage());
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home/arbitroHome.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alerter.showError("Errore nel ritorno alla home");
        }
    }
}