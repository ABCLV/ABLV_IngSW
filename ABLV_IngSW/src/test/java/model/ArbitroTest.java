package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArbitroTest {
    
    private Arbitro arbitro;
    private static final String CF_TEST = "VRDMRC75P101F";
    private static final String NOME_TEST = "Marco";
    private static final String COGNOME_TEST = "Verdi";
    private static final String SEZIONE_TEST = "Bergamo";

    @BeforeEach
    void setUp() {
        arbitro = new Arbitro(CF_TEST, NOME_TEST, COGNOME_TEST, SEZIONE_TEST);
    }
    
  
    
    @Test
    @DisplayName("Setter e getter modificano correttamente i valori")
    void testSetterGetter() {
        arbitro.setCfArbitro("NEWCF123");
        assertEquals("NEWCF123", arbitro.getCfArbitro());
        
        arbitro.setNome("Luca");
        assertEquals("Luca", arbitro.getNome());
        
        arbitro.setCognome("Bianchi");
        assertEquals("Bianchi", arbitro.getCognome());
        
        arbitro.setSezione("Brescia");
        assertEquals("Brescia", arbitro.getSezione());
    }
    
  
}