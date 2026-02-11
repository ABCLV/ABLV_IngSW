package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CampoGaraTest {
    
    private CampoGara campo;
    private static final int ID_TEST = 12345678;
    private static final String CORPO_IDRICO_TEST = "Fiume Adda";
    private static final String PAESE_TEST = "Bergamo";
    private static final int LUNGHEZZA_TEST = 500;
    private static final String DESCRIZIONE_TEST = "Zona Pesca Sportiva";

    @BeforeEach
    void setUp() {
        campo = new CampoGara(ID_TEST, PAESE_TEST, CORPO_IDRICO_TEST, LUNGHEZZA_TEST, DESCRIZIONE_TEST);
    }
    
    
 
    @Test
    @DisplayName("Setter e getter modificano correttamente tutti i valori")
    void testSetterGetter() {
        campo.setIdCampoGara(23456789);
        assertEquals(23456789, campo.getIdCampoGara());
        
        campo.setCorpoIdrico("Lago di Como");
        assertEquals("Lago di Como", campo.getCorpoIdrico());
        
        campo.setPaese("Lecco");
        assertEquals("Lecco", campo.getPaese());
        
        campo.setLunghezza(1000);
        assertEquals(1000, campo.getLunghezza());
        
        campo.setDescrizione("Nuova descrizione");
        assertEquals("Nuova descrizione", campo.getDescrizione());
    }
    
  
}