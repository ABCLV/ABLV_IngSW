package applicazione;

/**
 * Tecniche di pesca ammesse in gara.
 */
public enum Tecnica {
    SPINNING, PASSATA, MOSCA, TENKARA, GALLEGGIANTE;

    public static Tecnica fromString(String s) {
        for (Tecnica t : values()) {
            if (t.name().equalsIgnoreCase(s)) return t;
        }
        return null; // oppure lancia eccezione
    }
}
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
