package controller.iscrizione;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Concorrente;
import model.Gara;
import db.repository.IscrizioneDAO;
import service.IscrizioneService;
import service.SocietaService;
import service.exception.IscrizioneEccezione;
import service.exception.RicercaEccezione;

import state.Session;
import utils.Alerter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class IscrizioneGaraController {

    @FXML private VBox concorrenteSection;
    @FXML private ComboBox<Concorrente> concorrenteComboBox;
    @FXML private ComboBox<Gara> garaComboBox;
    @FXML private VBox dettagliGaraSection;
    @FXML private Button btnConferma;
    
    // Label per i dettagli della gara
    @FXML private Label lblCodiceGara;
    @FXML private Label lblData;
    @FXML private Label lblTecnica;
    @FXML private Label lblTipoGara;
    @FXML private Label lblCampoGara;
    @FXML private Label lblCriterioPunti;
    @FXML private Label lblStatoGara;
    @FXML private Label lblCampionato;
    @FXML private Label lblNumProva;

    private IscrizioneDAO garaService = new IscrizioneDAO();
    private IscrizioneService iscrizioneService = new IscrizioneService();
    private SocietaService societaService = new SocietaService();
    
    private ObservableList<Gara> gareList;
    private ObservableList<Gara> gareTotali; // Lista completa delle gare disponibili
    private ObservableList<Concorrente> concorrentiList;
    
    private boolean isSocieta;
    private String userType;

    @FXML
    public void initialize() {
        
        // Determina il ruolo dell'utente
        userType = Session.getUserType();
        isSocieta = userType.equalsIgnoreCase("Societa");
        
        // Mostra la sezione concorrente solo se è una società
        if (isSocieta) {
            btnConferma.setText("Iscivi concorrente ad una gara");
            concorrenteSection.setVisible(true);
            concorrenteSection.setManaged(true);
            caricaConcorrenti();
            setupAutoCompleteConcorrente();
        } else {
            btnConferma.setText("Iscriviti ad una gara");
        }
        
        // Carica le gare disponibili
        caricaGare();
        setupAutoCompleteGara();
        
        // BOTTONE SEMPRE ABILITATO
        btnConferma.setDisable(false);
    }

    private void caricaConcorrenti() {
        try {
            concorrentiList = FXCollections.observableArrayList(
                this.societaService.getConcorrenti(Session.getUserName())
            );
            concorrenteComboBox.setItems(concorrentiList);
        } catch (RicercaEccezione e) {
            e.printStackTrace();
            Alerter.showError(e.getMessage());
        }
    }

    private void caricaGare() {
        try {
            // Carica TUTTE le gare disponibili
            List<Gara> tutteLeGare = this.iscrizioneService.getGareDisponibiliPerIscrizione();
            gareTotali = FXCollections.observableArrayList(tutteLeGare);
            
            // Se è un concorrente, filtra subito
            if (!isSocieta) {
                filtraGarePerConcorrente(Session.getUserName());
            } else {
                // Se è una società, mostra tutte le gare inizialmente
                gareList = FXCollections.observableArrayList(gareTotali);
                garaComboBox.setItems(gareList);
            }
            
        } catch (RicercaEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }
    
    private void filtraGarePerConcorrente(String cf) {
        try {
            // Ottieni le gare a cui è già iscritto
            List<String> gareIscritte = iscrizioneService.getCodiciGareIscrittoPerConcorrente(cf);
            
            // Filtra le gare
            List<Gara> gareFiltrate = gareTotali.stream()
                .filter(g -> !gareIscritte.contains(g.getCodice()))
                .collect(Collectors.toList());
            
            gareList = FXCollections.observableArrayList(gareFiltrate);
            garaComboBox.setItems(gareList);
            
            // Reset della selezione
            garaComboBox.setValue(null);
            nascondiDettagliGara();
            
        } catch (IscrizioneEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }

    private void setupAutoCompleteConcorrente() {
        setupAutoComplete(concorrenteComboBox, concorrentiList);
        
        // Quando viene selezionato un concorrente
        concorrenteComboBox.setOnAction(e -> {
            Concorrente concorrenteSelezionato = getSelectedConcorrente();
            
            if (concorrenteSelezionato != null) {
                // Filtra le gare per il concorrente selezionato
                filtraGarePerConcorrente(concorrenteSelezionato.getCf());
            } else {
                // Se deseleziona, mostra tutte le gare
                gareList = FXCollections.observableArrayList(gareTotali);
                garaComboBox.setItems(gareList);
                nascondiDettagliGara();
            }
        });
    }

    private void setupAutoCompleteGara() {
        setupAutoComplete(garaComboBox, gareList);
        
        // Quando viene selezionata una gara, mostra i dettagli
        garaComboBox.setOnAction(e -> {
            Gara garaSelezionata = getSelectedGara();
            
            if (garaSelezionata != null) {
                mostraDettagliGara(garaSelezionata);
            } else {
                nascondiDettagliGara();
            }
        });
    }

    private <T> void setupAutoComplete(ComboBox<T> comboBox, ObservableList<T> originalList) {
        comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
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
                
                if (!filteredList.isEmpty()) {
                    if (!comboBox.isShowing()) {
                        comboBox.show();
                    }
                } else {
                    comboBox.hide();
                }
            }
        });
        
        comboBox.setOnMouseClicked(e -> {
            if (!comboBox.isShowing()) {
                comboBox.setItems(originalList);
            }
        });
    }

    @FXML
    private void onGaraSelected(ActionEvent event) {
        Gara garaSelezionata = getSelectedGara();
        
        if (garaSelezionata != null) {
            mostraDettagliGara(garaSelezionata);
        } else {
            nascondiDettagliGara();
        }
    }

    private void mostraDettagliGara(Gara gara) {
        lblCodiceGara.setText(gara.getCodice());
        lblData.setText(gara.getData() != null ? gara.getData().toString() : "N/A");
        lblTecnica.setText(gara.getTecnica() != null ? gara.getTecnica().toString() : "N/A");
        lblTipoGara.setText(gara.getTipoGara() != null ? gara.getTipoGara().toString() : "N/A");
        lblCampoGara.setText(gara.getCampoGara() != null ? gara.getCampoGara().getIdCampoGara() : "N/A");
        lblCriterioPunti.setText(gara.getCriterioPunti() != null ? gara.getCriterioPunti().toString() : "N/A");
        lblStatoGara.setText(gara.getStatoGara() != null ? gara.getStatoGara().toString() : "N/A");
        
        // Campi opzionali
        if (gara.getCampionato() != null) {
            lblCampionato.setText(gara.getCampionato().getTitolo());
            lblNumProva.setText(String.valueOf(gara.getNumProva()));
        } else {
            lblCampionato.setText("Gara singola");
            lblNumProva.setText("N/A");
        }
        
        dettagliGaraSection.setVisible(true);
        dettagliGaraSection.setManaged(true);
    }

    private void nascondiDettagliGara() {
        dettagliGaraSection.setVisible(false);
        dettagliGaraSection.setManaged(false);
        // NON disabilitare più il bottone
    }
    
    private Gara getSelectedGara() {
        Object selected = garaComboBox.getValue();
        
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
    
    private Concorrente getSelectedConcorrente() {
        Object selected = concorrenteComboBox.getValue();
        
        if(selected != null) {
            String text = selected.toString();
            int startCF = text.lastIndexOf('(');
            int endCF = text.lastIndexOf(')');
            
            if (startCF != -1 && endCF != -1) {
                String cf = text.substring(startCF + 1, endCF);
                for (Concorrente c : concorrentiList) {
                    if (c.getCf().equals(cf)) {
                        return c;
                    }
                }
            }
        }

        return null;
    }

    @FXML
    private void handleConferma(ActionEvent event) {
        // Controllo 1: Verifica se è stata selezionata una gara
        Gara garaSelezionata = getSelectedGara();
        if (garaSelezionata == null) {
            Alerter.showError("Per favore, seleziona una gara prima di confermare l'iscrizione.");
            return;
        }
        
        String cfConcorrente;
        
        // Controllo 2: Se è una società, verifica che sia stato selezionato un concorrente
        if (isSocieta) {
            Concorrente concorrenteSelezionato = getSelectedConcorrente();
            if (concorrenteSelezionato == null) {
                Alerter.showError("Per favore, seleziona un concorrente da iscrivere alla gara.");
                return;
            }
            cfConcorrente = concorrenteSelezionato.getCf();
        } else {
            cfConcorrente = Session.getUserName();
        }
        
        // Se tutti i controlli passano, procedi con l'iscrizione
        try {
            this.iscrizioneService.iscriviConcorrenteAGara(cfConcorrente, garaSelezionata.getCodice());
            Alerter.showSuccess("Iscrizione completata con successo!");
            handleBack(event);
        } catch (IscrizioneEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            String fxmlPath;
            
            if (isSocieta) {
                fxmlPath = "/view/fxml/societaHome.fxml";
            } else {
                fxmlPath = "/view/fxml/concorrenteHome.fxml";
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alerter.showError("Errore nel ritorno alla home");
        }
    }
}