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
import service.ConcorrenteService;
import service.ModificaService;
import service.SocietaService;
import service.exception.ConcorrenteHomeEccezione;
import service.exception.ModificaEccezione;
import service.exception.SocietaHomeEccezione;
import state.Session;
import utils.Alerter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Controller per la gestione della modifica dei dati personali
 * Supporta sia Concorrenti che Società
 */
public class ModificaDatiController {

    // Label e messaggi
    @FXML private Label titleLabel;
    @FXML private Label messageLabel;

    // Sezioni
    @FXML private VBox concorrenteSection;
    @FXML private VBox societaSection;

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
    
    private String userType;
    private final ConcorrenteService concorrenteService = new ConcorrenteService();
    private final SocietaService societaService = new SocietaService();
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
        if (userType.equalsIgnoreCase("CONCORRENTE")) {
            inizializzaConcorrente();
        } else if (userType.equalsIgnoreCase("SOCIETA")) {
            inizializzaSocieta();
        } else {
            Alerter.showError("Tipo di utente non riconosciuto!");
        }
    }

    /**
     * Inizializza la vista per un concorrente
     */
    private void inizializzaConcorrente() {
        titleLabel.setText("Modifica Dati Concorrente");
        
        // Mostra solo la sezione concorrente
        concorrenteSection.setManaged(true);
        concorrenteSection.setVisible(true);
        societaSection.setManaged(false);
        societaSection.setVisible(false);

        // Carica i dati attuali del concorrente
        caricaDatiConcorrente();
    }

    /**
     * Inizializza la vista per una società
     */
    private void inizializzaSocieta() {
        titleLabel.setText("Modifica Dati Società");
        
        // Mostra solo la sezione società
        societaSection.setManaged(true);
        societaSection.setVisible(true);
        concorrenteSection.setManaged(false);
        concorrenteSection.setVisible(false);

        // Carica i dati attuali della società
        caricaDatiSocieta();
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
     * Gestisce il salvataggio delle modifiche
     */
    @FXML
    private void salvaModifiche() {
        if (userType.equalsIgnoreCase("CONCORRENTE")) {
            salvaModificheConcorrente();
        } else if (userType.equalsIgnoreCase("SOCIETA")) {
            salvaModificheSocieta();
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
            String cfNuovo = cfField.getText().trim();
            LocalDate dataNascita = nascitaDatePicker.getValue();

            if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || cfNuovo.isEmpty() || dataNascita == null) {
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

            // Validazione CF (16 caratteri alfanumerici nel formato standard italiano)
            if (!isValidCF(cfNuovo)) {
                Alerter.showError("Il codice fiscale non è valido");
                return;
            }

            try{
            	this.modificaService.salvaConcorrente(Session.getUserName(),/* cfNuovo, */nome, cognome, email, dataNascita);

                Alerter.showSuccess("Dati aggiornati con successo!");
                // Aggiorno il nome nella sessione
                Session.setUserName(cfNuovo);
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
            String nomeNuovo = nomeSocietaField.getText().trim();
            String email = emailSocietaField.getText().trim();
            String cap = capField.getText().trim();
            String citta = cittaField.getText().trim();
            String indirizzo = indirizzoField.getText().trim();

            if (nomeNuovo.isEmpty() || email.isEmpty() || cap.isEmpty() || citta.isEmpty() || indirizzo.isEmpty()) {
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
            	this.modificaService.salvaSocieta(Session.getUserName(),/* nomeNuovo,*/ email, cap, citta, indirizzo);
            	
            	Alerter.showSuccess("Dati aggiornati con successo!");
                // Aggiorno il nome nella sessione
                Session.setUserName(nomeNuovo);
                this.annulla(null);
            } catch(ModificaEccezione e) {
            	throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            Alerter.showError("Errore: " + e.getMessage());
            e.printStackTrace();
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
            
            String percorso = userType.equalsIgnoreCase("CONCORRENTE")
                    ? "/view/fxml/concorrenteHome.fxml"
                    : "/view/fxml/societaHome.fxml";

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
        
        // 1. Prova tramite un campo che sicuramente esiste nella scena
        if (concorrenteSection != null && concorrenteSection.getScene() != null) {
            stage = (Stage) concorrenteSection.getScene().getWindow();
        } 
        // 2. Altrimenti prova con societaSection
        else if (societaSection != null && societaSection.getScene() != null) {
            stage = (Stage) societaSection.getScene().getWindow();
        }
        // 3. Altrimenti prova con titleLabel
        else if (titleLabel != null && titleLabel.getScene() != null) {
            stage = (Stage) titleLabel.getScene().getWindow();
        }
        // 4. Ultima risorsa: cerca tra tutti i nodi della scena
        else {
            // Cerca qualsiasi nodo che abbia una scena
            for (Node node : Arrays.asList(concorrenteSection, societaSection, titleLabel)) {
                if (node != null && node.getScene() != null) {
                    stage = (Stage) node.getScene().getWindow();
                    break;
                }
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

    /**
     * Valida il formato del codice fiscale italiano
     * Formato: 6 lettere | 2 cifre | 1 lettera | 2 cifre | 1 lettera | 3 cifre | 1 lettera
     */
    private boolean isValidCF(String cf) {
        String cfRegex = "^[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]$";
        return cf.matches(cfRegex);
    }
}