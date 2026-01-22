package applicazione;

/**
 * Rappresenta una recensione di un concorrente per una gara.
 */
public class Recensione {

	public int idRecensione;
	private String titolo;
	private String descrizione;
	private int voto;

	/**
	 * Costruttore completo.
	 * 
	 * @param idRecensione identificativo univoco della recensione
	 * @param titolo       titolo della recensione
	 * @param descrizione  descrizione dettagliata della recensione
	 * @param voto         punteggio (es. da 1 a 5)
	 */
	public Recensione(int idRecensione, String titolo, String descrizione, int voto) {
		this.idRecensione = idRecensione;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.voto = voto;
	}
}