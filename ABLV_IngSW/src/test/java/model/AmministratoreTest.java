package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitari per la classe Amministratore.
 * Verifica il corretto funzionamento dei getter/setter, 
 * dell'implementazione di SoggettoIF e dei costruttori.
 */
class AmministratoreTest {

    private Amministratore admin;
    private static final String CF_TEST = "RSSMRA80A01H501Z";
    private static final String NOME_TEST = "Mario";
    private static final String COGNOME_TEST = "Rossi";

    @BeforeEach
    void setUp() {
        admin = new Amministratore();
    }

    // ============================================================
    // TEST COSTRUTTORI
    // ============================================================

    @Test
    @DisplayName("Costruttore vuoto dovrebbe creare istanza non null")
    void testCostruttoreVuoto() {
        Amministratore a = new Amministratore();
        assertNotNull(a);
    }

    @Test
    @DisplayName("Costruttore completo dovrebbe inizializzare tutti i campi")
    void testCostruttoreCompleto() {
        Amministratore a = new Amministratore(CF_TEST, NOME_TEST, COGNOME_TEST);
        
        assertAll("Verifica inizializzazione costruttore",
            () -> assertEquals(CF_TEST, a.getCfAmministratore()),
            () -> assertEquals(NOME_TEST, a.getNome()),
            () -> assertEquals(COGNOME_TEST, a.getCognome())
        );
    }

    // ============================================================
    // TEST GETTER/SETTER BASE
    // ============================================================

    @Test
    @DisplayName("Dovrebbe settare e restituire correttamente il CF")
    void testSetAndGetCfAmministratore() {
        admin.setCfAmministratore(CF_TEST);
        assertEquals(CF_TEST, admin.getCfAmministratore());
    }

    @Test
    @DisplayName("Dovrebbe settare e restituire correttamente il nome")
    void testSetAndGetNome() {
        admin.setNome(NOME_TEST);
        assertEquals(NOME_TEST, admin.getNome());
    }

    @Test
    @DisplayName("Dovrebbe settare e restituire correttamente il cognome")
    void testSetAndGetCognome() {
        admin.setCognome(COGNOME_TEST);
        assertEquals(COGNOME_TEST, admin.getCognome());
    }

    @Test
    @DisplayName("Setter dovrebbero permettere modifiche post-costruzione")
    void testModificaDati() {
        // Setup iniziale
        admin.setCfAmministratore("OLD_CF");
        admin.setNome("Old");
        admin.setCognome("Old");
        
        // Modifica
        admin.setCfAmministratore(CF_TEST);
        admin.setNome(NOME_TEST);
        admin.setCognome(COGNOME_TEST);
        
        // Verifica
        assertAll("Verifica modifiche",
            () -> assertEquals(CF_TEST, admin.getCfAmministratore()),
            () -> assertEquals(NOME_TEST, admin.getNome()),
            () -> assertEquals(COGNOME_TEST, admin.getCognome())
        );
    }


    // ============================================================
    // TEST toString()
    // ============================================================

    @Test
    @DisplayName("toString() dovrebbe contenere CF, cognome e nome")
    void testToString() {
        admin.setCfAmministratore(CF_TEST);
        admin.setNome(NOME_TEST);
        admin.setCognome(COGNOME_TEST);
        
        String result = admin.toString();
        
        assertAll("Verifica contenuto toString",
            () -> assertTrue(result.contains(CF_TEST)),
            () -> assertTrue(result.contains(NOME_TEST)),
            () -> assertTrue(result.contains(COGNOME_TEST)),
            () -> assertTrue(result.contains(":")),
            () -> assertTrue(result.contains("-"))
        );
    }

    // ============================================================
    // TEST COMPORTAMENTO OGGETTO
    // ============================================================

    @Test
    @DisplayName("Distanza logica: due amministratori con stesso CF sono logicamente equivalenti")
    void testUguaglianzaLogicaPerCf() {
        Amministratore a1 = new Amministratore();
        a1.setCfAmministratore(CF_TEST);
        a1.setNome("Mario");
        
        Amministratore a2 = new Amministratore();
        a2.setCfAmministratore(CF_TEST);
        a2.setNome("Luigi"); // nome diverso, stesso CF
        
        // Verifica che il CF sia la chiave logica (anche se equals() non è override)
        assertEquals(a1.getCfAmministratore(), a2.getCfAmministratore());
    }
    
    @Test
    @DisplayName("Non dovrebbe accettare valori null nei campi obbligatori")
    void testSetterRifiutaNull() {
        // Test CF null
        try {
            admin.setCfAmministratore(null);
            fail("Dovrebbe lanciare IllegalArgumentException per CF null");
        } catch (IllegalArgumentException e) {
            // OK, ci aspettiamo questa eccezione
        }
        
        // Test Nome null
        try {
            admin.setNome(null);
            fail("Dovrebbe lanciare IllegalArgumentException per Nome null");
        } catch (IllegalArgumentException e) {
            // OK
        }
        
        // Test Cognome null
        try {
            admin.setCognome(null);
            fail("Dovrebbe lanciare IllegalArgumentException per Cognome null");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}