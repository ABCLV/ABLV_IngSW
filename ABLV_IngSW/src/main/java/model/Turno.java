package model;

/**
 * Rappresenta un turno all'interno di una gara di pesca.
 */
public class Turno {

	public String codiceTurno;
	public String getCodiceTurno() {
		return codiceTurno;
	}

	public void setCodiceTurno(String codiceTurno) {
		this.codiceTurno = codiceTurno;
	}

	private String durata;
	private Settore sett;

	public Settore getSett() {
		return sett;
	}

	public void setSett(Settore sett) {
		this.sett = sett;
	}

	/**
	 * Costruttore completo.
	 * 
	 * @param codiceTurno identificativo del turno
	 * @param durata      durata espressa in formato "HH:mm" o testo descrittivo
	 */
	public Turno(String codiceTurno, String durata, Settore sett) {
		this.codiceTurno = codiceTurno;
		this.durata = durata;
		this.sett = sett;
	}

	/**
	 * Calcola la classifica del turno sulla base dei punteggi forniti.
	 * 
	 * @param punteggi array dei punteggi da ordinare
	 */
	public void calcolaClassifica(Punteggio[] punteggi) {
	}

	/**
	 * Restituisce la classifica finale del turno.
	 * 
	 * @return array dei punteggi ordinati
	 */
	public Punteggio[] getClassifica() {
		return null;
	}
}