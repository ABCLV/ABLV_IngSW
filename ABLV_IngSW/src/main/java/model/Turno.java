package model;

/**
 * Rappresenta un turno all'interno di una gara di pesca.
 */
public class Turno {

	private int codiceTurno;
	private Integer durata;
	private Settore sett;
	private Integer numero;
	
	public Integer getDurata() {
		return durata;
	}

	
	@Override
	public String toString() {
	    return "Turno{" +
	            "codiceTurno=" + codiceTurno +
	            ", numero=" + numero +
	            ", durata=" + durata + " min" +
	            ", settore=" + (sett != null ? sett.toString() : "null") +
	            '}';
	}

	public void setDurata(Integer durata) {
		this.durata = durata;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCodiceTurno() {
		return codiceTurno;
	}

	public void setCodiceTurno(int codiceTurno) {
		this.codiceTurno = codiceTurno;
	}

	

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
	public Turno(int codiceTurno, Integer durata, Settore sett, int numero) {
	    this.codiceTurno = codiceTurno;
	    this.durata = durata;
	    this.sett = sett;
	    this.numero = numero;
	}
	
	public Turno() {}


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