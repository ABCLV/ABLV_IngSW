package controller.svolgimentoGara;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Concorrente;
import model.Gara;
import model.Settore;
import service.PunteggioService;
import service.RicercaService;
import service.ArbitroService;
import state.Session;
import utils.Alerter;

import java.util.List;

public class InizioGaraController {

    @FXML private Label lblTurno;
    @FXML private Label lblTipoPunteggio;

    @FXML private ComboBox<Settore> settoreComboBox;
    @FXML private VBox concorrentiVBox;

    private final PunteggioService punteggioService = new PunteggioService();
    private final ArbitroService arbitroService = new ArbitroService();
    private final RicercaService ricercaSettori = new RicercaService();

    private Gara garaCorrente;
    private int turnoCorrente;

    @FXML
    public void initialize() {
        // Recupero gara dalla sessione
        garaCorrente = arbitroService.getGara(Session.getCodiceGara());
        turnoCorrente = 1;

        lblTurno.setText(String.valueOf(turnoCorrente));
        setupTipoPunteggio();
        caricaSettori();
    }

    /* ---------- TIPO PUNTEGGIO ---------- */
    private void setupTipoPunteggio() {
        switch (garaCorrente.getCriterioPunti()) {
            case NUM_CATTURE -> lblTipoPunteggio.setText("Numero catture:");
            case PESO -> lblTipoPunteggio.setText("Peso totale (kg):");
            case BIG_ONE -> lblTipoPunteggio.setText("Peso cattura maggiore (kg):");
        }
    }

    /* ---------- CARICA SETTORI ---------- */
    private void caricaSettori() {
        List<Settore> settori = ricercaSettori.esploraSettori(garaCorrente.getCampoGara());
        settoreComboBox.setItems(FXCollections.observableArrayList(settori));

        settoreComboBox.setOnAction(e -> caricaConcorrentiSettore(settoreComboBox.getValue()));
    }

    /* ---------- CARICA CONCORRENTI DEL SETTORE ---------- */
    private void caricaConcorrentiSettore(Settore settore) {
        concorrentiVBox.getChildren().clear();
        if (settore == null) return;

        List<Concorrente> concorrenti =
                punteggioService.getConcorrentiSettore(garaCorrente.getCodice(), settore.getIdSettore());

        for (Concorrente c : concorrenti) {
            HBox row = new HBox(15);
            row.setAlignment(Pos.CENTER_LEFT);

            Label lblNome = new Label(c.getNome());
            lblNome.setPrefWidth(200);

            Spinner<Double> spPunteggio = new Spinner<>(0, 1000, 0, 0.1);
            spPunteggio.setEditable(true);
            spPunteggio.setPrefWidth(100);

            CheckBox chkSqualifica = new CheckBox("Squalifica");

            row.getChildren().addAll(lblNome, spPunteggio, chkSqualifica);
            concorrentiVBox.getChildren().add(row);

            // Se vuoi, puoi associare spinner e checkbox all’oggetto concorrente
            //c.setSpinnerPunteggio(spPunteggio);
            //c.setCheckBoxSqualifica(chkSqualifica);
        }
    }

    /* ---------- SALVA PUNTEGGI TURNO ---------- */
    @FXML
    private void handleSalvaTurno() {
        boolean tuttiCompilati = true;

        // Controllo che tutti abbiano punteggio o siano squalificati
        for (Node node : concorrentiVBox.getChildren()) {
            if (node instanceof HBox hbox) {
                Spinner<Double> sp = (Spinner<Double>) hbox.getChildren().get(1);
                CheckBox chk = (CheckBox) hbox.getChildren().get(2);

                if (!chk.isSelected() && sp.getValue() == null) {
                    tuttiCompilati = false;
                    break;
                }
            }
        }

        if (!tuttiCompilati) {
            Alerter.showError("Compila tutti i punteggi o marca i concorrenti squalificati.");
            return;
        }
        /*
        // Salvataggio punteggi
        for (Node node : concorrentiVBox.getChildren()) {
            if (node instanceof HBox hbox) {
                Label lbl = (Label) hbox.getChildren().get(0);
                Spinner<Double> sp = (Spinner<Double>) hbox.getChildren().get(1);
                CheckBox chk = (CheckBox) hbox.getChildren().get(2);

                Concorrente c = ricercaConcorrenteDaNome(lbl.getText());
                double punteggio = sp.getValue();
                boolean squalifica = chk.isSelected();

                try {
                    punteggioService.salvaPunteggio(
                            garaCorrente.getCodice(),
                            turnoCorrente,
                            c.getIdConcorrente(),
                            punteggio,
                            squalifica
                    );
                } catch (Exception e) {
                    Alerter.showError("Errore nel salvataggio del punteggio di " + c.getNome());
                }
            }
        }
	*/
        turnoCorrente++;
        lblTurno.setText(String.valueOf(turnoCorrente));
        Alerter.showSuccess("Turno salvato correttamente");
    }

   
}
