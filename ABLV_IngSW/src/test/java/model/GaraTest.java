package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.enums.CriterioPunti;
import model.enums.StatoConferma;
import model.enums.StatoGara;
import model.enums.Tecnica;
import model.enums.TipologiaGara;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class GaraTest {
    
    private Gara gara;
    private static final int CODICE_TEST = 12345678;
    private static final int NUM_PROVA_TEST = 1;
    private static final int MIN_PERSONE_TEST = 10;
    private static final int MAX_PERSONE_TEST = 50;
    
    @BeforeEach
    void setUp() {
        gara = new Gara();
        gara.setCodice(CODICE_TEST);
        gara.setNumProva(NUM_PROVA_TEST);
        gara.setTecnica(Tecnica.SPINNING);
        gara.setCriterioPunti(CriterioPunti.NUM_CATTURE);
        gara.setData(LocalDate.now().plusDays(7)); // Data futura
        gara.setMinPersone(MIN_PERSONE_TEST);
        gara.setMaxPersone(MAX_PERSONE_TEST);
        gara.setStatoGara(StatoGara.NON_INIZIATA);
        gara.setStatoConferma(StatoConferma.IN_ATTESA);
        gara.setTipoGara(TipologiaGara.INDIVIDUALE);
    }
    
   
    
    @Test
    @DisplayName("Setter e getter modificano correttamente i valori base")
    void testSetterGetter() {
        assertEquals(CODICE_TEST, gara.getCodice());
        assertEquals(NUM_PROVA_TEST, gara.getNumProva());
        assertEquals(Tecnica.SPINNING, gara.getTecnica());
        assertEquals(CriterioPunti.NUM_CATTURE, gara.getCriterioPunti());
        assertEquals(MIN_PERSONE_TEST, gara.getMinPersone());
        assertEquals(MAX_PERSONE_TEST, gara.getMaxPersone());
        assertEquals(StatoGara.NON_INIZIATA, gara.getStatoGara());
        assertEquals(StatoConferma.IN_ATTESA, gara.getStatoConferma());
        assertEquals(TipologiaGara.INDIVIDUALE, gara.getTipoGara());
    }
    
    @Test
    @DisplayName("Validazione: codice null non consentito")
    void testCodiceNull() {
        try {
            gara.setCodice((Integer) null);
            fail("Dovrebbe lanciare eccezione per codice null");
        } catch (IllegalArgumentException e) {
            // OK, eccezione attesa
        }
    }

    @Test
    @DisplayName("Validazione: numero prova deve essere positivo")
    void testNumProvaNonValido() {
        try {
            gara.setNumProva(0);
            fail("Dovrebbe lanciare eccezione per numero prova zero");
        } catch (IllegalArgumentException e) {
            // OK
        }
        
        try {
            gara.setNumProva(-5);
            fail("Dovrebbe lanciare eccezione per numero prova negativo");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    @DisplayName("Validazione: data non puo essere passata")
    void testDataPassata() {
        try {
            gara.setData(LocalDate.now().minusDays(1));
            fail("Dovrebbe lanciare eccezione per data passata");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    @DisplayName("Validazione: max persone deve essere maggiore di min persone")
    void testMaxMinPersoneNonValidi() {
        gara.setMinPersone(10);
        
        try {
            gara.setMaxPersone(5); // max < min
            fail("Dovrebbe lanciare eccezione quando max < min");
        } catch (IllegalArgumentException e) {
            // OK
        }
        
        try {
            gara.setMaxPersone(10); // max = min
            fail("Dovrebbe lanciare eccezione quando max = min");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
    
    
    

}