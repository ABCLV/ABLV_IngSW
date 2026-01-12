package applicazione;

/**
 * Classe che rappresenta l'amministratore del sistema.
 */
public class Amministratore {

    public String cfAmministratore;
    private String nome;
    private String cognome;

    /**
     * Costruttore completo.
     * @param cfAmministratore codice fiscale dell'amministratore
     * @param nome nome dell'amministratore
     * @param cognome cognome dell'amministratore
     */
    public Amministratore(String cfAmministratore, String nome, String cognome) {
        this.cfAmministratore = cfAmministratore;
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * Crea e pubblica una nuova gara.
     * @param gara oggetto gara da inserire nel sistema
     */
    public void nuovaGara(Gara gara) {
    }

    /**
     * Approva una proposta di gara.
     * @param numGara numero identificativo della gara
     */
    public void confermaProposta(int numGara) {
    }

    /**
     * Rifiuta una proposta di gara inviando il motivo.
     * @param numGara numero identificativo della gara
     * @param motivo motivazione del rifiuto
     */
    public void negaProposta(int numGara, String motivo) {
    }

    /**
     * Invia notifica alle società interessate.
     */
    private void notificaSocieta() {
    }

    /**
     * Invia notifica al comune competente.
     */
    private void notificaComune() {
    }

    /**
     * Restituisce il testo del regolamento aggiornato.
     * @return testo completo del regolamento
     */
    public String regolamento() {
        return null;
    }
}