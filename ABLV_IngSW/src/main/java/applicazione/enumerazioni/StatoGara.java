package applicazione.enumerazioni;

/**
 * Stato di avanzamento di una singola gara.
 */
public enum StatoGara {
	/**
	 * La gara è attualmente in svolgimento.
	 */
	IN_CORSO, NON_INIZIATA,

	/**
	 * La gara è in pausa tra due turni.
	 */
	INTERVALLO,

	/**
	 * La gara è terminata.
	 */
	TERMINATA;

	public static StatoGara fromString(String s) {
		for (StatoGara t : values()) {
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