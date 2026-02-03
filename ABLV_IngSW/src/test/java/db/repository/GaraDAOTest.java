package db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Amministratore;
import model.CampoGara;
import model.Gara;
import model.enums.CriterioPunti;
import model.enums.StatoConferma;
import model.enums.StatoGara;
import model.enums.Tecnica;
import model.enums.TipologiaGara;

class GaraDAOTest {

    private GaraDAO dao;
    private AmministratoreDAO adminDao;
    private CampoGaraDAO campoGaraDao;

    @BeforeEach
    void setUp() {
        dao = new GaraDAO();
        adminDao = new AmministratoreDAO();
        campoGaraDao = new CampoGaraDAO();
    }

    // Metodo helper per ottenere un campo gara esistente dal DB
    private String getCampoGaraEsistente() {
        List<CampoGara> campi = assertDoesNotThrow(() -> campoGaraDao.getCampoGara());
        if (!campi.isEmpty()) {
            return campi.get(0).getIdCampoGara();
        }
        return "CAMPO_001"; // fallback, ma potrebbe fallire se non esiste
    }

  
    @Test
    @DisplayName("Codice campo gara non trovato → null")
    void testTrovaCodiceCampoGaraNotFound() {
        String codice = dao.trovaCodiceCampoGara("GARA_INESISTENTE_" + System.nanoTime());
        assertNull(codice);
    }

    @Test
    @DisplayName("Esplora gare per campo")
    void testEsploraGare() {
        String campoEsistente = getCampoGaraEsistente();
        CampoGara campo = new CampoGara();
        campo.setIdCampoGara(campoEsistente);

        List<Gara> gare = dao.esploraGare(campo);
        assertNotNull(gare);
    }

    @Test
    @DisplayName("Ultimo codice gara: formato valido")
    void testGetUltimoCodiceGara() {
        String codice = dao.getUltimoCodiceGara();
        assertNotNull(codice);
        assertTrue(codice.matches("G\\d+"));
    }

    @Test
    @DisplayName("Insert gara e verifica recupero")
    void testInsertGara() {
        // Preparazione: creo admin propositore
        String cfAdmin = "ADM_GARA_" + System.nanoTime();
        assertDoesNotThrow(() -> 
            adminDao.registraAmministratore(cfAdmin, "Admin", "Test", "admin@test.it", "pwd")
        );

        String codiceGara = dao.getUltimoCodiceGara();
        int num = Integer.parseInt(codiceGara.substring(1)) + 1;
        String nuovoCodice = String.format("G%03d", num);

        String idCampo = getCampoGaraEsistente();

        Gara gara = new Gara();
        gara.setCodice(nuovoCodice);
        gara.setNumProva(1);
        gara.setTecnica(Tecnica.SPINNING);
        gara.setCriterioPunti(CriterioPunti.PESO);
        gara.setMinPersone(2);
        gara.setMaxPersone(10);
        gara.setStatoGara(StatoGara.NON_INIZIATA);
        gara.setStatoConferma(StatoConferma.IN_ATTESA);
        gara.setTipoGara(TipologiaGara.INDIVIDUALE);
        gara.setData(LocalDate.now().plusDays(7));
        
        CampoGara campo = new CampoGara();
        campo.setIdCampoGara(idCampo);
        gara.setCampoGara(campo);
        
        Amministratore admin = new Amministratore();
        admin.setCfAmministratore(cfAdmin);
        gara.setPropositore(admin);

        // Inserimento
        assertDoesNotThrow(() -> dao.insertGara(gara));

        // Verifica: la gara ora esiste
        String campoGara = dao.trovaCodiceCampoGara(nuovoCodice);
        assertNotNull(campoGara);
        assertEquals(idCampo, campoGara);

        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaTurniGara(nuovoCodice));
        assertDoesNotThrow(() -> dao.eliminaGara(nuovoCodice));
        assertDoesNotThrow(() -> adminDao.eliminaAmministratore(cfAdmin));
        
        // Verifica eliminazione
        assertNull(dao.trovaCodiceCampoGara(nuovoCodice));
    }

    @Test
    @DisplayName("Accetta gara")
    void testAccettaGara() {
        // Preparazione: creo due admin
        String cfPropositore = "ADM_PROP_" + System.nanoTime();
        String cfAccettatore = "ADM_ACC_" + System.nanoTime();
        
        assertDoesNotThrow(() -> {
            adminDao.registraAmministratore(cfPropositore, "Prop", "Test", "prop@test.it", "pwd");
            adminDao.registraAmministratore(cfAccettatore, "Acc", "Test", "acc@test.it", "pwd");
        });

        // Creo gara
        String codiceGara = dao.getUltimoCodiceGara();
        int num = Integer.parseInt(codiceGara.substring(1)) + 1;
        String nuovoCodice = String.format("G%03d", num);

        String idCampo = getCampoGaraEsistente();

        Gara gara = new Gara();
        gara.setCodice(nuovoCodice);
        gara.setNumProva(1);
        gara.setTecnica(Tecnica.GALLEGGIANTE);
        gara.setCriterioPunti(CriterioPunti.BIG_ONE);
        gara.setMinPersone(1);
        gara.setMaxPersone(5);
        gara.setStatoGara(StatoGara.NON_INIZIATA);
        gara.setStatoConferma(StatoConferma.IN_ATTESA);
        gara.setTipoGara(TipologiaGara.INDIVIDUALE);
        gara.setData(LocalDate.now().plusDays(14));
        
        CampoGara campo = new CampoGara();
        campo.setIdCampoGara(idCampo);
        gara.setCampoGara(campo);
        
        Amministratore adminProp = new Amministratore();
        adminProp.setCfAmministratore(cfPropositore);
        gara.setPropositore(adminProp);

        assertDoesNotThrow(() -> dao.insertGara(gara));

        // Accetto la gara con admin diverso
        boolean accettata = assertDoesNotThrow(() -> dao.accettaGara(nuovoCodice, cfAccettatore));
        assertTrue(accettata);

        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaTurniGara(nuovoCodice));
        assertDoesNotThrow(() -> dao.eliminaGara(nuovoCodice));
        assertDoesNotThrow(() -> {
            adminDao.eliminaAmministratore(cfPropositore);
            adminDao.eliminaAmministratore(cfAccettatore);
        });
    }

    @Test
    @DisplayName("Rifiuta gara")
    void testRifiutaGara() {
        // Preparazione
        String cfPropositore = "ADM_PROP_R_" + System.nanoTime();
        String cfRifiutatore = "ADM_RIF_" + System.nanoTime();
        
        assertDoesNotThrow(() -> {
            adminDao.registraAmministratore(cfPropositore, "Prop", "Rif", "prop@rif.it", "pwd");
            adminDao.registraAmministratore(cfRifiutatore, "Rif", "Test", "rif@test.it", "pwd");
        });

        // Creo gara
        String codiceGara = dao.getUltimoCodiceGara();
        int num = Integer.parseInt(codiceGara.substring(1)) + 1;
        String nuovoCodice = String.format("G%03d", num);

        String idCampo = getCampoGaraEsistente();

        Gara gara = new Gara();
        gara.setCodice(nuovoCodice);
        gara.setNumProva(1);
        gara.setTecnica(Tecnica.GALLEGGIANTE);
        gara.setCriterioPunti(CriterioPunti.BIG_ONE);
        gara.setMinPersone(2);
        gara.setMaxPersone(8);
        gara.setStatoGara(StatoGara.NON_INIZIATA);
        gara.setStatoConferma(StatoConferma.IN_ATTESA);
        gara.setTipoGara(TipologiaGara.INDIVIDUALE);
        gara.setData(LocalDate.now().plusDays(21));
        
        CampoGara campo = new CampoGara();
        campo.setIdCampoGara(idCampo);
        gara.setCampoGara(campo);
        
        Amministratore adminProp = new Amministratore();
        adminProp.setCfAmministratore(cfPropositore);
        gara.setPropositore(adminProp);

        assertDoesNotThrow(() -> dao.insertGara(gara));

        // Rifiuto la gara
        boolean rifiutata = assertDoesNotThrow(() -> dao.rifiutaGara(nuovoCodice, cfRifiutatore));
        assertTrue(rifiutata);

        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaTurniGara(nuovoCodice));
        assertDoesNotThrow(() -> dao.eliminaGara(nuovoCodice));
        assertDoesNotThrow(() -> {
            adminDao.eliminaAmministratore(cfPropositore);
            adminDao.eliminaAmministratore(cfRifiutatore);
        });
    }

    @Test
    @DisplayName("Get gare da confermare")
    void testGetGareDaConfermare() {
        String cf = "ADM_CONF_" + System.nanoTime();
        assertDoesNotThrow(() -> 
            adminDao.registraAmministratore(cf, "Conf", "Test", "conf@test.it", "pwd")
        );

        List<Gara> gare = assertDoesNotThrow(() -> dao.getGareDaConfermare(cf));
        assertNotNull(gare);

        assertDoesNotThrow(() -> adminDao.eliminaAmministratore(cf));
    }

    

    @Test
    @DisplayName("Admin non può accettare propria gara")
    void testAccettaGaraStessoAdmin() {
        String cfAdmin = "ADM_SELF_" + System.nanoTime();
        assertDoesNotThrow(() -> 
            adminDao.registraAmministratore(cfAdmin, "Self", "Test", "self@test.it", "pwd")
        );

        // Creo gara
        String codiceGara = dao.getUltimoCodiceGara();
        int num = Integer.parseInt(codiceGara.substring(1)) + 1;
        String nuovoCodice = String.format("G%03d", num);

        String idCampo = getCampoGaraEsistente();

        Gara gara = new Gara();
        gara.setCodice(nuovoCodice);
        gara.setNumProva(1);
        gara.setTecnica(Tecnica.GALLEGGIANTE);
        gara.setCriterioPunti(CriterioPunti.PESO);
        gara.setMinPersone(1);
        gara.setMaxPersone(3);
        gara.setStatoGara(StatoGara.NON_INIZIATA);
        gara.setStatoConferma(StatoConferma.IN_ATTESA);
        gara.setTipoGara(TipologiaGara.INDIVIDUALE);
        gara.setData(LocalDate.now().plusDays(30));
        
        CampoGara campo = new CampoGara();
        campo.setIdCampoGara(idCampo);
        gara.setCampoGara(campo);
        
        Amministratore admin = new Amministratore();
        admin.setCfAmministratore(cfAdmin);
        gara.setPropositore(admin);

        assertDoesNotThrow(() -> dao.insertGara(gara));

        // Provo ad accettare con lo stesso admin → deve fallire
        boolean accettata = assertDoesNotThrow(() -> dao.accettaGara(nuovoCodice, cfAdmin));
        assertFalse(accettata);

        // Pulizia
        assertDoesNotThrow(() -> dao.eliminaTurniGara(nuovoCodice));
        assertDoesNotThrow(() -> dao.eliminaGara(nuovoCodice));
        assertDoesNotThrow(() -> adminDao.eliminaAmministratore(cfAdmin));
    }
}