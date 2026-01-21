package applicazione;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * Rappresenta una gara di pesca.
 */
public class Gara {

	private String codice;
    private int numProva;
    private Tecnica tecnica;
    private CriterioPunti criterioPunti;
    private LocalDate data;
    private int maxPersone;
    private int minPersone;
    private StatoGara statoGara;
    private StatoConferma statoConferma;
    private TipologiaGara tipoGara;
    /*
     * In posizione:
     *  - 0, abbiamo il propositore
     *  - 1, abbiamo l'accettatore
     */
    private PropositoreIF[] autori = new PropositoreIF[2]; 
    private Campionato campionato;
    private Arbitro arbitro;
    private CampoGara campoGara;

    /**
     * Costruttore completo.
     * @param codiceGara      codice identificativo della gara
     * @param nProva          numero della prova (per campionati)
     * @param tipoTecnica     tecnica di pesca ammessa
     * @param criterioPunti   criterio di assegnazione punti
     * @param dataSvolgimento data in cui si svolge la gara
     * @param maxPersone      numero massimo di partecipanti
     * @param minPersone      numero minimo di partecipanti
     * @param stato           stato organizzativo della gara
     * @param tipoGara        tipologia (singola, campionato, ecc.)
     * @param annoGara        anno di edizione
     */
    public Gara(String codiceGara, int nProva, Tecnica tipoTecnica,
                CriterioPunti criterioPunti, LocalDate dataSvolgimento, int maxPersone, int minPersone,
                StatoConferma statoConferma, StatoGara statoGara, TipologiaGara tipoGara, LocalDate annoGara,
                PropositoreIF propositore, PropositoreIF accettatore, Campionato campionato, Arbitro arbitro,
                CampoGara campoGara) {
    	try {
    		this.setCodice(codiceGara);
            this.setNumProva(nProva);
            this.setTecnica(tipoTecnica);
            this.setCriterioPunti(criterioPunti);
            this.setMinPersone(minPersone);
            this.setMaxPersone(maxPersone);
            this.setStatoGara(statoGara);
            this.setStatoConferma(statoConferma);
            this.setTipoGara(tipoGara);
            this.setData(annoGara);
            this.setPropositore(propositore);
            this.setAccettatore((Amministratore) accettatore);
            this.setCampionato(campionato);
            this.setArbitro(arbitro);
            this.setCampoGara(campoGara);
    	} catch(Exception e) {
    		System.out.println("Errore: " + e.getMessage());
    	}
        
    }
    
    public Gara() {}

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
     /*
      * Ritorna true se "o" NON è null.
      * Sennò lancia un eccezione e viene catturata nel costruttore della classe.
      */
    private boolean checkNull(Object o, String msg) {
    	if(o == null) {
    		throw new IllegalArgumentException(msg);
    	}
    	//Ci arriva solo se "o" NON è null.
    	return true;
    }
    
    /*
     * Logica simile a checkNull().
     */
    private boolean checkNum(int num, String msg) {
    	if(num < 0) {
    		throw new IllegalArgumentException(msg);
    	}
    	
    	return true;
    }
    
    public void setCodice(String codice) throws IllegalArgumentException{
		this.checkNull(codice, "Codice Gara non valido!");
		this.codice = codice;
    }
    
    public void setNumProva(int numProva) throws IllegalArgumentException {
    	this.checkNum(numProva, "Numero di prove non valido! Deve essere positivo...");
    	this.numProva = numProva;
    }
    public void setTecnica(Tecnica tecnica) {
    	this.checkNull(tecnica, "Tipo di tecnica non valido!");
    	this.tecnica = tecnica;
    }
    public void setCriterioPunti(CriterioPunti criterioPunti) {
    	this.checkNull(criterioPunti, "Criterio punti non valido!");
    	this.criterioPunti = criterioPunti;
    }
    public void setData(LocalDate data) {
    	LocalDate current = LocalDate.now();
    	if(current.isAfter(data)) {
    		throw new IllegalArgumentException("Data della gara non valida! (E' antecedente ad oggi...)");
    	} else {
    		this.data = data;
    	}
    }
    
    public void setMinPersone(int minPersone) {
    	this.checkNum(minPersone, "Numero di persone minime della gara non valido! Deve essere positivo...");
    	this.minPersone = minPersone;
    }
    
    public void setMaxPersone(int maxPersone) {
    	this.checkNum(maxPersone, "Numero di persone massime della gara non valido! Deve essere positivo...");
    	if(this.minPersone > maxPersone) {
    		throw new IllegalArgumentException("Numero di persone massime della gara "
    				+ "non valido! Deve essere strettamente maggiore del numero minimo di persone...");
    	}
    	this.maxPersone = maxPersone;
    }
    public void setStatoGara(StatoGara statoGara) {
    	this.checkNull(statoGara, "Stato gara non valido!");
    	this.statoGara = statoGara;    	
    }
    public void setStatoConferma(StatoConferma statoConferma) {
    	this.checkNull(statoConferma, "Stato conferma della gara non valido!");
    	this.statoConferma = statoConferma; 
    }
    public void setTipoGara(TipologiaGara tipoGara) {
    	this.checkNull(tipoGara, "Tipologia di gara non valida!");
    	this.tipoGara = tipoGara; 
    }
    
    public void setPropositore(PropositoreIF p) {
    	this.checkNull(p, "Propositore non valido!");
    	this.autori[0] = p;
    }
    
    public void setAccettatore(Amministratore a) {
    	this.autori[1] = a;
    }
    
    public void setCampionato(Campionato campionato) {
		this.campionato = campionato;
	}

    public void setArbitro(Arbitro arbitro) {
		this.arbitro = arbitro;
	}
    
    public void setCampoGara(CampoGara campoGara) {
    	this.checkNull(campoGara, "Campo gara non valido!");
		this.campoGara = campoGara;
	}

	public String getCodice() {
		return this.codice;
	}

	public int getNumProva() {
		return this.numProva;
	}

	public Tecnica getTecnica() {
		return this.tecnica;
	}

	public CriterioPunti getCriterioPunti() {
		return this.criterioPunti;
	}

	public LocalDate getData() {
		return this.data;
	}

	public int getMaxPersone() {
		return this.maxPersone;
	}

	public int getMinPersone() {
		return this.minPersone;
	}

	public StatoGara getStatoGara() {
		return this.statoGara;
	}

	public StatoConferma getStatoConferma() {
		return this.statoConferma;
	}

	public TipologiaGara getTipoGara() {
		return this.tipoGara;
	}

	public PropositoreIF[] getAutori() {
		return this.autori;
	}
	
	public PropositoreIF getPropositore() {
		return this.autori[0];
	}

	public Amministratore getAccettatore() {
		return (Amministratore) this.autori[1];
	}
	
	public Campionato getCampionato() {
		return campionato;
	}
	
	public Arbitro getArbitro() {
		return arbitro;
	}

	public CampoGara getCampoGara() {
		return campoGara;
	}

	@Override
	public String toString() {
		return "Gara [codice=" + codice + ", numProva=" + numProva + ", tecnica=" + tecnica + ", criterioPunti="
				+ criterioPunti + ", data=" + data + ", maxPersone=" + maxPersone + ", minPersone=" + minPersone
				+ ", statoGara=" + statoGara + ", statoConferma=" + statoConferma + ", tipoGara=" + tipoGara
				+ ", autori=" + Arrays.toString(autori) + ", campionato=" + campionato + ", arbitro=" + arbitro
				+ ", campoGara=" + campoGara + "]";
	}
}