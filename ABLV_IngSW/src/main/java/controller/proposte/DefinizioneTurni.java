package controller.proposte;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.ProponiGaraService;
import service.SalvataggioTurniService;
import state.Session;

import java.util.ArrayList;
import java.util.List;

public class DefinizioneTurni {

    @FXML
    private Spinner<Integer> durataSpinner;

    @FXML
    private VBox listaTurniBox;

    @FXML
    private Label turnoLabel;

    private int numeroTurno = 1;
    private final List<Integer> durateTurni = new ArrayList<>();
    private SalvataggioTurniService settTurni;
    private ProponiGaraService garaproposta;
    
    @FXML
    public void initialize() {
        durataSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 600, 10)
        );
    }

    @FXML
    private void aggiungiTurno() {
        int durata = durataSpinner.getValue();
        durateTurni.add(durata);

        Label riga = new Label("Turno " + numeroTurno + ": " + durata + " minuti");
        riga.setStyle("-fx-text-fill: #004d4d;");
        listaTurniBox.getChildren().add(riga);

        numeroTurno++;

        aggiornaLabelTurno();
        durataSpinner.getValueFactory().setValue(10);
    }

    @FXML
    private void terminaInserimento() {

        System.out.println("Durate turni inserite:");
        for (int i = 0; i < durateTurni.size(); i++) {
            System.out.println("Turno " + (i + 1) + ": " + durateTurni.get(i) + " min");
        }
        garaproposta = new ProponiGaraService();
        settTurni = new SalvataggioTurniService();
        settTurni.insertTurni(durateTurni, garaproposta.getUltimoCodiceGara());
        
        chiudi();
    }

    private void aggiornaLabelTurno() {
        switch (numeroTurno) {
            case 1 -> turnoLabel.setText("Durata primo turno (minuti):");
            case 2 -> turnoLabel.setText("Durata secondo turno (minuti):");
            case 3 -> turnoLabel.setText("Durata terzo turno (minuti):");
            default -> turnoLabel.setText("Durata turno " + numeroTurno + " (minuti):");
        }
    }

    public List<Integer> getDurateTurni() {
        return durateTurni;
    }
    
    @FXML
    private void chiudi() {
        Stage stage = (Stage) durataSpinner.getScene().getWindow();
        stage.close();
    }
}

