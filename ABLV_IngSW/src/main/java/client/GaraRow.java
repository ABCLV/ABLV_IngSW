package client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GaraRow {
	private final StringProperty codice = new SimpleStringProperty();
	private final StringProperty data = new SimpleStringProperty();
	private final StringProperty tecnica = new SimpleStringProperty();
	private final StringProperty campo = new SimpleStringProperty();

	public GaraRow(String codice, String data, String tecnica, String campo) {
		this.codice.set(codice);
		this.data.set(data);
		this.tecnica.set(tecnica);
		this.campo.set(campo);
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