package controller.svolgimentoGara;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import model.Gara;
import model.Concorrente;
import service.ArbitroService;
import service.IscrizioneService;
import service.PunteggioService;
import state.Session;
import utils.Alerter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SelezionaGaraController {

    @FXML private Label lblDataOggi;
    @FXML private ComboBox<Gara> garaComboBox;

    @FXML private ListView<CheckBox> listConcorrenti;
    @FXML private Button btnAvviaGara;

    private final ArbitroService arbitroService = new ArbitroService();
    private final IscrizioneService iscrizioniService = new IscrizioneService();
    private final PunteggioService punteggioService = new PunteggioService();

    private ObservableList<Gara> gareOggi;
    private ObservableList<Concorrente> concorrentiIscritti;

    @FXML
    public void initialize() {
        lblDataOggi.setText(LocalDate.now().toString());
        btnAvviaGara.setDisable(true);

        caricaGareOggi();
        setupGaraConverter();
    }

    /* ---------- GARE DI OGGI ---------- */
    private void caricaGareOggi() {
        try {
            String cfArbitro = Session.getUserName();

            List<Gara> gare = arbitroService.getGareDiArbitro(cfArbitro)
                    .stream()
                    .filter(g -> LocalDate.now().equals(g.getData()))
                    .collect(Collectors.toList());

            gareOggi = FXCollections.observableArrayList(gare);
            garaComboBox.setItems(gareOggi);

        } catch (Exception e) {
            Alerter.showError("Errore nel caricamento delle gare di oggi");
        }
    }

    private void setupGaraConverter() {
        garaComboBox.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Gara g) {
                if (g == null) return "";
                return g.getCodice() + " - " + g.getCampoGara().getIdCampoGara();
            }

            @Override
            public Gara fromString(String s) {
                return null;
            }
        });
    }

    /* ---------- SELEZIONE GARA ---------- */
    @FXML
    private void onGaraSelected() {
        Gara gara = garaComboBox.getValue();
        if (gara == null) return;

        caricaConcorrenti(gara);
        btnAvviaGara.setDisable(false);
    }

    /* ---------- CONCORRENTI ISCRITTI ---------- */
    private void caricaConcorrenti(Gara gara) {
        try {
            List<Concorrente> lista = this.iscrizioniService.getIscrizioniGara(gara.getCodice());

            concorrentiIscritti = FXCollections.observableArrayList(lista);
            listConcorrenti.getItems().clear();

            for (Concorrente c : concorrentiIscritti) {
                CheckBox cb = new CheckBox(
                        c.getNome() + " " + c.getCognome()
                );
                cb.setUserData(c); // riferimento al concorrente
                listConcorrenti.getItems().add(cb);
            }

        } catch (Exception e) {
            Alerter.showError("Errore nel caricamento concorrenti");
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/fxml/arbitroHome.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alerter.showError("Errore nel ritorno al menu arbitro");
        }
    }


    /* ---------- AVVIO GARA ---------- */
    @FXML
    private void handleAvviaGara() {
        Gara gara = garaComboBox.getValue();
        if (gara == null) {
            Alerter.showError("Seleziona una gara");
            return;
        }

        // concorrenti ASSENTI
        List<Concorrente> assenti = listConcorrenti.getItems().stream()
                .filter(CheckBox::isSelected)
                .map(cb -> (Concorrente) cb.getUserData())
                .collect(Collectors.toList());

        try {
        	System.out.println("ciao");
            punteggioService.assegnazioneGruppi(gara.getCodice(), assenti);
        } catch (Exception e) {
            System.out.println(("Errore durante l'appello " + e.getMessage()));
            return;
        }
        
        

        // 👉 CAMBIO SCHERMATA
        // definizione gruppi + gara
        // FXMLLoader verso "avvioGara.fxml"

        Alerter.showSuccess("Appello completato. Avvio gara...");
    }
}
