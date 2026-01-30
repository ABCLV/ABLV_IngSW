package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.CampoGara;

class CampoGaraDAOTest {

    private CampoGaraDAO dao;

    @BeforeEach
    void setUp() {
        dao = new CampoGaraDAO();
    }

    @Test
    @DisplayName("Get campi gara")
    void testGetCampiGara() {
        List<CampoGara> campi = dao.getCampoGara();
        assertNotNull(campi);
    }

    @Test
    @DisplayName("Campo gara inesistente → null")
    void testTrovaCampoGaraNotFound() {
        CampoGara campo = dao.trovaCampoGara("ID_INESISTENTE");
        assertNull(campo);
    }
}
