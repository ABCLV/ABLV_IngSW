package client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class GaraRow {
	private final StringProperty codice = new SimpleStringProperty();
	private final SimpleIntegerProperty numprova = new SimpleIntegerProperty();
	private final StringProperty data = new SimpleStringProperty();
	private final StringProperty tecnica = new SimpleStringProperty();
	private final StringProperty campionato = new SimpleStringProperty();
	private final StringProperty campo = new SimpleStringProperty();
	private final StringProperty propositore = new SimpleStringProperty();

	public GaraRow(String codice, String data, String tecnica, String campo) {
		this.codice.set(codice);
		this.data.set(data);
		this.tecnica.set(tecnica);
		this.campo.set(campo);
	}
	
	public GaraRow(String codice, int numprova, String tecnica, String data, String campionato, String campo) {
		this.codice.set(codice);
		this.data.set(data);
		this.tecnica.set(tecnica);
		this.campo.set(campo);
		this.numprova.set(numprova);
		this.campionato.set(campionato);
	}
	
	public GaraRow(String codice, int numprova, String tecnica, String data, String campionato, String campo, String propositore) {
		this.codice.set(codice);
		this.data.set(data);
		this.tecnica.set(tecnica);
		this.campo.set(campo);
		this.numprova.set(numprova);
		this.campionato.set(campionato);
		this.propositore.set(propositore);
	}

	public StringProperty codiceProperty() {
		return codice;
	}

	public StringProperty dataProperty() {
		return data;
	}

	public StringProperty tecnicaProperty() {
		return tecnica;
	}

	public StringProperty campoProperty() {
		return campo;
	}
}