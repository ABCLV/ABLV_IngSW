package applicazione;

/**
 * Rappresenta l'arbitro che gestisce lo svolgimento di una gara.
 */
public class Arbitro {

    private String cfArbitro;
    private String nome;
    private String cognome;
    private String sezione;

    /**
     * Costruttore completo.
     * @param cfArbitro  codice fiscale dell'arbitro
     * @param nome       nome dell'arbitro
     * @param cognome    cognome dell'arbitro
     * @param sezione    sezione di appartenenza (es. provinciale)
     */
    public Arbitro(String cfArbitro, String nome, String cognome, String sezione) {
        this.cfArbitro = cfArbitro;
        this.nome = nome;
        this.cognome = cognome;
        this.sezione = sezione;
    }

    public String getCfArbitro() {
		return cfArbitro;
	}

	public void setCfArbitro(String cfArbitro) {
		this.cfArbitro = cfArbitro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	/**
     * Avvia formalmente una gara.
     * @param codice identificativo della gara
     */
    public void avviaGara(String codice) {
    }

    /**
     * Rinuncia all'incarico di arbitraggio per una specifica gara.
     * @param codice identificativo della gara
     */
    public void rinuncia(String codice) {
    }

    /**
     * Registra le assenze dei concorrenti prima dell'inizio.
     * @param concorrenti array dei concorrenti iscritti
     */
    public void segnaAssenze(Concorrente[] concorrenti) {
    }

    /**
     * Crea i gruppi di pesca sulla base dei concorrenti presenti.
     * @param concorrenti array dei concorrenti da raggruppare
     */
    public void generaGruppi(Concorrente[] concorrenti) {
    }

    /**
     * Assegna un punto a un concorrente (cattura singola).
     * @param concorrente concorrente che ha effettuato la cattura
     */
    public void segnaPunto(Concorrente concorrente) {
    }

    /**
     * Assegna un numero personalizzato di punti a un concorrente.
     * @param concorrente concorrente da premiare
     * @param punti        quantità di punti da assegnare
     */
    public void segnaPunto(Concorrente concorrente, int punti) {
    }

    /**
     * Esclude un concorrente dalla gara specificando il motivo.
     * @param concorrente concorrente da escludere
     * @param motivo      motivazione dell'esclusione
     */
    public void annullaConcorrente(Concorrente concorrente, String motivo) {
    }

    /**
     * Applica una penalità in punti a un concorrente.
     * @param concorrente concorrente da penalizzare
     * @param quantita    punti da sottrarre
     * @param motivo      motivazione della penalità
     */
    public void penalizzaConcorrente(Concorrente concorrente, int quantita, String motivo) {
    }

    /**
     * Salva i punteggi di un turno per un determinato settore.
     * @param idSettore identificativo del settore
     * @param numTurno  numero del turno
     */
    public void salvaPunti(String idSettore, int numTurno) {
    }
    
    @Override
    public String toString() {
        return this.cognome + " " + this.nome + " (" + this.cfArbitro + ")";
    }
}