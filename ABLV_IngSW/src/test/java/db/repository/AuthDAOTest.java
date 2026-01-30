package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.exception.AuthEccezione;

class AuthDAOTest {

    private AuthDAO authDao;
    private AmministratoreDAO adminDao;

    @BeforeEach
    void setUp() {
        authDao = new AuthDAO();
        adminDao = new AmministratoreDAO();
    }

    @Test
    @DisplayName("Check password corretta per amministratore esistente")
    void testCheckPasswordCorretta() {
        String cf = "AUTH_TEST_" + System.nanoTime();
        String pwd = "password123";
        
        // Creo admin
        assertDoesNotThrow(() -> 
            adminDao.registraAmministratore(cf, "Auth", "Test", "auth@test.it", pwd)
        );
        
        // Verifico password corretta
        assertDoesNotThrow(() -> {
            boolean valido = authDao.checkPassword("Amministratore", cf, pwd);
            assertTrue(valido);
        });
    }

    @Test
    @DisplayName("Check password errata restituisce false")
    void testCheckPasswordErrata() {
        String cf = "AUTH_WRONG_" + System.nanoTime();
        String pwd = "password123";
        
        // Creo admin
        assertDoesNotThrow(() -> 
            adminDao.registraAmministratore(cf, "Auth", "Wrong", "wrong@test.it", pwd)
        );
        
        // Verifico password errata
        assertDoesNotThrow(() -> {
            boolean valido = authDao.checkPassword("Amministratore", cf, "passwordSbagliata");
            assertFalse(valido);
        });
    }

    @Test
    @DisplayName("Check password per utente inesistente restituisce false")
    void testCheckPasswordUtenteInesistente() {
        assertDoesNotThrow(() -> {
            boolean valido = authDao.checkPassword("Amministratore", "CF_INESISTENTE", "qualunque");
            assertFalse(valido);
        });
    }

    @Test
    @DisplayName("Check password per tipo non valido restituisce false")
    void testCheckPasswordTipoNonValido() {
        assertDoesNotThrow(() -> {
            boolean valido = authDao.checkPassword("TipoSconosciuto", "CF123", "pwd");
            assertFalse(valido);
        });
    }
}