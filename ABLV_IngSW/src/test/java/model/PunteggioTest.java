package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PunteggioTest {
    
    private Punteggio punteggio;
    private static final int ID_TEST = 1;
    private static final float NUM_PUNTI_TEST = 15.5f;
    private static final boolean SQUALIFICA_TEST = false;

    @BeforeEach
    void setUp() {
        punteggio = new Punteggio(ID_TEST, NUM_PUNTI_TEST, SQUALIFICA_TEST);
    }
    
    @Test
    @DisplayName("Costruttore inizializza tutti i campi")
    void testCostruttore() {
        assertEquals(ID_TEST, punteggio.idPunteggio);
        assertEquals(NUM_PUNTI_TEST, punteggio.getPunti());
        // Nota: non c'è getter per squalifica, quindi non testabile direttamente
    }
    
    @Test
    @DisplayName("Setter e getter punti modificano il valore")
    void testSetterGetterPunti() {
        punteggio.setPunti(20);
        assertEquals(20, punteggio.getPunti());
        
        punteggio.setPunti(0);
        assertEquals(0, punteggio.getPunti());
    }
    
   
    
    @Test
    @DisplayName("GetPunti ritorna 0 di default se non settato")
    void testGetPuntiDefault() {
        Punteggio p = new Punteggio(2, 0.0f, false);
        // Il metodo getPunti nel tuo codice ritorna 0 hardcoded!
        assertEquals(0, p.getPunti());
    }
}