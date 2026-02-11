package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.CampoGara;

class CampoGaraDAOTest {

    private CampoGaraDAO dao;

    @Test
    @DisplayName("Campo gara inesistente → null")
    void testTrovaCampoGaraNotFound() {
    	dao = new CampoGaraDAO();
        CampoGara campo = dao.trovaCampoGara(12345);
        assertNull(campo);
    }
}
