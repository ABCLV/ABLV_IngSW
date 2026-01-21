package applicazione;

/**
 * Rappresenta un settore all'interno di un campo gara.
 */
public class Settore {

	public String getIdSettore() {
		return idSettore;
	}

	public int getLunghezza() {
		return lunghezza;
	}

	public String getDescrizione() {
		return descrizione;
	}


	private String idSettore;
    private int lunghezza;
    private String descrizione;

    /**
     * Costruttore completo.
     * @param idSettore   identificativo del settore
     * @param descrizione descrizione del settore
     */
    public Settore(String idSettore, int lunghezza, String descrizione) {
        this.idSettore = idSettore;
        this.lunghezza = lunghezza;
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
    
    
    @Override
    public String toString() {
        return "Settore {\n" +
               "  idSettore   = " + idSettore + ",\n" +
               "  lunghezza   = " + lunghezza + ",\n" +
               "  descrizione = " + descrizione + "\n" +
               "}";
    }

}