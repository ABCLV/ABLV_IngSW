package client;   // stesso package dove hai GaraRow

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import applicazione.Gara;

public final class GaraFxWrapper {
    private final Gara gara;
    private final StringProperty codice = new SimpleStringProperty();
    private final StringProperty data   = new SimpleStringProperty();
    private final StringProperty tecnica= new SimpleStringProperty();
    private final StringProperty campo  = new SimpleStringProperty();

    public GaraFxWrapper(Gara gara, String campoDescr) {
        this.gara = gara;
        codice.set(gara.getCodice());
        data.set(gara.getData().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        tecnica.set(gara.getTecnica().name());   // oppure getDescrizione() se hai un metodo
        campo.set(campoDescr);
    }

    /* ---------- property necessarie alla TableView ---------- */
    public StringProperty codiceProperty()  { return codice; }
    public StringProperty dataProperty()    { return data; }
    public StringProperty tecnicaProperty() { return tecnica; }
    public StringProperty campoProperty()   { return campo; }

    /* ---------- getter se servono altrove ---------- */
    public Gara getGara() { return gara; }
}