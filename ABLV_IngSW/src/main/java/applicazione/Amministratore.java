package applicazione;

import java.sql.SQLException;

import client.Session;
import database.Consultazioni;

/**
 * Classe che rappresenta l'amministratore del sistema.
 */
public class Amministratore implements PropositoreIF {

    public String cfAmministratore;
    private String nome;
    private String cognome;

    /**
     * Costruttore completo.
     * @param cfAmministratore codice fiscale dell'amministratore
     * @param nome nome dell'amministratore
     * @param cognome cognome dell'amministratore
     */
    public Amministratore(String cfAmministratore, String nome, String cognome) {
        this.cfAmministratore = cfAmministratore;
        this.nome = nome;
        this.cognome = cognome;
    }
    
    public Amministratore() {}

    /**
     * Crea e proponi una nuova gara.
     * @param gara oggetto gara da inserire nel sistema
     */
    public void proponiGara(Gara gara) {
    	if(gara.getPropositore().getNome().isEmpty()) {
    		gara.setPropositore(this);
    	}
    	Consultazioni.insertGara(gara);
    }

    /**
     * Approva una proposta di gara.
     * @param numGara numero identificativo della gara
     * @throws SQLException 
     */
    public boolean confermaProposta(String numGara) throws SQLException {
    	return Consultazioni.accettaGara(numGara, this.cfAmministratore);
    }

    /**
     * Rifiuta una proposta di gara inviando il motivo.
     * @param numGara numero identificativo della gara
     * @param motivo motivazione del rifiuto
     */
    public boolean negaProposta(String codiceGara) throws SQLException {
        return Consultazioni.rifiutaGara(codiceGara, this.cfAmministratore);
    }
    /**
     * Invia notifica alle società interessate.
     */
    private void notificaSocieta() {
    }

    /**
     * Invia notifica al comune competente.
     */
    private void notificaComune() {
    }

    /**
     * Restituisce il testo del regolamento aggiornato.
     * @return testo completo del regolamento
     */
    public String regolamento() {
        return null;
    }

	public String getCfAmministratore() {
		return cfAmministratore;
	}

	public void setCfAmministratore(String cfAmministratore) {
		this.cfAmministratore = cfAmministratore;
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
	
	public String getIdentificatore() {
		return this.getCfAmministratore();
	}
}