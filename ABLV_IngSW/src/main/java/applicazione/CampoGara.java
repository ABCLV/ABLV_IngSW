package applicazione;

/**
 * Rappresenta un campo gara (tratto di fiume/torrente) dove si svolgono le competizioni.
 */
public class CampoGara {

    public String idCampoGara;
    private String corpoIdrico;
    private String paese;
    private int lunghezza;

    /**
     * Costruttore completo.
     * @param idCampoGara  identificativo del campo gara
     * @param corpoIdrico nome del fiume/torrente
     * @param paese       comune o località
     * @param lunghezza   lunghezza in metri
     */
    public CampoGara(String idCampoGara, String corpoIdrico, String paese, int lunghezza) {
        this.idCampoGara = idCampoGara;
        this.corpoIdrico = corpoIdrico;
        this.paese = paese;
        this.lunghezza = lunghezza;
    }

    /**
     * Restituisce le caratteristiche del campo gara.
     * @return descrizione testuale delle caratteristiche
     */
    public String getCaratteristiche() {
        return null;
    }

    /**
     * Imposta o aggiorna le caratteristiche del campo gara.
     */
    public void setCaratteristiche() {
    }
}