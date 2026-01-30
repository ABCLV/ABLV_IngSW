package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Campionato;

class CampionatoDAOTest {

    private CampionatoDAO dao;

    @BeforeEach
    void setUp() {
        dao = new CampionatoDAO();
    }

    @Test
    @DisplayName("Get campionati: restituisce lista non null")
    void testGetCampionati() {
        assertDoesNotThrow(() -> {
            List<Campionato> campionati = dao.getCampionati();
            assertNotNull(campionati);
        });
    }

    @Test
    @DisplayName("Esiste gara in campionato: restituisce false se non esiste")
    void testEsisteGaraInCampionatoNotFound() {
        Campionato campionato = new Campionato();
        campionato.setTitolo("CAMPIONATO_TEST_" + System.nanoTime());

        assertDoesNotThrow(() -> {
            boolean esiste = dao.esisteGaraInCampionato(campionato, 999);
            assertFalse(esiste);
        });
    }
}
