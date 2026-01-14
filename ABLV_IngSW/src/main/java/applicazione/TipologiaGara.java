package applicazione;

/**
 * Tipologie di gara gestite dal sistema.
 */
public enum TipologiaGara {
    /**
     * Gara individuale.
     */
    INDIVIDUALE,

    /**
     * Gara a squadre.
     */
    SQUADRE;
	
	@Override
	public String toString() {
	    String s = name().toLowerCase().replace("_", " ");
	    if (s.isEmpty()) return s;
	    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

}