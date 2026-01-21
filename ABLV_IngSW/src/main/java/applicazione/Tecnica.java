package applicazione;

/**
 * Tecniche di pesca ammesse in gara.
 */
public enum Tecnica {
    /**
     * Tecnica spinning.
     */
    SPINNING,

    /**
     * Tecnica passata.
     */
    PASSATA,

    /**
     * Tecnica mosca.
     */
    MOSCA,
	
	TENKARA,
	
	GALLEGGIANTE;
	
	@Override
	public String toString() {
	    String s = name().toLowerCase().replace("_", " ");
	    if (s.isEmpty()) return s;
	    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	
}