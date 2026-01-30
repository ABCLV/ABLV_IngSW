package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SponsorTest {
    
    private Sponsor sponsor;
    private static final String ID_TEST = "SP001";
    private static final String NOME_TEST = "Fishing Pro";
    private static final String PAGINA_WEB_TEST = "www.fishingpro.it";

    @BeforeEach
    void setUp() {
        sponsor = new Sponsor(ID_TEST, NOME_TEST, PAGINA_WEB_TEST);
    }
    
    @Test
    @DisplayName("Costruttore inizializza correttamente")
    void testCostruttore() {
        assertNotNull(sponsor);
        assertEquals(ID_TEST, sponsor.IdSponsor);
    }
    
    @Test
    @DisplayName("Accesso campo pubblico IdSponsor")
    void testIdSponsorAccess() {
        sponsor.IdSponsor = "SP002";
        assertEquals("SP002", sponsor.IdSponsor);
    }
}