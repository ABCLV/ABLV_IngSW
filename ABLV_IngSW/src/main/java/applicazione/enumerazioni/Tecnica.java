package applicazione.enumerazioni;

/**
 * Tecniche di pesca ammesse in gara.
 */
public enum Tecnica {

	/** Tecnica spinning. */
	SPINNING,

	/** Tecnica passata. */
	PASSATA,

	/** Tecnica mosca. */
	MOSCA,

	/** Tecnica tenkara. */
	TENKARA,

	/** Tecnica galleggiante. */
	GALLEGGIANTE;

	/**
	 * Converte una stringa (case-insensitive) nel corrispondente valore enum.
	 * 
	 * @param s nome della tecnica
	 * @return tecnica corrispondente, oppure {@code null} se non esiste
	 */
	public static Tecnica fromString(String s) {
		if (s == null)
			return null;
		try {
			return Tecnica.valueOf(s.toUpperCase());
		} catch (IllegalArgumentException ex) {
			return null; // oppure lanciare ex
		}
	}

	@Override
	public String toString() {
		String s = name().toLowerCase().replace('_', ' ');
		return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
}