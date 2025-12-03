package applicazione;

/**
 * Rappresenta un turno all'interno di una gara di pesca.
 */
public class Turno {

    public String codiceTurno;
    private String durata;

    /**
     * Costruttore completo.
     * @param codiceTurno identificativo del turno
     * @param durata      durata espressa in formato "HH:mm" o testo descrittivo
     */
    public Turno(String codiceTurno, String durata) {
        this.codiceTurno = codiceTurno;
        this.durata = durata;
    }

    /**
     * Calcola la classifica del turno sulla base dei punteggi forniti.
     * @param punteggi array dei punteggi da ordinare
     */
    public void calcolaClassifica(Punteggio[] punteggi) {
    }

    /**
     * Restituisce la classifica finale del turno.
     * @return array dei punteggi ordinati
     */
    public Punteggio[] getClassifica() {
        return null;
    }
}