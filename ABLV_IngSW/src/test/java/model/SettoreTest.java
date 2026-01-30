package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SettoreTest {
    
    private Settore settore;
    private static final String ID_TEST = "SET001";
    private static final int LUNGHEZZA_TEST = 150;
    private static final String DESCRIZIONE_TEST = "Zona nord torrente";

    @BeforeEach
    void setUp() {
        settore = new Settore(ID_TEST, LUNGHEZZA_TEST, DESCRIZIONE_TEST);
    }
    
    @Test
    @DisplayName("Costruttore inizializza correttamente tutti i campi")
    void testCostruttore() {
        assertAll("Verifica inizializzazione",
            () -> assertEquals(ID_TEST, settore.getIdSettore()),
            () -> assertEquals(LUNGHEZZA_TEST, settore.getLunghezza()),
            () -> assertEquals(DESCRIZIONE_TEST, settore.getDescrizione())
        );
    }
    
    @Test
    @DisplayName("toString contiene id, lunghezza e descrizione")
    void testToString() {
        String result = settore.toString();
        
        assertTrue(result.contains(ID_TEST));
        assertTrue(result.contains(String.valueOf(LUNGHEZZA_TEST)));
        assertTrue(result.contains(DESCRIZIONE_TEST));
    }
    
    
}