package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.CampoGara;
import model.Gara;

class GaraDAOTest {

    private GaraDAO dao;

    @BeforeEach
    void setUp() {
        dao = new GaraDAO();
    }

    @Test
    @DisplayName("Get gare")
    void testGetGare() {
        List<Gara> gare = dao.getGare();
        assertNotNull(gare);
    }

    @Test
    @DisplayName("Codice campo gara non trovato → null")
    void testTrovaCodiceCampoGaraNotFound() {
        String codice = dao.trovaCodiceCampoGara("GARA_INESISTENTE");
        assertNull(codice);
    }

    @Test
    @DisplayName("Esplora gare per campo")
    void testEsploraGare() {
        CampoGara campo = new CampoGara();
        campo.setIdCampoGara("ID_INESISTENTE");

        List<Gara> gare = dao.esploraGare(campo);
        assertNotNull(gare);
    }

    @Test
    @DisplayName("Ultimo codice gara: formato valido o null")
    void testGetUltimoCodiceGara() {
        String codice = dao.getUltimoCodiceGara();
        if (codice != null) {
            assertTrue(codice.matches("G\\d+"));
        }
    }
}
