
package verifiche;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import unibg.it.ablv.MyMath;
import unibg.it.ablv.Rettangolo;

public class MathTest {
	@Test
    void testMax_UtilePerDifetto() {
        // Caso che evidenzia il bug: x != y
        int result = MyMath.max(5, 3); // il massimo dovrebbe essere 5
        assertEquals(5, result); // FALLIRA, perché il metodo ritorna 3
    }

    @Test
    void testMax_InutilePerDifetto() {
        // Caso che non evidenzia il bug: x == y
        int result = MyMath.max(4, 4);
        assertEquals(4, result); // PASSA, il metodo ritorna correttamente 4
    }
    
    @Test
    void testRett_UtilePerDifetto() {
    	Rettangolo rett = new Rettangolo(3,2);
        // Caso che evidenzia il bug: x != y
        int result = rett.getArea(); // il massimo dovrebbe essere 5
        assertEquals(6, result); // FALLIRA, perché il metodo ritorna 3
    }
    
    @Test
    void testRett_InutilePerDifetto() {
	    Rettangolo rett = new Rettangolo(2,2);
	    // Caso che evidenzia il bug: x != y
	    int result = rett.getArea(); // il massimo dovrebbe essere 5
	    assertEquals(4, result); // FALLIRA, perché il metodo ritorna 3
    }
}
