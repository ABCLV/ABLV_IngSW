package applicazione;

/**
 * Rappresenta una società di pesca partecipante alle gare.
 */
public class Società {

    private String nome;

    /**
     * Costruttore completo.
     * @param nome nome della società
     */
    public Società(String nome) {
        this.nome = nome;
    }

    /**
     * Iscrive un gruppo di concorrenti a una gara.
     * @param concorrenti array dei concorrenti da iscrivere
     * @param codiceGara  codice identificativo della gara
     * @return true se l'iscrizione è andata a buon fine, false altrimenti
     */
    public boolean iscrizioneGaraGruppi(Concorrente[] concorrenti, String codiceGara) {
        return false;
    }

    /**
     * Iscrive un singolo concorrente a una gara.
     * @param concorrente concorrente da iscrivere
     * @param codiceGara  codice identificativo della gara
     * @return true se l'iscrizione è andata a buon fine, false altrimenti
     */
    public boolean iscrizioneGaraSingolo(Concorrente concorrente, String codiceGara) {
        return false;
    }

    /**
     * Mostra il profilo della società.
     */
    public void getProfilo() {
    }

    /**
     * Aggiunge un nuovo socio alla società.
     * @param concorrente concorrente da aggiungere
     */
    public void aggiungiSocio(Concorrente concorrente) {
    }

    /**
     * Rimuove un socio dalla società.
     * @param concorrente concorrente da rimuovere
     */
    public void abbandonoSocio(Concorrente concorrente) {
    }

    /**
     * Permette la modifica del profilo della società.
     */
    public void modificaProfilo() {
    }

    /**
     * Propone una nuova gara all’amministratore.
     * @param gara oggetto gara da proporre
     */
    public void nuovaGara(Gara gara) {
    }

    /**
     * Restituisce il regolamento interno della società.
     * @return testo del regolamento
     */
    public String regolamento() {
        return null;
    }
}