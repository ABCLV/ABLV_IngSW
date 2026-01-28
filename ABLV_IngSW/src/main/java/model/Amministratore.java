package model;

import model.interfaces.SoggettoIF;

/**
 * Classe che rappresenta l'amministratore del sistema.
 */
public class Amministratore implements SoggettoIF {

	private String cfAmministratore;
	private String nome;
	private String cognome;

	/**
	 * Costruttore completo.
	 * 
	 * @param cfAmministratore codice fiscale dell'amministratore
	 * @param nome             nome dell'amministratore
	 * @param cognome          cognome dell'amministratore
	 */
	public Amministratore(String cfAmministratore, String nome, String cognome) {
		this.cfAmministratore = cfAmministratore;
		this.nome = nome;
		this.cognome = cognome;
	}

	public Amministratore() {};

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

	@Override
	public String toString() {
		return this.cfAmministratore + ":" + "\n - " + this.cognome + "\n - " + this.nome;
	}
}