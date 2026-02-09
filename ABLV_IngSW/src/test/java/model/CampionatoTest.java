package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CampionatoTest {
    
    private Campionato campionato;
    private static final String TITOLO_TEST = "Coppa Italia 2024";
    private static final String CATEGORIA_TEST = "Senior";

    @BeforeEach
    void setUp() {
        campionato = new Campionato(TITOLO_TEST, CATEGORIA_TEST);
    }
    
   
  
    
    @Test
    @DisplayName("Setter e getter modificano titolo e categoria")
    void testSetterGetter() {
        campionato.setTitolo("Nuovo Torneo");
        assertEquals("Nuovo Torneo", campionato.getTitolo());
        
        campionato.setCategoria("Junior");
        assertEquals("Junior", campionato.getCategoria());
    }
   
    
    @Test
    @DisplayName("Getter classifiche dovrebbero ritornare liste, ma al momento sono null")
    void testClassificheNonImplementate() {
        // Questo test fallisce perché getClassificaTotale() e classificaProva() 
        // ritornano null.
        assertNotNull(campionato.getClassificaTotale(), "TODO: implementare logica classifica totale");
        assertNotNull(campionato.classificaProva(1), "TODO: implementare logica classifica prova");
    }
}