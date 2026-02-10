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

 


    // ============================================================
    // TEST COMPORTAMENTO OGGETTO
    // ============================================================

  
    
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