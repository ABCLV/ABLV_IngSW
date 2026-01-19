package client;

import database.Consultazioni;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import applicazione.*;

import java.time.LocalDate;
import java.util.List;

public class ProponiGaraController {

    @FXML
    private Spinner<Integer> numProvaSpinner = new Spinner<Integer>();
    @FXML
    private Spinner<Integer> minPersoneSpinner = new Spinner<Integer>();
    @FXML
    private Spinner<Integer> maxPersoneSpinner = new Spinner<Integer>();

    @FXML
    private ComboBox<Tecnica> tecnicaBox = new ComboBox<Tecnica>();
    @FXML
    private ComboBox<TipologiaGara> tipologiaBox = new ComboBox<TipologiaGara>();
    
    // Nuove ComboBox
    @FXML
    private ComboBox<Campionato> campionatoBox = new ComboBox<Campionato>();
    @FXML
    private ComboBox<Arbitro> arbitroBox = new ComboBox<Arbitro>();
    @FXML
    private ComboBox<CampoGara> campoGaraBox = new ComboBox<CampoGara>();

    @FXML
    private TextField criterioField = new TextField();
    @FXML
    private DatePicker dataPicker = new DatePicker();

    @FXML
    private Button proponiButton;
    
    private boolean isAdjusting = false;
    
    // Liste per filtraggio
    private ObservableList<Campionato> campionatiList;
    private ObservableList<Arbitro> arbitriList;
    private ObservableList<CampoGara> campiGaraList;
    
    @FXML
    public void initialize() {

        // Spinner numerici
        numProvaSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );

        minPersoneSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );

        maxPersoneSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 2)
        );

        // ComboBox ENUM
        tecnicaBox.getItems().setAll(Tecnica.values());
        tipologiaBox.getItems().setAll(TipologiaGara.values());

        // Carica dati dal database per le nuove ComboBox
        caricaDatiDatabase();
        
        // Rendi le ComboBox editabili per permettere la ricerca
        campionatoBox.setEditable(true);
        arbitroBox.setEditable(true);
        campoGaraBox.setEditable(true);
        
        // Configura autocomplete/filtro
        setupAutoComplete(campionatoBox, campionatiList);
        setupAutoComplete(arbitroBox, arbitriList);
        setupAutoComplete(campoGaraBox, campiGaraList);

        // Listener per controllo min < max
        minPersoneSpinner.valueProperty().addListener((obs, oldV, newV) -> {
            if (!isAdjusting) {
                isAdjusting = true;
                if (newV >= maxPersoneSpinner.getValue()) {
                    maxPersoneSpinner.getValueFactory().setValue(newV + 1);
                }
                isAdjusting = false;
            }
        });

        maxPersoneSpinner.valueProperty().addListener((obs, oldV, newV) -> {
            if (!isAdjusting) {
                isAdjusting = true;
                if (newV <= minPersoneSpinner.getValue()) {
                    minPersoneSpinner.getValueFactory().setValue(newV - 1);
                }
                isAdjusting = false;
            }
        });
    }

    private void caricaDatiDatabase() {
        // Carica i campionati dal database
        campionatiList = FXCollections.observableArrayList(Consultazioni.getCampionati());
        campionatoBox.setItems(campionatiList);
        
        // Carica gli arbitri dal database
        arbitriList = FXCollections.observableArrayList(Consultazioni.getArbitri());
        arbitroBox.setItems(arbitriList);
        
        // Carica i campi gara dal database
        campiGaraList = FXCollections.observableArrayList(Consultazioni.getCampiGara());
        campoGaraBox.setItems(campiGaraList);
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

    private boolean checkMinMax() {
        int min = minPersoneSpinner.getValue();
        int max = maxPersoneSpinner.getValue();

        if (min >= max) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Il numero minimo di partecipanti deve essere minore del massimo!");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    private Campionato getSelectedCampionato() {
        Object selected = campionatoBox.getValue();
        
        if(selected != null) {
        	String titolo = selected.toString();
            for (Campionato c : campionatiList) {
                if (c.getTitolo().equals(titolo)) {
                    return c;
                }
            }
        }

        return null;
    }
    
    private Arbitro getSelectedArbitro() {
        Object selected = arbitroBox.getValue();
        
        if(selected != null) {
        	String text = selected.toString();
            int startCF = text.lastIndexOf('(');
            int endCF = text.lastIndexOf(')');
            
            if (startCF != -1 && endCF != -1) {
                String cf = text.substring(startCF + 1, endCF);
                for (Arbitro a : arbitriList) {
                    if (a.getCfArbitro().equals(cf)) {
                        return a;
                    }
                }
            }
        }

        return null;
    }
    
    private CampoGara getSelectedCampoGara() {
        Object selected = campoGaraBox.getValue();
        
        if(selected != null) {
        	String text = selected.toString();
            String id = text.split(",")[0].trim();
            
            for (CampoGara cg : campiGaraList) {
                if (cg.getIdCampoGara().equals(id)) {
                    return cg;
                }
            }
        }
        
        return null;
    }
    
    @FXML
    private void proponiGara() {

        int numProva = numProvaSpinner.getValue();
        Tecnica tecnica = tecnicaBox.getValue();
        String criterio = criterioField.getText().trim();
        LocalDate data = dataPicker.getValue();
        int minPersone = minPersoneSpinner.getValue();
        int maxPersone = maxPersoneSpinner.getValue();
        TipologiaGara tipo = tipologiaBox.getValue();
        
        // Nuovi campi - recupero oggetti dalle liste
        Campionato campionato = getSelectedCampionato();
        Arbitro arbitro = getSelectedArbitro();
        CampoGara campoGara = getSelectedCampoGara();

        if (tecnica == null || criterio.isEmpty() || data == null ||
                tipo == null || campoGara == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "Compilare tutti i campi obbligatori!");
            alert.showAndWait();
            return;
        }
        
        // Controllo: se è stato selezionato un campionato, verificare che non esista già 
        // una gara con lo stesso numero di prova in quel campionato
        if (campionato != null) {
            if (Consultazioni.esisteGaraInCampionato(campionato, numProva)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, 
                    "Esiste già una gara con il numero di prova " + numProva + 
                    " nel campionato " + campionato + "!");
                alert.showAndWait();
                return;
            }
        }

        if (!checkMinMax()) {
            return;
        }

        try {
            // Autori:
            // [0] propositore = società corrente
            // [1] accettatore = null
            PropositoreIF[] autori = new PropositoreIF[2];
            if(Session.userType.equals("Societa")) {
                autori[0] = Consultazioni.getSocieta(Session.userName);
            } else if(Session.userType.equals("Amministratore")) {
            	autori[0] = Consultazioni.getAmministratore(Session.userName);
            }
            autori[1] = null;
            
            String ultimoCodice = Consultazioni.getUltimoCodiceGara();
            int numero = Integer.parseInt(ultimoCodice.substring(1));
            numero++;
            String nuovoCodice = String.format("G%03d", numero);

            Gara g = new Gara();
            g.setCodice(nuovoCodice);
            g.setNumProva(numProva);
            g.setTecnica(tecnica);
            g.setCriterioPunti(criterio);
            g.setData(data);
            g.setMaxPersone(maxPersone);
            g.setMinPersone(minPersone);
            g.setStatoConferma(StatoConferma.IN_ATTESA);
            g.setStatoGara(StatoGara.NON_INIZIATA);
            g.setTipoGara(tipo);
            g.setPropositore(autori[0]);
            g.setCampionato(campionato);
            g.setArbitro(arbitro);
            g.setCampoGara(campoGara);
            
            Consultazioni.insertGara(g);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Gara proposta con successo!");
            alert.showAndWait();
            chiudi();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void chiudi() {
        Stage stage = (Stage) numProvaSpinner.getScene().getWindow();
        stage.close();
    }
}