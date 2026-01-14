package client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConcorrenteRow {
	private final StringProperty cf = new SimpleStringProperty();
	private final StringProperty nome = new SimpleStringProperty();
	private final StringProperty cognome = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final StringProperty nascita = new SimpleStringProperty();

	public ConcorrenteRow(String cf, String nome, String cognome, String email, String nascita) {
		this.cf.set(cf);
		this.nome.set(nome);
		this.cognome.set(cognome);
		this.email.set(email);
		this.nascita.set(nascita);
	}

	public StringProperty cfProperty() {
		return cf;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty cognomeProperty() {
		return cognome;
	}

	public StringProperty emailProperty() {
		return email;
	}

	public StringProperty nascitaProperty() {
		return nascita;
	}
}