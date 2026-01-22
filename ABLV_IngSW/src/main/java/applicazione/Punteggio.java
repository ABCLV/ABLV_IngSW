package applicazione;

/**
 * Rappresenta un punteggio di un concorrente in una gara.
 */
public class Punteggio {

	public int idPunteggio;
	private float numPunti;
	private boolean squalifica;

	/**
	 * Costruttore completo.
	 * 
	 * @param idPunteggio identificativo del punteggio
	 * @param numPunti    punteggio assegnato
	 * @param squalifica  stato di squalifica
	 */
	public Punteggio(int idPunteggio, float numPunti, boolean squalifica) {
		this.idPunteggio = idPunteggio;
		this.numPunti = numPunti;
		this.squalifica = squalifica;
	}

	/**
	 * Visualizza la penalità associata al punteggio.
	 * 
	 * @return descrizione della penalità
	 */
	public String mostraPenalita() {
		return "";
	}

	/**
	 * Imposta il punteggio.
	 * 
	 * @param totPunti punteggio da assegnare
	 */
	public void setPunti(int totPunti) {
	}

	/**
	 * Restituisce il punteggio.
	 * 
	 * @return punteggio attuale
	 */
	public int getPunti() {
		return 0;
	}

	/**
	 * Verifica se il concorrente è squalificato.
	 */
	public void squalifica() {
	}

	/**
	 * Applica una penalità al punteggio.
	 * 
	 * @param quantita quantità di punti da togliere
	 * @param motivo   motivazione della penalità
	 * @param numPunti punteggio attuale
	 */
	public void penalita(int quantita, String motivo, int numPunti) {
	}
}