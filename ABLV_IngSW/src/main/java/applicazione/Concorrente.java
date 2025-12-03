package applicazione;

import java.time.LocalDate;

/**
 * Rappresenta un concorrente alle gare di pesca.
 */
public class Concorrente {

    public String cf;
    private String cognome;
    private String nome;
    private LocalDate  nascita;

    /**
     * Costruttore completo.
     * @param cf       codice fiscale del concorrente
     * @param cognome  cognome del concorrente
     * @param nome     nome del concorrente
     * @param nascita  data di nascita del concorrente
     */
    public Concorrente(String cf, String cognome, String nome, LocalDate nascita) {
        this.cf = cf;
        this.cognome = cognome;
        this.nome = nome;
        this.nascita = nascita;
    }

    /**
     * Iscrive il concorrente a una gara.
     * @param codice codice identificativo della gara
     * @return true se l'iscrizione è andata a buon fine, false altrimenti
     */
    public boolean iscriviGara(String codice) {
        return false;
    }

    /**
     * Annulla l'iscrizione del concorrente a una gara.
     * @param codice codice identificativo della gara
     * @return true se l'annullamento è andato a buon fine, false altrimenti
     */
    public boolean annullaIscrizione(String codice) {
        return false;
    }

    /**
     * Visualizza il profilo del concorrente.
     */
    public void visualizzaProfilo() {
    }

    /**
     * Mostra la carriera del concorrente.
     */
    public void carriera() {
    }

    /**
     * Permette la modifica del profilo del concorrente.
     */
    public void modificaProfilo() {
    }

    /**
     * Mostra le iscrizioni attive del concorrente.
     */
    public void iscrizioni() {
    }

    /**
     * Aggiunge una recensione per una gara a cui ha partecipato.
     * @param codice codice identificativo della gara
     * @param rec    oggetto recensione da associare
     * @return true se l'inserimento è andato a buon fine, false altrimenti
     */
    public boolean aggiungiRecensione(String codice, Recensione rec) {
        return false;
    }
}