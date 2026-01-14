package client;

import database.Consultazioni;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import applicazione.*;

import java.time.LocalDate;

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
    @FXML
    private ComboBox<StatoGara> statoGaraBox = new ComboBox<StatoGara>();
    @FXML
    private ComboBox<StatoConferma> statoConfermaBox = new ComboBox<StatoConferma>();

    @FXML
    private TextField criterioField = new TextField();
    @FXML
    private DatePicker dataPicker = new DatePicker();

    @FXML
    private Button proponiButton;
    
    private boolean isAdjusting = false;
    
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
        statoGaraBox.getItems().setAll(StatoGara.values());
        statoConfermaBox.getItems().setAll(StatoConferma.values());

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
    
    @FXML
    private void proponiGara() {

        int numProva = numProvaSpinner.getValue();
        Tecnica tecnica = tecnicaBox.getValue();
        String criterio = criterioField.getText().trim();
        LocalDate data = dataPicker.getValue();
        int minPersone = minPersoneSpinner.getValue();
        int maxPersone = maxPersoneSpinner.getValue();
        TipologiaGara tipo = tipologiaBox.getValue();
        StatoGara statoGara = statoGaraBox.getValue();
        StatoConferma statoConferma = statoConfermaBox.getValue();

        if (tecnica == null || criterio.isEmpty() || data == null ||
                tipo == null || statoGara == null || statoConferma == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "Compilare tutti i campi!");
            alert.showAndWait();
            return;
        }

        if (!checkMinMax()) {
            return;
        }

        try {
            // Autori:
            // [0] propositore = società corrente
            // [1] accettatore = null
            PropositoreIF[] autori = new PropositoreIF[2];
            autori[0] = Consultazioni.getSocieta(Session.userName);
            autori[1] = null;
            
            String ultimoCodice = Consultazioni.getUltimoCodiceGara();
            int numero = Integer.parseInt(ultimoCodice.substring(1));
            numero++;
            String nuovoCodice = String.format("G%03d", numero);

            Gara g = new Gara(nuovoCodice, numProva, tecnica, criterio, data, maxPersone, minPersone,
            		statoConferma, statoGara, tipo, autori[0]/*, autori*/);
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
