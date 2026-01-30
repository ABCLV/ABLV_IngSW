package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.exception.SocietaEccezione;
import model.Concorrente;
import model.Gara;
import model.Societa;

class SocietaDAOTest {

    private SocietaDAO dao;

    @BeforeEach
    void setUp() {
        dao = new SocietaDAO();
    }

    @Test
    @DisplayName("Registrazione società e recupero")
    void testRegistraESocietaGet() {
        String nome = "SOC_" + System.nanoTime();

        dao.registraSocieta(
            nome, "Via Test", "Città", "12345", "soc@test.it", "pwd"
        );

        Societa soc = dao.getSocieta(nome);
        assertNotNull(soc);
        assertEquals(nome, soc.getNome());
    }

    @Test
    @DisplayName("Registrazione società duplicata genera eccezione")
    void testRegistraSocietaDuplicata() {
        String nome = "SOC_DUP_" + System.nanoTime();

        dao.registraSocieta(
            nome, "Via Test", "Città", "12345", "soc@test.it", "pwd"
        );

        assertThrows(SocietaEccezione.class, () ->
            dao.registraSocieta(
                nome, "Via X", "Y", "00000", "x@test.it", "pwd"
            )
        );
    }

    @Test
    @DisplayName("Esiste società")
    void testEsisteSocieta() {
        String nome = "SOC_EXIST_" + System.nanoTime();

        assertFalse(dao.esisteSocieta(nome));

        dao.registraSocieta(
            nome, "Via Test", "Città", "12345", "soc@test.it", "pwd"
        );

        assertTrue(dao.esisteSocieta(nome));
    }

    @Test
    @DisplayName("Liste associate a società non sono null")
    void testListeSocieta() {
        List<Gara> gare = dao.getGareProposteDaSocieta("INESISTENTE");
        List<Concorrente> conc = dao.getConcorrentiPerSocieta("INESISTENTE");

        assertNotNull(gare);
        assertNotNull(conc);
    }
}
