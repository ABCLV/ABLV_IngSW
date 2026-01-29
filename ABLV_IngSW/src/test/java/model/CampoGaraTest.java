package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CampoGaraTest {
    
    private CampoGara campo;
    private static final String ID_TEST = "CG001";
    private static final String CORPO_IDRICO_TEST = "Fiume Adda";
    private static final String PAESE_TEST = "Bergamo";
    private static final int LUNGHEZZA_TEST = 500;
    private static final String DESCRIZIONE_TEST = "Zona Pesca Sportiva";

    @BeforeEach
    void setUp() {
        campo = new CampoGara(ID_TEST, PAESE_TEST, CORPO_IDRICO_TEST, LUNGHEZZA_TEST, DESCRIZIONE_TEST);
    }
    
    @Test
    @DisplayName("Costruttore inizializza tutti i campi")
    void testCostruttore() {
        assertAll("Verifica inizializzazione",
            () -> assertEquals(ID_TEST, campo.getIdCampoGara()),
            () -> assertEquals(CORPO_IDRICO_TEST, campo.getCorpoIdrico()),
            () -> assertEquals(PAESE_TEST, campo.getPaese()),
            () -> assertEquals(LUNGHEZZA_TEST, campo.getLunghezza()),
            () -> assertEquals(DESCRIZIONE_TEST, campo.getDescrizione())
        );
    }
    
    @Test
    @DisplayName("Costruttore vuoto crea istanza")
    void testCostruttoreVuoto() {
        CampoGara cg = new CampoGara();
        assertNotNull(cg);
    }
    
    @Test
    @DisplayName("Setter e getter modificano correttamente tutti i valori")
    void testSetterGetter() {
        campo.setIdCampoGara("CG002");
        assertEquals("CG002", campo.getIdCampoGara());
        
        campo.setCorpoIdrico("Lago di Como");
        assertEquals("Lago di Como", campo.getCorpoIdrico());
        
        campo.setPaese("Lecco");
        assertEquals("Lecco", campo.getPaese());
        
        campo.setLunghezza(1000);
        assertEquals(1000, campo.getLunghezza());
        
        campo.setDescrizione("Nuova descrizione");
        assertEquals("Nuova descrizione", campo.getDescrizione());
    }
    
    @Test
    @DisplayName("toString contiene id, corpo idrico, paese e descrizione")
    void testToString() {
        String result = campo.toString();
        
        assertTrue(result.contains(ID_TEST));
        assertTrue(result.contains(CORPO_IDRICO_TEST));
        assertTrue(result.contains(PAESE_TEST));
        assertTrue(result.contains(DESCRIZIONE_TEST));
    }
}