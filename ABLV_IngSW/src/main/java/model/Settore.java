package model;

import java.util.Objects;

/**
 * Rappresenta un settore all'interno di un campo gara.
 */
public class Settore {

	public int getIdSettore() {
		return idSettore;
	}

	public int getLunghezza() {
		return lunghezza;
	}

	public String getDescrizione() {
		return descrizione;
	}

	private int idSettore;
	private int lunghezza;
	private String descrizione;

	/**
	 * Costruttore completo.
	 * 
	 * @param idSettore   identificativo del settore
	 * @param descrizione descrizione del settore
	 */
	public Settore(int idSettore, int lunghezza, String descrizione) {
		this.idSettore = idSettore;
		this.lunghezza = lunghezza;
		this.descrizione = descrizione;
	}

	/**
	 * Restituisce le caratteristiche del settore.
	 * 
	 * @return caratteristiche testuali del settore
	 */
	public String getCaratteristiche() {
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Settore)) return false;
	    Settore other = (Settore) o;
	    return Objects.equals(idSettore, other.idSettore);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(idSettore);
	}


	/**
	 * Imposta le caratteristiche del settore.
	 */
	public void setCaratteristiche() {
	}

	@Override
	public String toString() {
		return "Settore {\n" + "  idSettore   = " + idSettore + ",\n" + "  lunghezza   = " + lunghezza + ",\n"
				+ "  descrizione = " + descrizione + "\n" + "}";
	}

}