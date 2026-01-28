package model.enums;

/**
 * Stato organizzativo di una gara.
 */
public enum StatoConferma {
	/**
	 * La gara è stata confermata e si svolgerà.
	 */
	CONFERMATA,

	/**
	 * La gara è in attesa di approvazione.
	 */
	IN_ATTESA,

	/**
	 * La gara è stata annullata.
	 */
	ANNULLATA;

	public static StatoConferma fromString(String s) {
		for (StatoConferma t : values()) {
			if (t.name().equalsIgnoreCase(s))
				return t;
		}
		return null; // oppure lancia eccezione
	}

	@Override
	public String toString() {
		String s = name().toLowerCase().replace("_", " ");
		if (s.isEmpty())
			return s;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

}