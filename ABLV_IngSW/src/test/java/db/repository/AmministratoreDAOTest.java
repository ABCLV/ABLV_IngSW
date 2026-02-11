package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.exception.AmministratoreEccezione;
import model.Amministratore;
import model.Gara;

class AmministratoreDAOTest {

    private AmministratoreDAO dao;

    @BeforeEach
    void setUp() {
        dao = new AmministratoreDAO();
    }

    @Test
    @DisplayName("Registrazione amministratore con CF univoco")
    void testRegistraAmministratoreOK() {
        String cf = "TESTCF_" + System.nanoTime();

        assertDoesNotThrow(() ->
            dao.registraAmministratore(
                cf,
                "NomeTest",
                "CognomeTest",
                "test@email.it",
                "password123"
            )
        );
        
        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaAmministratore(cf));
        
        // Verifica che sia stato eliminato
        Amministratore amm = dao.getAmministratoreByCF(cf);
        assertNull(amm);
    }

    @Test
    @DisplayName("Registrazione amministratore duplicato fallisce")
    void testRegistraAmministratoreDuplicato() {
        String cf = "TESTCF_" + System.nanoTime();

        assertDoesNotThrow(() ->
            dao.registraAmministratore(
                cf,
                "NomeTest",
                "CognomeTest",
                "test@email.it",
                "password123"
            )
        );

        assertThrows(AmministratoreEccezione.class, () ->
            dao.registraAmministratore(
                cf,
                "AltroNome",
                "AltroCognome",
                "altro@email.it",
                "pwd"
            )
        );
        
        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaAmministratore(cf));
    }

    @Test
    @DisplayName("Get amministratore by CF: restituisce null se non esiste")
    void testGetAmministratoreByCFNotFound() {
        Amministratore amm = dao.getAmministratoreByCF("CF_INESISTENTE_" + System.nanoTime());
        assertNull(amm);
    }

    @Test
    @DisplayName("Get amministratore by CF: restituisce amministratore se esiste")
    void testGetAmministratoreByCFExists() {
        String cf = "TESTCF_GET_" + System.nanoTime();

        assertDoesNotThrow(() ->
            dao.registraAmministratore(
                cf,
                "NomeTest",
                "CognomeTest",
                "get@email.it",
                "password"
            )
        );

        Amministratore amm = dao.getAmministratoreByCF(cf);

        assertNotNull(amm);
        assertEquals(cf, amm.getCfAmministratore());
        assertEquals("NomeTest", amm.getNome());
        assertEquals("CognomeTest", amm.getCognome());
        
        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaAmministratore(cf));
        
        // Verifica che sia stato eliminato
        Amministratore ammEliminato = dao.getAmministratoreByCF(cf);
        assertNull(ammEliminato);
    }

    @Test
    @DisplayName("Get gare proposte: restituisce lista non null")
    void testGetGareProposteDaAmministratore() {
        List<Gara> gare = dao.getGareProposteDaAmministratore("CF_QUALSIASI");
        assertNotNull(gare);
    }
}