package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TurnoTest {
    
    private Turno turno;
    private Settore settoreTest;
    private static final String CODICE_TEST = "T01";
    private static final String DURATA_TEST = "09:00-13:00";

    @BeforeEach
    void setUp() {
    	settoreTest = new Settore("S01", 100, "Settore di test");
    	turno = new Turno(CODICE_TEST, DURATA_TEST, settoreTest);
    }
    
    @Test
    @DisplayName("Costruttore inizializza correttamente")
    void testCostruttore() {
        assertNotNull(turno);
        assertEquals(CODICE_TEST, turno.codiceTurno);
        assertEquals(settoreTest, turno.getSett());
    }
    
    @Test
    @DisplayName("Campo codiceTurno accessibile e modificabile")
    void testCodiceTurnoPublic() {
        turno.codiceTurno = "T02";
        assertEquals("T02", turno.codiceTurno);
    }
    
    @Test
    @DisplayName("Setter e getter per settore funzionano correttamente")
    void testSettoreGetterSetter() {
        Settore nuovoSettore = new Settore("S01", 100, "Settore di test");
        turno.setSett(nuovoSettore);
        assertEquals(nuovoSettore, turno.getSett());
    }
}