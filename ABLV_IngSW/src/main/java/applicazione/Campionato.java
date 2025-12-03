package applicazione;

import java.util.ArrayList;

/**
 * Rappresenta un campionato: insieme di prove che assegnano punteggi cumulativi.
 */
public class Campionato {

    public String titolo;
    private String categoria;
    private ArrayList<Gara> prove;
    private ArrayList<Punteggio> classificaTotale;

    /**
     * Costruttore completo.
     * @param titolo    nome del campionato
     * @param categoria categoria di partecipazione (es. "senior", "junior")
     */
    public Campionato(String titolo, String categoria) {
        this.titolo = titolo;
        this.categoria = categoria;
        this.prove = new ArrayList<>();
        this.classificaTotale = new ArrayList<>();
    }

    /**
     * Restituisce la classifica generale finale del campionato.
     * @return lista dei punteggi totali ordinati per posizione
     */
    public ArrayList<Punteggio> getClassificaTotale() {
        return null;
    }

    /**
     * Calcola e restituisce la classifica di una singola prova.
     * @param numProva numero di prova (1-based)
     * @return lista dei punteggi di quella prova
     */
    public ArrayList<Punteggio> classificaProva(int numProva) {
    	return null;
    }
}