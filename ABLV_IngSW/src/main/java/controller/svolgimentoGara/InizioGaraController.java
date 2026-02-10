package controller.svolgimentoGara;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Concorrente;
import model.Gara;
import model.RisultatoTurno;
import model.Settore;
import service.PunteggioService;
import service.RicercaService;
import service.ArbitroService;
import state.Session;
import utils.Alerter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

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
    private Settore settoreCorrente;

    // idSettore -> risultati inseriti in quel settore
    private final Map<Integer, List<RisultatoTurno>> risultatiPerSettore = new HashMap<>();

    // idSettore -> set di CF concorrenti che sono stati modificati dall'utente
    private final Map<Integer, Set<String>> modifiedPerSettore = new HashMap<>();

    /* ===================== INIT ===================== */

    @FXML
    public void initialize() {
        garaCorrente = arbitroService.getGara(Session.getCodiceGara());
        turnoCorrente = 1;

        lblTurno.setText(String.valueOf(turnoCorrente));
        setupTipoPunteggio();
        caricaSettori();
    }

    /* ===================== TIPO PUNTEGGIO ===================== */

    private void setupTipoPunteggio() {
        switch (garaCorrente.getCriterioPunti()) {
            case NUM_CATTURE -> lblTipoPunteggio.setText("Numero catture:");
            case PESO -> lblTipoPunteggio.setText("Peso totale:");
            case BIG_ONE -> lblTipoPunteggio.setText("Peso cattura maggiore:");
        }
    }

    /* ===================== SETTORI ===================== */

    private void caricaSettori() {
        List<Settore> settori =
                ricercaSettori.esploraSettori(garaCorrente.getCampoGara());

        settoreComboBox.setItems(FXCollections.observableArrayList(settori));
        settoreComboBox.setOnAction(e -> {
            Settore nuovoSettore = settoreComboBox.getValue();
            salvaRisultatiCorrenti(); // salva i valori del settore attuale
            caricaConcorrentiSettore(nuovoSettore);
        });
    }

    /* ===================== CONCORRENTI ===================== */

    private void caricaConcorrentiSettore(Settore settore) {
        concorrentiVBox.getChildren().clear();
        if (settore == null) return;

        settoreCorrente = settore;
        int idSettore = settore.getIdSettore();

        // assicurati che esista un set per le modifiche di questo settore
        modifiedPerSettore.putIfAbsent(idSettore, new HashSet<>());

        List<Concorrente> concorrenti =
                punteggioService.getConcorrentiSettore(
                        garaCorrente.getCodice(),
                        idSettore
                );

        for (Concorrente c : concorrenti) {
            HBox row = new HBox(15);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setUserData(c);

            Label lblNome = new Label(c.getNome());
            lblNome.setPrefWidth(200);

            Control inputPunteggio;
            Label lblUnita = new Label();

            switch (garaCorrente.getCriterioPunti()) {
                case NUM_CATTURE -> {
                    Spinner<Integer> sp = new Spinner<>(0, 1000, 0, 1);
                    sp.setEditable(true);
                    sp.setPrefWidth(100);
                    enforcePositiveIntegerSpinner(sp);

                    // listener per marcare la modifica reale
                    sp.valueProperty().addListener((obs, oldV, newV) -> markAsModified(idSettore, c.getCf()));

                    inputPunteggio = sp;
                }

                case PESO, BIG_ONE -> {
                    Spinner<Double> sp = new Spinner<>(0.0, 1000.0, 0.0, 0.01);
                    sp.setEditable(true);
                    sp.setPrefWidth(100);
                    enforcePositiveDoubleSpinner(sp);

                    DecimalFormat df = new DecimalFormat("#.00");
                    sp.getValueFactory().setConverter(new StringConverter<>() {
                        @Override
                        public String toString(Double value) {
                            return value == null ? "0.00" : df.format(value);
                        }

                        @Override
                        public Double fromString(String s) {
                            try {
                                return Double.parseDouble(s.replace(",", "."));
                            } catch (Exception e) {
                                return 0.0;
                            }
                        }
                    });

                    // listener per marcare la modifica reale
                    sp.valueProperty().addListener((obs, oldV, newV) -> markAsModified(idSettore, c.getCf()));

                    lblUnita.setText("kg");
                    inputPunteggio = sp;
                }

                default -> throw new IllegalStateException("Criterio non gestito");
            }

            CheckBox chkSqualifica = new CheckBox("Squalifica");
            chkSqualifica.selectedProperty().addListener(
                    (obs, oldV, newV) -> {
                        inputPunteggio.setDisable(newV);
                        markAsModified(idSettore, c.getCf()); // solo modifiche reali
                    }
            );

            // listener sull'editor del spinner (testo)
            if (inputPunteggio instanceof Spinner<?> spEditor) {
                spEditor.getEditor().textProperty().addListener((obs, oldT, newT) -> markAsModified(idSettore, c.getCf()));
            }

            row.getChildren().addAll(lblNome, inputPunteggio, lblUnita, chkSqualifica);
            concorrentiVBox.getChildren().add(row);
        }

        // Ripristina eventuali risultati già salvati per questo settore
        List<RisultatoTurno> salvati = risultatiPerSettore.get(idSettore);
        if (salvati != null) {
            for (Node node : concorrentiVBox.getChildren()) {
                if (!(node instanceof HBox hbox)) continue;
                Concorrente c = (Concorrente) hbox.getUserData();
                RisultatoTurno r = salvati.stream()
                        .filter(rt -> rt.getIdConcorrente().equals(c.getCf()))
                        .findFirst().orElse(null);
                if (r == null) continue;

                Node input = hbox.getChildren().get(1);
                CheckBox chk = (CheckBox) hbox.getChildren().get(3);

                chk.setSelected(r.isSqualificato());
                if (input instanceof Spinner<?> sp) {
                    if (!r.isSqualificato()) {
                        if (sp.getValue() instanceof Integer)
                            ((Spinner<Integer>) sp).getValueFactory().setValue((int) r.getPunteggio());
                        else
                            ((Spinner<Double>) sp).getValueFactory().setValue(r.getPunteggio());
                    }
                    sp.setDisable(r.isSqualificato());
                }

                // ❌ NON marcare come modificato qui! Solo i listener faranno la marcatura
            }
        }
    }


    /* ===================== MARCATURE MODIFICA ===================== */

    private void markAsModified(int idSettore, String cfConcorrente) {
        if (idSettore <= 0 || cfConcorrente == null) return;
        modifiedPerSettore.putIfAbsent(idSettore, new HashSet<>());
        modifiedPerSettore.get(idSettore).add(cfConcorrente);
    }

    /* ===================== SALVA RISULTATI LOCALI ===================== */

    private void salvaRisultatiCorrenti() {
        if (settoreCorrente == null) return;

        int idSettore = settoreCorrente.getIdSettore();
        List<RisultatoTurno> risultati = new ArrayList<>();

        for (Node node : concorrentiVBox.getChildren()) {
            if (!(node instanceof HBox hbox)) continue;

            Concorrente c = (Concorrente) hbox.getUserData();
            if (c == null) continue;

            Node input = hbox.getChildren().get(1);
            CheckBox chk = (CheckBox) hbox.getChildren().get(3);

            boolean squalificato = chk.isSelected();
            double punteggio = 0.0;

            if (!squalificato && input instanceof Spinner<?> sp && sp.getValue() != null) {
                Object val = sp.getValue();
                punteggio = val instanceof Integer i ? i.doubleValue() : (Double) val;
            }

            risultati.add(new RisultatoTurno(c.getCf(), punteggio, squalificato));
        }

        risultatiPerSettore.put(idSettore, risultati);
    }

    /* ===================== SALVA TURNO ===================== */

    @FXML
    private void handleSalvaTurno(ActionEvent event) {
        // salva i risultati del settore visibile
        salvaRisultatiCorrenti();

        // unisci tutti i risultati di tutti i settori
        List<RisultatoTurno> tuttiRisultati = new ArrayList<>();
        risultatiPerSettore.values().forEach(tuttiRisultati::addAll);

        // controlla se ci sono concorrenti non modificati
        boolean ciSonoNonToccati = false;

        for (Map.Entry<Integer, List<RisultatoTurno>> entry : risultatiPerSettore.entrySet()) {
            int idSettore = entry.getKey();
            Set<String> modified = modifiedPerSettore.getOrDefault(idSettore, Collections.emptySet());

            for (RisultatoTurno r : entry.getValue()) {
                // se il CF non è presente nel set "modified", allora è non toccato
                if (!modified.contains(r.getIdConcorrente())) {
                    ciSonoNonToccati = true;
                    break;
                }
            }
            if (ciSonoNonToccati) break;
        }

        // mostra alert solo se ci sono concorrenti non toccati
        if (ciSonoNonToccati) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Valori non inseriti");
            alert.setHeaderText("Alcuni concorrenti non sono stati modificati.");
            alert.setContentText(
                "Vuoi continuare comunque e considerarli con valore predefinito/squalificati?\n" +
                "Scegli 'Annulla' per tornare a modificare."
            );
            ButtonType continua = new ButtonType("Continua comunque");
            ButtonType annulla = new ButtonType("Annulla");
            alert.getButtonTypes().setAll(continua, annulla);

            Optional<ButtonType> risultato = alert.showAndWait();
            if (risultato.isEmpty() || risultato.get() == annulla) {
                return; // l'utente vuole modificare ancora
            }

            // ---------- NUOVA LOGICA ----------
            // Imposta punteggio 0 e squalifica per tutti i non modificati
            for (Map.Entry<Integer, List<RisultatoTurno>> entry : risultatiPerSettore.entrySet()) {
                int idSettore = entry.getKey();
                Set<String> modified = modifiedPerSettore.getOrDefault(idSettore, Collections.emptySet());
                for (RisultatoTurno r : entry.getValue()) {
                    if (!modified.contains(r.getIdConcorrente())) {
                        r.azzeraPunti();
                        r.setSqualificato();
                    }
                }
            }

            // Aggiorna subito la UI per mostrare gli azzeramenti
            for (Node node : concorrentiVBox.getChildren()) {
                if (!(node instanceof HBox hbox)) continue;
                Concorrente c = (Concorrente) hbox.getUserData();
                RisultatoTurno r = risultatiPerSettore.get(settoreCorrente.getIdSettore()).stream()
                        .filter(rt -> rt.getIdConcorrente().equals(c.getCf()))
                        .findFirst().orElse(null);
                if (r == null) continue;

                Node input = hbox.getChildren().get(1);
                CheckBox chk = (CheckBox) hbox.getChildren().get(3);

                chk.setSelected(r.isSqualificato());
                if (input instanceof Spinner<?> sp) {
                    sp.setDisable(r.isSqualificato());
                    if (!r.isSqualificato()) {
                        if (sp.getValue() instanceof Integer)
                            ((Spinner<Integer>) sp).getValueFactory().setValue((int) r.getPunteggio());
                        else
                            ((Spinner<Double>) sp).getValueFactory().setValue(r.getPunteggio());
                    } else {
                        if (sp.getValue() instanceof Integer)
                            ((Spinner<Integer>) sp).getValueFactory().setValue(0);
                        else
                            ((Spinner<Double>) sp).getValueFactory().setValue(0.0);
                    }
                }
            }
        }


        try {
            // invia tutti i risultati al servizio
            arbitroService.salvaTurno(
                    garaCorrente.getCodice(),
                    turnoCorrente,
                    tuttiRisultati
            );

            // dopo il salvataggio reale, svuota le strutture locali e incrementa turno
            risultatiPerSettore.clear();
            modifiedPerSettore.clear();

            turnoCorrente++;
            
           
            
            lblTurno.setText(String.valueOf(turnoCorrente));
            Alerter.showSuccess("Turno salvato correttamente");
            System.out.println("turno corrente: " + turnoCorrente);
            System.out.println("turni totali: " + this.arbitroService.getNumTurni(garaCorrente.getCodice()));
            if(turnoCorrente > this.arbitroService.getNumTurni(garaCorrente.getCodice())) {
            	buttonEsci(event);
            }

        } catch (Exception e) {
            Alerter.showError("Errore nel salvataggio del turno");
            e.printStackTrace();
        }
    }
    
    private void buttonEsci(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/arbitroHome.fxml"));
			Scene homeScene = new Scene(loader.load());
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(homeScene);
			stage.setTitle("Orobic Fishing Race");
		} catch(IOException e) {
			Alerter.showError(e.getMessage());
		}
    }


    /* ===================== VALIDAZIONE SPINNER ===================== */

    private void enforcePositiveIntegerSpinner(Spinner<Integer> spinner) {
        TextFormatter<Integer> formatter = new TextFormatter<>(change -> {
            String text = change.getControlNewText();
            if (text.isEmpty()) return change;
            return text.matches("\\d+") ? change : null;
        });

        spinner.getEditor().setTextFormatter(formatter);

        spinner.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) {
                try {
                    int value = Integer.parseInt(spinner.getEditor().getText());
                    spinner.getValueFactory().setValue(Math.max(0, value));
                } catch (Exception e) {
                    spinner.getValueFactory().setValue(0);
                }
            }
        });
    }

    private void enforcePositiveDoubleSpinner(Spinner<Double> spinner) {
        TextFormatter<Double> formatter = new TextFormatter<>(change -> {
            String text = change.getControlNewText();
            if (text.isEmpty()) return change;
            return text.matches("\\d*(\\.|,)?\\d*") ? change : null;
        });

        spinner.getEditor().setTextFormatter(formatter);

        spinner.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) {
                try {
                    double value = Double.parseDouble(
                            spinner.getEditor().getText().replace(",", ".")
                    );
                    spinner.getValueFactory().setValue(Math.max(0.0, value));
                } catch (Exception e) {
                    spinner.getValueFactory().setValue(0.0);
                }
            }
        });
    }
}
