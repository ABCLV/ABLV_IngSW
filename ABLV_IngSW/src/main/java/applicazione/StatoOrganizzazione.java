package applicazione;

/**
 * Stato organizzativo di una gara.
 */
public enum StatoOrganizzazione {
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
}