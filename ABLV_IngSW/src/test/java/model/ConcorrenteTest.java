package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ConcorrenteTest {
    
    private Concorrente concorrente;
    private static final String CF_TEST = "RSSMRA80A01H501Z";
    private static final String COGNOME_TEST = "Rossi";
    private static final String NOME_TEST = "Mario";
    private static final String EMAIL_TEST = "mario.rossi@email.it";
    private static final LocalDate NASCITA_TEST = LocalDate.of(1980, 1, 15);
    private static final String SOCIETA_TEST = "Pesca Club Bergamo";

    @BeforeEach
    void setUp() {
        concorrente = new Concorrente(CF_TEST, COGNOME_TEST, NOME_TEST, EMAIL_TEST, NASCITA_TEST, SOCIETA_TEST);
    }
    
    @Test
    @DisplayName("Costruttore inizializza tutti i campi")
    void testCostruttore() {
        assertAll("Verifica inizializzazione",
            () -> assertEquals(CF_TEST, concorrente.getCf()),
            () -> assertEquals(COGNOME_TEST, concorrente.getCognome()),
            () -> assertEquals(NOME_TEST, concorrente.getNome()),
            () -> assertEquals(EMAIL_TEST, concorrente.getEmail()),
            () -> assertEquals(NASCITA_TEST, concorrente.getNascita()),
            () -> assertEquals(SOCIETA_TEST, concorrente.getSocieta())
        );
    }
    
    @Test
    @DisplayName("Setter e getter modificano correttamente i valori")
    void testSetterGetter() {
        concorrente.setCf("NEWCF123");
        assertEquals("NEWCF123", concorrente.getCf());
        
        concorrente.setCognome("Bianchi");
        assertEquals("Bianchi", concorrente.getCognome());
        
        concorrente.setNome("Luigi");
        assertEquals("Luigi", concorrente.getNome());
        
        concorrente.setEmail("luigi.bianchi@email.it");
        assertEquals("luigi.bianchi@email.it", concorrente.getEmail());
        
        LocalDate nuovaData = LocalDate.of(1990, 5, 20);
        concorrente.setNascita(nuovaData);
        assertEquals(nuovaData, concorrente.getNascita());
    }
    
    @Test
    @DisplayName("toString contiene CF, cognome, nome e email")
    void testToString() {
        String result = concorrente.toString();
        
        assertTrue(result.contains(CF_TEST));
        assertTrue(result.contains(COGNOME_TEST));
        assertTrue(result.contains(NOME_TEST));
        assertTrue(result.contains(EMAIL_TEST));
    }
    
    @Test
    @DisplayName("Getter societa ritorna il nome della societa")
    void testGetSocieta() {
        assertEquals(SOCIETA_TEST, concorrente.getSocieta());
    }
}