package applicazione;

import java.sql.SQLException;
import database.Consultazioni;
import java.util.List;

import applicazione.interfacce.PropositoreIF;

/**
 * Rappresenta una società di pesca partecipante alle gare.
 */
public class Societa implements PropositoreIF {

	private String nome;
	private String email;
	private String cap;
	private String citta;
	private String indirizzo;

	/**
	 * Costruttore completo.
	 * 
	 * @param nome nome della società
	 */
	public Societa(String nome, String email, String cap, String citta, String indirizzo) {
		try {
			this.setNome(nome);
			this.setEmail(email);
			this.setCap(cap);
			this.setCitta(citta);
			this.setIndirizzo(indirizzo);
		} catch (Exception e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}

	public Societa() {
	}

	/**
	 * Iscrive un gruppo di concorrenti a una gara.
	 * 
	 * @param concorrenti array dei concorrenti da iscrivere
	 * @param codiceGara  codice identificativo della gara
	 * @return true se l'iscrizione è andata a buon fine, false altrimenti
	 */
	public boolean iscrizioneGaraGruppi(Concorrente[] concorrenti, String codiceGara) {
		return false;
	}

	/**
	 * Iscrive un singolo concorrente a una gara.
	 * 
	 * @param concorrente concorrente da iscrivere
	 * @param codiceGara  codice identificativo della gara
	 * @return true se l'iscrizione è andata a buon fine, false altrimenti
	 */
	public boolean iscrizioneGaraSingolo(Concorrente concorrente, String codiceGara) {
		return false;
	}

	/**
	 * Mostra il profilo della società.
	 */
	public void getProfilo() {
	}

	/**
	 * Aggiunge un nuovo socio alla società.
	 * 
	 * @param concorrente concorrente da aggiungere
	 */
	public void aggiungiSocio(Concorrente concorrente) {
	}

	/**
	 * Rimuove un socio dalla società.
	 * 
	 * @param concorrente concorrente da rimuovere
	 */
	public void abbandonoSocio(Concorrente concorrente) {
	}

	/**
	 * Permette la modifica del profilo della società.
	 */
	public void modificaProfilo() {
	}

	public List<Concorrente> getConcorrenti() {
		List<Concorrente> ret;
		try {
			ret = Consultazioni.getConcorrentiPerSocieta(this.nome);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
		}

		return ret;
	}

	public List<Gara> getGareProposte() {
		List<Gara> ret;
		try {
			ret = Consultazioni.getGareProposteDaSocieta(this.nome);
		} catch (Exception e) {
			e.printStackTrace();
			ret = null;
		}

		return ret;
	}

	/**
	 * Propone una nuova gara all’amministratore.
	 * 
	 * @param gara oggetto gara da proporre
	 */
	public void proponiGara(Gara gara) {
		if (gara.getPropositore().getNome().isEmpty()) {
			gara.setPropositore(this);
		}
		Consultazioni.insertGara(gara);
	}

	/**
	 * Restituisce il regolamento interno della società.
	 * 
	 * @return testo del regolamento
	 */
	public String regolamento() {
		return null;
	}

	public String getNome() {
		return nome;
	}

	private boolean checkString(String s) {
		if (s.isEmpty() || s.isBlank()) {
			throw new IllegalArgumentException("Nome della società non valido!");
		}
		return true;
	}

	public void setNome(String nome) {
		this.checkString(nome);
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.checkString(email);
		this.email = email;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.checkString(cap);
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.checkString(citta);
		this.citta = citta;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.checkString(indirizzo);
		this.indirizzo = indirizzo;
	}

	public String getIdentificatore() {
		return this.getNome();
	}

	public static Societa fromUsername(String nome) throws SQLException {
		return Consultazioni.getSocieta(nome);
	}

	public List<Concorrente> getConcorrentiIscritti() throws SQLException {
		return Consultazioni.getConcorrentiPerSocieta(this.nome);
	}
}