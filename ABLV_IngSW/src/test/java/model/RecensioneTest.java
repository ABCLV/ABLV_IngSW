package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecensioneTest {
    
    private Recensione recensione;
    private static final int ID_TEST = 1;
    private static final String TITOLO_TEST = "Ottima gara";
    private static final String DESCRIZIONE_TEST = "Organizzazione perfetta e bel campo";
    private static final int VOTO_TEST = 5;

    @BeforeEach
    void setUp() {
        recensione = new Recensione(ID_TEST, TITOLO_TEST, DESCRIZIONE_TEST, VOTO_TEST);
    }
    
    /*modificare prima recensione quando Locatelli termina le sue modifiche
    @Test
    @DisplayName("Costruttore inizializza tutti i campi")
    void testCostruttore() {
        assertEquals(ID_TEST, recensione.idRecensione);
        assertEquals(TITOLO_TEST, recensione.getTitolo());
        assertEquals(DESCRIZIONE_TEST, recensione.getDescrizione());
        assertEquals(VOTO_TEST, recensione.getVoto());
    }*/
}