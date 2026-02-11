package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.CampoGara;
import model.Settore;

class SettoreDAOTest {

    private SettoreDAO dao;

    @BeforeEach
    void setUp() {
        dao = new SettoreDAO();
    }

    @Test
    @DisplayName("Esplora settori: ritorna lista (anche vuota)")
    void testEsploraSettori() {
        CampoGara campo = new CampoGara();
        campo.setIdCampoGara(123456);

        List<Settore> settori = dao.esploraSettori(campo);
        assertNotNull(settori);
    }
}
