package applicazione;

/**
 * Stato di avanzamento di una singola gara.
 */
public enum StatoGara {
    /**
     * La gara è attualmente in svolgimento.
     */
    IN_CORSO,
    NON_INIZIATA,

    /**
     * La gara è in pausa tra due turni.
     */
    INTERVALLO,

    /**
     * La gara è terminata.
     */
    TERMINATA;
}