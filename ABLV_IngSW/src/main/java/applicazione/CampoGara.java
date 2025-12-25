package applicazione;

/**
 * Rappresenta un campo gara (tratto di fiume/torrente) dove si svolgono le competizioni.
 */
public class CampoGara {

    public String idCampoGara;
    private String corpoIdrico;
    private String paese;
    private int lunghezza;
    private String descrizione;

    /**
     * Costruttore completo.
     * @param idCampoGara  identificativo del campo gara
     * @param corpoIdrico nome del fiume/torrente
     * @param paese       comune o località
     * @param lunghezza   lunghezza in metri
     */
    public CampoGara(String idCampoGara, String paese, String corpoIdrico,  int lunghezza, String descrizione) {
        this.idCampoGara = idCampoGara;
        this.corpoIdrico = corpoIdrico;
        this.paese = paese;
        this.lunghezza = lunghezza;
        this.descrizione = descrizione;
    }

    /**
     * Restituisce le caratteristiche del campo gara.
     * @return descrizione testuale delle caratteristiche
     */
    public String getCaratteristiche() {
        return null;
    }
    
    
    public String getId() {
    	return this.idCampoGara;
    }
    

    /**
     * Imposta o aggiorna le caratteristiche del campo gara.
     */
    public void setCaratteristiche() {
    }

    @Override
    public String toString() {
        return "CampoGara {\n" +
               "  idCampoGara = " + idCampoGara + ",\n" +
               "  corpoIdrico = " + corpoIdrico + ",\n" +
               "  paese       = " + paese + ",\n" +
               "  lunghezza   = " + lunghezza + ",\n" +
               "  descrizione = " + descrizione + "\n" +
               "}";
    }


	
}