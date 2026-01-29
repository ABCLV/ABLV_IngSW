package model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CriterioPuntiTest {

    @Test
    @DisplayName("Enum contiene tutti i valori previsti")
    void testValoriEnum() {
        CriterioPunti[] valori = CriterioPunti.values();
        
        assertEquals(3, valori.length);
        assertNotNull(CriterioPunti.NUM_CATTURE);
        assertNotNull(CriterioPunti.PESO);
        assertNotNull(CriterioPunti.BIG_ONE);
    }
    
    @Test
    @DisplayName("toString formatta correttamente convertendo underscore e capitalizzando")
    void testToString() {
        assertEquals("Num catture", CriterioPunti.NUM_CATTURE.toString());
        assertEquals("Peso", CriterioPunti.PESO.toString());
        assertEquals("Big one", CriterioPunti.BIG_ONE.toString());
    }
    
    @Test
    @DisplayName("valueOf ritorna il valore corretto dal nome")
    void testValueOf() {
        assertEquals(CriterioPunti.NUM_CATTURE, CriterioPunti.valueOf("NUM_CATTURE"));
        assertEquals(CriterioPunti.PESO, CriterioPunti.valueOf("PESO"));
    }
}