package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Concorrente;
import model.Gara;

class ConcorrenteDAOTest {

    private ConcorrenteDAO dao;

    @BeforeEach
    void setUp() {
        dao = new ConcorrenteDAO();
    }

    @Test
    @DisplayName("Registrazione e recupero concorrente")
    void testRegistraEGetConcorrente() {
        String cf = "CF_" + System.nanoTime();

        dao.registraConcorrente(
            cf, "Nome", "Cognome", "c@test.it",
            LocalDate.of(1990, 1, 1),
            "SOCIETA_TEST", "pwd"
        );

        Concorrente c = dao.getConcorrente(cf);
        assertNotNull(c);
        assertEquals(cf, c.getCf());
    }

    @Test
    @DisplayName("Concorrente non esistente → null")
    void testGetConcorrenteNotFound() {
        assertNull(dao.getConcorrente("CF_INESISTENTE"));
    }

    @Test
    @DisplayName("Get gare concorrente")
    void testGetGareConcorrente() {
        List<Gara> gare = dao.getGareConcorrente("CF_INESISTENTE");
        assertNotNull(gare);
    }

    @Test
    @DisplayName("Count pescatori >= 0")
    void testCountPescatori() {
        long count = dao.countPescatori();
        assertTrue(count >= 0);
    }
}
