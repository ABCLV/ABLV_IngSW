package applicazione;

/**
 * Rappresenta una sede associata a una società di pesca.
 */
public class Sede {

    public int idSede;
    private String indirizzo;
    private String citta;
    private String cap;

    /**
     * Costruttore completo.
     * @param idSede    identificativo numerico della sede
     * @param indirizzo via e numero civico
     * @param citta     comune di appartenenza
     * @param cap       codice di avviamento postale
     */
    public Sede(int idSede, String indirizzo, String citta, String cap) {
        this.idSede = idSede;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.cap = cap;
    }
}