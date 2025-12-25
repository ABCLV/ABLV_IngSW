package applicazione;

import java.time.LocalDate;

/**
 * Rappresenta una gara di pesca.
 */
public class Gara {

	


	private String codice;
    private int numProva;
    private Tecnica tecnica;
    private String criterioPunti;
    private LocalDate data;
    private int maxPersone;
    private int minPersone;
    private StatoGara statoGara;
    private StatoConferma statoConferma;
    private TipologiaGara tipoGara;

    /**
     * Costruttore completo.
     * @param codiceGara      codice identificativo della gara
     * @param nProva          numero della prova (per campionati)
     * @param organizzatore   società organizzatrice
     * @param tipoTecnica     tecnica di pesca ammessa
     * @param criterioPunti   criterio di assegnazione punti
     * @param dataSvolgimento data in cui si svolge la gara
     * @param maxPersone      numero massimo di partecipanti
     * @param minPersone      numero minimo di partecipanti
     * @param stato           stato organizzativo della gara
     * @param tipoGara        tipologia (singola, campionato, ecc.)
     * @param annoGara        anno di edizione
     */
    public Gara(String codiceGara, int nProva, String organizzatore, Tecnica tipoTecnica,
                String criterioPunti, LocalDate dataSvolgimento, int maxPersone, int minPersone,
                StatoConferma statoConferma, StatoGara statoGara, TipologiaGara tipoGara, LocalDate annoGara) {
        this.codice = codiceGara;
        this.numProva = nProva;
        this.tecnica = tipoTecnica;
        this.criterioPunti = criterioPunti;
        this.minPersone = minPersone;
        this.maxPersone = maxPersone;
        this.statoGara = statoGara;
        this.statoConferma = statoConferma;
        this.tipoGara = tipoGara;
        this.data = dataSvolgimento;
    }
    
    public Gara() {
    	
    }

    /**
     * Cambia lo stato organizzativo della gara.
     * @param stato nuovo stato organizzativo
     */
    public void cambiaStatoOrganizzazione(StatoConferma stato) {
    }

    /**
     * Cambia lo stato di svolgimento della gara.
     * @param stato nuovo stato gara
     */
    public void cambiaStatoGara(StatoGara stato) {
    }

    /**
     * Squalifica un concorrente dalla gara.
     * @param concorrente concorrente da squalificare
     * @param motivo      motivazione della squalifica
     */
    public void squalifica(Concorrente concorrente, String motivo) {
    }

    /**
     * Prepara i turni in base ai concorrenti presenti.
     * @param concorrenti array dei concorrenti da smistare
     */
    public void preparaTurni(Concorrente[] concorrenti) {
    }

    /**
     * Assegna un turno a un gruppo di concorrenti.
     * @param turno  turno da assegnare
     * @param gruppo array dei concorrenti del gruppo
     */
    private void assegnaTurno(Turno turno, Concorrente[] gruppo) {
    }

    /**
     * Calcola la classifica di un turno specifico.
     * @param turno     turno da classificare
     * @param punteggi  punteggi ottenuti dai concorrenti
     */
    public void classificaTurno(Turno turno, Punteggio[] punteggi) {
    }

    /**
     * Individua e assegna un arbitro alla gara.
     */
    public void trovaArbitro() {
    }

    /**
     * Registra una nuova iscrizione verificando i limiti di partecipanti.
     * @param cf         codice fiscale del concorrente
     * @param numIscritti numero attuale di iscritti
     * @return true se l'iscrizione è accettata, false altrimenti
     */
    public boolean nuovaIscrizione(String cf, int numIscritti) {
        return false;
    }

    /**
     * Gestisce l'iscrizione multipla di più concorrenti.
     * @param concorrentiPresenti array dei concorrenti da iscrivere
     * @return true se tutte le iscrizioni sono accettate, false altrimenti
     */
    public boolean iscrizioneMultipla(Concorrente[] concorrentiPresenti) {
        return false;
    }

    /**
     * Verifica se la data della gara è valida.
     * @return true se la data è ammissibile, false altrimenti
     */
    public boolean verificaData() {
        return false;
    }

    /**
     * Verifica se il numero massimo di partecipanti è rispettato.
     * @return true se non si supera il massimo, false altrimenti
     */
    public boolean verificaMax() {
        return false;
    }

    /**
     * Verifica se il numero minimo di partecipanti è raggiunto.
     * @return true se il minimo è soddisfatto, false altrimenti
     */
    public boolean verificaMin() {
        return false;
    }

    /**
     * Restituisce la classifica finale della gara.
     * @return array dei punteggi ordinati per posizione
     */
    public Punteggio[] getClassifica() {
        return null;
    }
    
    public void setCodice(String setCodice) { this.codice = setCodice;}
    public void setNumProva(int numProva) { this.numProva = numProva; }
    public void setTecnica(Tecnica tecnica) { this.tecnica = tecnica; }
    public void setCriterioPunti(String criterioPunti) { this.criterioPunti = criterioPunti; }
    public void setData(LocalDate data) { this.data = data; }
    public void setMaxPersone(int maxPersone) { this.maxPersone = maxPersone; }
    public void setMinPersone(int minPersone) { this.minPersone = minPersone; }
    public void setStatoGara(StatoGara statoGara) { this.statoGara = statoGara; }
    public void setStatoConferma(StatoConferma statoConferma) { this.statoConferma = statoConferma; }
    public void setTipoGara(TipologiaGara tipoGara) { this.tipoGara = tipoGara; }

    
    @Override
	public String toString() {
	    return "Gara {\n" +
	           "  codiceGara      = " + codice + ",\n" +
	           "  nProva          = " + numProva + ",\n" +
	           "  tipoTecnica     = " + tecnica + ",\n" +
	           "  criterioPunti   = " + criterioPunti + ",\n" +
	           "  dataSvolgimento = " + data + ",\n" +
	           "  maxPersone      = " + maxPersone + ",\n" +
	           "  minPersone      = " + minPersone + ",\n" +
	           "  statoGara       = " + statoGara + ",\n" +
	           "  statoConferma   = " + statoConferma + ",\n" +
	           "  tipoGara        = " + tipoGara + "\n" +
	           "}";
	}
}