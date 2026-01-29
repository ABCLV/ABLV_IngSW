package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SedeTest {
    
    private static final int ID_TEST = 1;
    private static final String INDIRIZZO_TEST = "Via Roma 123";
    private static final String CITTA_TEST = "Milano";
    private static final String CAP_TEST = "20121";

    @Test
    @DisplayName("Costruttore inizializza correttamente")
    void testCostruttore() {
        Sede sede = new Sede(ID_TEST, INDIRIZZO_TEST, CITTA_TEST, CAP_TEST);
        
        assertNotNull(sede);
        assertEquals(ID_TEST, sede.idSede);
    }
    
    @Test
    @DisplayName("Creazione sede con valori null non lancia eccezioni")
    void testCostruttoreConNull() {
        try {
            Sede sede = new Sede(2, null, null, null);
            assertNotNull(sede);
            assertEquals(2, sede.idSede);
        } catch (Exception e) {
            fail("Non dovrebbe lanciare eccezione con parametri null");
        }
    }
}