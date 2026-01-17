package applicazione;

import java.util.ArrayList;

/**
 * Rappresenta un campionato: insieme di prove che assegnano punteggi cumulativi.
 */
public class Campionato {

    private String titolo;
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

    public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public ArrayList<Gara> getProve() {
		return prove;
	}

	public void setProve(ArrayList<Gara> prove) {
		this.prove = prove;
	}

	public void setClassificaTotale(ArrayList<Punteggio> classificaTotale) {
		this.classificaTotale = classificaTotale;
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
    
    @Override
    public String toString() {
        return this.titolo;
    }
}