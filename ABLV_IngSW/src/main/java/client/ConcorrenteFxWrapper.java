package client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import applicazione.Concorrente;

public final class ConcorrenteFxWrapper {
    private final Concorrente concorrente;
    private final StringProperty cf      = new SimpleStringProperty();
    private final StringProperty nome    = new SimpleStringProperty();
    private final StringProperty cognome = new SimpleStringProperty();
    private final StringProperty email   = new SimpleStringProperty();
    private final StringProperty nascita = new SimpleStringProperty();

    public ConcorrenteFxWrapper(Concorrente c) {
        this.concorrente = c;
        cf.set(c.cf);
        nome.set(c.getNome());
        cognome.set(c.getCognome());
        email.set(c.getEmail());
        nascita.set(c.getNascita().toString()); // LocalDate -> stringa
    }

    /* Property richieste dalla TableView */
    public StringProperty cfProperty()      { return cf; }
    public StringProperty nomeProperty()    { return nome; }
    public StringProperty cognomeProperty() { return cognome; }
    public StringProperty emailProperty()   { return email; }
    public StringProperty nascitaProperty() { return nascita; }

    /* Getter se serve altrove */
    public Concorrente getConcorrente() { return concorrente; }
}