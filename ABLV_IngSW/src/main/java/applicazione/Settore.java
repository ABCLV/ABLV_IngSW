package applicazione;

/**
 * Rappresenta un settore all'interno di un campo gara.
 */
public class Settore {

    public String idSettore;
    private String descrizione;

    /**
     * Costruttore completo.
     * @param idSettore   identificativo del settore
     * @param descrizione descrizione del settore
     */
    public Settore(String idSettore, String descrizione) {
        this.idSettore = idSettore;
        this.descrizione = descrizione;
    }

    /**
     * Restituisce le caratteristiche del settore.
     * @return caratteristiche testuali del settore
     */
    public String getCaratteristiche() {
        return null;
    }

    /**
     * Imposta le caratteristiche del settore.
     */
    public void setCaratteristiche() {
    }
}