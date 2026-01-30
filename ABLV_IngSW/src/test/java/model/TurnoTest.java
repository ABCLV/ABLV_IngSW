package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TurnoTest {
    
    private Turno turno;
    private static final String CODICE_TEST = "T01";
    private static final String DURATA_TEST = "09:00-13:00";

    @BeforeEach
    void setUp() {
        turno = new Turno(CODICE_TEST, DURATA_TEST);
    }
    
    @Test
    @DisplayName("Costruttore inizializza correttamente")
    void testCostruttore() {
        assertNotNull(turno);
        assertEquals(CODICE_TEST, turno.codiceTurno);
    }
    
    @Test
    @DisplayName("Campo codiceTurno accessibile e modificabile")
    void testCodiceTurnoPublic() {
        turno.codiceTurno = "T02";
        assertEquals("T02", turno.codiceTurno);
    }
}