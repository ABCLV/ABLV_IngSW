package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TurnoTest {
    
    private Turno turno;
    private Settore settoreTest;
    private static final int CODICE_TEST = 123456;
    private static final int DURATA_TEST = 4;

    @BeforeEach
    void setUp() {
    	settoreTest = new Settore(456789, 100, "Settore di test");
    	turno = new Turno(CODICE_TEST, DURATA_TEST, settoreTest, 2);
    }
    
 
    @Test
    @DisplayName("Campo codiceTurno accessibile e modificabile")
    void testCodiceTurnoPublic() {
        turno.setCodiceTurno(999);
        assertEquals(999, turno.getCodiceTurno());
    }
    
    @Test
    @DisplayName("Setter e getter per settore funzionano correttamente")
    void testSettoreGetterSetter() {
        Settore nuovoSettore = new Settore(789, 100, "Settore di test");
        turno.setSett(nuovoSettore);
        assertEquals(nuovoSettore, turno.getSett());
    }
}