package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.exception.ArbitroEccezione;
import model.Arbitro;

class ArbitroDAOTest {

    private ArbitroDAO dao;

    @BeforeEach
    void setUp() {
        dao = new ArbitroDAO();
    }

    @Test
    @DisplayName("Registrazione arbitro con CF univoco")
    void testRegistraArbitroOK() {
        String cf = "TESTCF_" + System.nanoTime();

        assertDoesNotThrow(() ->
            dao.registraArbitro(
                cf,
                "NomeArbitro",
                "CognomeArbitro",
                "SezioneTest",
                "password123"
            )
        );
        
        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaArbitro(cf));
        
        // Verifica che sia stato eliminato
        boolean esiste = dao.esisteArbitro(cf);
        assertFalse(esiste);
    }

    @Test
    @DisplayName("Registrazione arbitro duplicato fallisce")
    void testRegistraArbitroDuplicato() {
        String cf = "TESTCF_" + System.nanoTime();

        assertDoesNotThrow(() ->
            dao.registraArbitro(
                cf,
                "NomeArbitro",
                "CognomeArbitro",
                "SezioneTest",
                "password123"
            )
        );

        assertThrows(ArbitroEccezione.class, () ->
            dao.registraArbitro(
                cf,
                "AltroNome",
                "AltroCognome",
                "AltraSezione",
                "pwd"
            )
        );
        
        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaArbitro(cf));
    }

    @Test
    @DisplayName("Esiste arbitro: restituisce false se non esiste")
    void testEsisteArbitroNotFound() {
        boolean esiste = dao.esisteArbitro("CF_INESISTENTE_" + System.nanoTime());
        assertFalse(esiste);
    }

    @Test
    @DisplayName("Esiste arbitro: restituisce true se esiste")
    void testEsisteArbitroExists() {
        String cf = "TESTCF_EXIST_" + System.nanoTime();

        assertDoesNotThrow(() ->
            dao.registraArbitro(
                cf,
                "NomeArbitro",
                "CognomeArbitro",
                "SezioneTest",
                "password"
            )
        );

        boolean esiste = dao.esisteArbitro(cf);
        assertTrue(esiste);
        
        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaArbitro(cf));
        
        // Verifica che sia stato eliminato
        boolean esisteDopoEliminazione = dao.esisteArbitro(cf);
        assertFalse(esisteDopoEliminazione);
    }

   
}