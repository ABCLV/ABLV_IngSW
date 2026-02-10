package controller.update;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Concorrente;
import model.Societa;
import model.Arbitro;
import model.Amministratore;
import service.ConcorrenteService;
import service.ModificaService;
import service.SocietaService;
import service.ArbitroService;
import service.AmministratoreService;
import service.exception.ConcorrenteHomeEccezione;
import service.exception.ModificaEccezione;
import service.exception.SocietaHomeEccezione;
import service.exception.ArbitroHomeEccezione;
import service.exception.AmministratoreHomeEccezione;
import state.Session;
import utils.Alerter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Controller per la gestione della modifica dei dati personali
 * Supporta Concorrenti, Società, Arbitri e Amministratori
 */
public class ModificaDatiController {

    // Label e messaggi
    @FXML private Label titleLabel;
    @FXML private Label messageLabel;

    // Sezioni
    @FXML private VBox concorrenteSection;
    @FXML private VBox societaSection;
    @FXML private VBox arbitroSection;
    @FXML private VBox amministratoreSection;

    // Campi Concorrente
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private TextField emailConcorrenteField;
    @FXML private DatePicker nascitaDatePicker;
    @FXML private TextField cfField;

    // Campi Società
    @FXML private TextField nomeSocietaField;
    @FXML private TextField emailSocietaField;
    @FXML private TextField capField;
    @FXML private TextField cittaField;
    @FXML private TextField indirizzoField;
    
    // Campi Arbitro
    @FXML private TextField cfArbitroField;
    @FXML private TextField nomeArbitroField;
    @FXML private TextField cognomeArbitroField;
    @FXML private TextField sezioneField;
    
    // Campi Amministratore
    @FXML private TextField cfAmministratoreField;
    @FXML private TextField nomeAmministratoreField;
    @FXML private TextField cognomeAmministratoreField;
    
    private String userType;
    private final ConcorrenteService concorrenteService = new ConcorrenteService();
    private final SocietaService societaService = new SocietaService();
    private final ArbitroService arbitroService = new ArbitroService();
    private final AmministratoreService amministratoreService = new AmministratoreService();
    private final ModificaService modificaService = new ModificaService();
    
    /**
     * Inizializzazione del controller
     * Determina il tipo di utente e mostra i campi appropriati
     */
    @FXML
    public void initialize() {
        // Ottieni il tipo di utente dalla sessione
        userType = Session.getUserType();
        
        if (userType == null) {
            Alerter.showError("Errore: utente non autenticato");
            return;
        }

        // Mostra i campi appropriati in base al tipo di utente
        switch (userType.toUpperCase()) {
            case "CONCORRENTE":
                inizializzaConcorrente();
                break;
            case "SOCIETA":
                inizializzaSocieta();
                break;
            case "ARBITRO":
                inizializzaArbitro();
                break;
            case "AMMINISTRATORE":
                inizializzaAmministratore();
                break;
            default:
                Alerter.showError("Tipo di utente non riconosciuto!");
                break;
        }
    }

    /**
     * Inizializza la vista per un concorrente
     */
    private void inizializzaConcorrente() {
        titleLabel.setText("Modifica Dati Concorrente");
        mostraSezione(concorrenteSection);
        caricaDatiConcorrente();
    }

    /**
     * Inizializza la vista per una società
     */
    private void inizializzaSocieta() {
        titleLabel.setText("Modifica Dati Società");
        mostraSezione(societaSection);
        caricaDatiSocieta();
    }

    /**
     * Inizializza la vista per un arbitro
     */
    private void inizializzaArbitro() {
        titleLabel.setText("Modifica Dati Arbitro");
        mostraSezione(arbitroSection);
        caricaDatiArbitro();
    }

    /**
     * Inizializza la vista per un amministratore
     */
    private void inizializzaAmministratore() {
        titleLabel.setText("Modifica Dati Amministratore");
        mostraSezione(amministratoreSection);
        caricaDatiAmministratore();
    }

    /**
     * Mostra solo la sezione specificata, nascondendo le altre
     */
    private void mostraSezione(VBox sezioneAttiva) {
        VBox[] sezioni = {concorrenteSection, societaSection, arbitroSection, amministratoreSection};
        
        for (VBox sezione : sezioni) {
            if (sezione != null) {
                boolean isAttiva = sezione == sezioneAttiva;
                sezione.setManaged(isAttiva);
                sezione.setVisible(isAttiva);
            }
        }
    }

    /**
     * Carica i dati attuali del concorrente nei campi
     */
    private void caricaDatiConcorrente() {
        try {
            Concorrente concorrente = this.concorrenteService.getConcorrente(Session.getUserName());
            
            nomeField.setText(concorrente.getNome() != null ? concorrente.getNome() : "");
            cognomeField.setText(concorrente.getCognome() != null ? concorrente.getCognome(): "");
            emailConcorrenteField.setText(concorrente.getEmail() != null ? concorrente.getEmail(): "");
            cfField.setText(concorrente.getCf() != null ? concorrente.getCf() : "");
            nascitaDatePicker.setValue(concorrente.getNascita());
        } catch(ConcorrenteHomeEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }

    /**
     * Carica i dati attuali della società nei campi
     */
    private void caricaDatiSocieta() {
        try {
            Societa societa = this.societaService.getSocieta(Session.getUserName());

            nomeSocietaField.setText(societa.getNome() != null ? societa.getNome() : "");
            emailSocietaField.setText(societa.getEmail() != null ? societa.getEmail() : "");
            capField.setText(societa.getCap() != null ? societa.getCap(): "");
            cittaField.setText(societa.getCitta() != null ? societa.getCitta() : "");
            indirizzoField.setText(societa.getIndirizzo() != null ? societa.getIndirizzo() : "");
        } catch (SocietaHomeEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }

    /**
     * Carica i dati attuali dell'arbitro nei campi
     */
    private void caricaDatiArbitro() {
        try {
            Arbitro arbitro = this.arbitroService.getArbitro(Session.getUserName());
            
            cfArbitroField.setText(arbitro.getCfArbitro() != null ? arbitro.getCfArbitro() : "");
            nomeArbitroField.setText(arbitro.getNome() != null ? arbitro.getNome() : "");
            cognomeArbitroField.setText(arbitro.getCognome() != null ? arbitro.getCognome() : "");
            sezioneField.setText(arbitro.getSezione() != null ? arbitro.getSezione() : "");
        } catch (ArbitroHomeEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }

    /**
     * Carica i dati attuali dell'amministratore nei campi
     */
    private void caricaDatiAmministratore() {
        try {
            Amministratore amministratore = this.amministratoreService.getAmministratore(Session.getUserName());
            
            cfAmministratoreField.setText(amministratore.getCfAmministratore() != null ? amministratore.getCfAmministratore() : "");
            nomeAmministratoreField.setText(amministratore.getNome() != null ? amministratore.getNome() : "");
            cognomeAmministratoreField.setText(amministratore.getCognome() != null ? amministratore.getCognome() : "");
        } catch (AmministratoreHomeEccezione e) {
            Alerter.showError(e.getMessage());
        }
    }

    /**
     * Gestisce il salvataggio delle modifiche
     */
    @FXML
    private void salvaModifiche() {
        switch (userType.toUpperCase()) {
            case "CONCORRENTE":
                salvaModificheConcorrente();
                break;
            case "SOCIETA":
                salvaModificheSocieta();
                break;
            case "ARBITRO":
                salvaModificheArbitro();
                break;
            case "AMMINISTRATORE":
                salvaModificheAmministratore();
                break;
        }
    }

    /**
     * Salva le modifiche per un concorrente
     */
    private void salvaModificheConcorrente() {
        try {
            // Validazione dei campi
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String email = emailConcorrenteField.getText().trim();
            LocalDate dataNascita = nascitaDatePicker.getValue();

            if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || dataNascita == null) {
                Alerter.showError("Tutti i campi sono obbligatori");
                return;
            }

            // Validazione email
            if (!isValidEmail(email)) {
                Alerter.showError("Formato email non valido");
                return;
            }

            // Validazione data di nascita (deve essere nel passato)
            if (dataNascita.isAfter(LocalDate.now())) {
                Alerter.showError("La data di nascita deve essere nel passato");
                return;
            }

            try {
                this.modificaService.salvaConcorrente(Session.getUserName(), nome, cognome, email, dataNascita);
                Alerter.showSuccess("Dati aggiornati con successo!");
                this.annulla(null);
            } catch (ModificaEccezione e) {
                throw new Exception(e.getMessage());
            }
        } catch(Exception e) {
            Alerter.showError(e.getMessage());
        }
    }

    /**
     * Salva le modifiche per una società
     */
    private void salvaModificheSocieta() {
        try {
            // Validazione dei campi
            String email = emailSocietaField.getText().trim();
            String cap = capField.getText().trim();
            String citta = cittaField.getText().trim();
            String indirizzo = indirizzoField.getText().trim();

            if (email.isEmpty() || cap.isEmpty() || citta.isEmpty() || indirizzo.isEmpty()) {
                Alerter.showError("Tutti i campi sono obbligatori");
                return;
            }

            // Validazione email
            if (!isValidEmail(email)) {
                Alerter.showError("Formato email non valido");
                return;
            }

            // Validazione CAP (5 cifre per Italia)
            if (!cap.matches("\\d{5}")) {
                Alerter.showError("Il CAP deve essere composto da 5 cifre");
                return;
            }
            
            try {
                this.modificaService.salvaSocieta(Session.getUserName(), email, cap, citta, indirizzo);
                Alerter.showSuccess("Dati aggiornati con successo!");
                this.annulla(null);
            } catch(ModificaEccezione e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            Alerter.showError("Errore: " + e.getMessage());
        }
    }

    /**
     * Salva le modifiche per un arbitro
     */
    private void salvaModificheArbitro() {
        try {
            // Validazione dei campi
            String nome = nomeArbitroField.getText().trim();
            String cognome = cognomeArbitroField.getText().trim();
            String sezione = sezioneField.getText().trim();

            if (nome.isEmpty() || cognome.isEmpty() || sezione.isEmpty()) {
                Alerter.showError("Tutti i campi sono obbligatori");
                return;
            }

            try {
                this.modificaService.salvaArbitro(Session.getUserName(), nome, cognome, sezione);
                Alerter.showSuccess("Dati aggiornati con successo!");
                this.annulla(null);
            } catch (ModificaEccezione e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            Alerter.showError(e.getMessage());
        }
    }

    /**
     * Salva le modifiche per un amministratore
     */
    private void salvaModificheAmministratore() {
        try {
            // Validazione dei campi
            String nome = nomeAmministratoreField.getText().trim();
            String cognome = cognomeAmministratoreField.getText().trim();

            if (nome.isEmpty() || cognome.isEmpty()) {
                Alerter.showError("Tutti i campi sono obbligatori");
                return;
            }

            try {
                this.modificaService.salvaAmministratore(Session.getUserName(), nome, cognome);
                Alerter.showSuccess("Dati aggiornati con successo!");
                this.annulla(null);
            } catch (ModificaEccezione e) {
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            Alerter.showError(e.getMessage());
        }
    }

    /**
     * Gestisce il click sul pulsante Annulla
     */
    @FXML
    private void annulla(ActionEvent event) {
        tornaAllaHome();
    }

    /**
     * Metodo di fallback se chiamato senza ActionEvent
     */
    @FXML
    private void handleAnnulla() {
        tornaAllaHome();
    }

    /**
     * Metodo comune per tornare alla home
     */
    private void tornaAllaHome() {
        try {
            // Ottieni la finestra corrente in modo sicuro
            Stage currentStage = getCurrentStage();
            
            String percorso;
            switch (userType.toUpperCase()) {
                case "CONCORRENTE":
                    percorso = "/view/fxml/concorrenteHome.fxml";
                    break;
                case "SOCIETA":
                    percorso = "/view/fxml/societaHome.fxml";
                    break;
                case "ARBITRO":
                    percorso = "/view/fxml/arbitroHome.fxml";
                    break;
                case "AMMINISTRATORE":
                    percorso = "/view/fxml/amministratoreHome.fxml";
                    break;
                default:
                    Alerter.showError("Tipo di utente non riconosciuto");
                    return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(percorso));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alerter.showError("Errore nel caricamento della pagina home");
        }
    }

    /**
     * Ottiene la Stage corrente in modo sicuro
     */
    private Stage getCurrentStage() {
        // Prova diversi modi per ottenere la finestra corrente
        Stage stage = null;
        
        // Array di tutti i nodi che potrebbero avere una scena
        Node[] nodes = {
            concorrenteSection, societaSection, arbitroSection, amministratoreSection, titleLabel
        };
        
        for (Node node : nodes) {
            if (node != null && node.getScene() != null) {
                stage = (Stage) node.getScene().getWindow();
                break;
            }
        }
        
        return stage;
    }

    /**
     * Valida il formato dell'email
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}
