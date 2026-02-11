package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.exception.ConcorrenteEccezione;
import model.Concorrente;
import model.Gara;

class ConcorrenteDAOTest {

    private ConcorrenteDAO dao;
    private SocietaDAO societaDao;

    @BeforeEach
    void setUp() {
        dao = new ConcorrenteDAO();
        societaDao = new SocietaDAO();
    }

    @Test
    @DisplayName("Registrazione e recupero concorrente")
    void testRegistraEGetConcorrente() {
        // Preparazione: creo una società esistente
        String nomeSocieta = "SOC_CONC_" + System.nanoTime();
        assertDoesNotThrow(() ->
            societaDao.registraSocieta(
                nomeSocieta, "Via Test", "Città", "12345", "soc@test.it", "pwd"
            )
        );

        String cf = "CF_" + System.nanoTime();

        assertDoesNotThrow(() ->
            dao.registraConcorrente(
                cf, "Nome", "Cognome", "c@test.it",
                LocalDate.of(1990, 1, 1),
                nomeSocieta, "pwd"
            )
        );

        Concorrente c = dao.getConcorrente(cf);
        assertNotNull(c);
        assertEquals(cf, c.getCf());
        
        // Pulizia: elimino prima il concorrente (dipende dalla società)
        assertDoesNotThrow(() -> dao.eliminaConcorrente(cf));
        
        // Verifica eliminazione concorrente
        assertNull(dao.getConcorrente(cf));
        
        // Poi elimino la società
        assertDoesNotThrow(() -> societaDao.eliminaSocieta(nomeSocieta));
        
        // Verifica eliminazione società
        assertFalse(societaDao.esisteSocieta(nomeSocieta));
    }

    @Test
    @DisplayName("Concorrente non esistente → null")
    void testGetConcorrenteNotFound() {
        assertNull(dao.getConcorrente("CF_INESISTENTE_" + System.nanoTime()));
    }

  

    @Test
    @DisplayName("Count pescatori >= 0")
    void testCountPescatori() {
        long count = dao.countPescatori();
        assertTrue(count >= 0);
    }
}