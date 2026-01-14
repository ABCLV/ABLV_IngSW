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
